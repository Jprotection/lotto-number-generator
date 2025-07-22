package boho.lottonumbergenerator.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boho.lottonumbergenerator.common.event.WinningLottoDetectedEvent;
import boho.lottonumbergenerator.domain.dto.LottoGenerateRequest;
import boho.lottonumbergenerator.domain.dto.LottoGenerateResponse;
import boho.lottonumbergenerator.domain.dto.LottoListResponse;
import boho.lottonumbergenerator.domain.dto.WinningLottoListResponse;
import boho.lottonumbergenerator.domain.dto.WinningLottoRankGroupResponse;
import boho.lottonumbergenerator.domain.entity.lotto.GeneratedLotto;
import boho.lottonumbergenerator.domain.entity.lotto.OfficialLotto;
import boho.lottonumbergenerator.domain.entity.lotto.WinningRank;
import boho.lottonumbergenerator.domain.entity.member.Member;
import boho.lottonumbergenerator.repository.GeneratedLottoRepository;
import boho.lottonumbergenerator.repository.MemberRepository;
import boho.lottonumbergenerator.repository.OfficialLottoRepository;
import boho.lottonumbergenerator.service.GeneratedLottoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratedLottoServiceImpl implements GeneratedLottoService {

	@Value("${lotto.purchase.cutoff-time}")
	private LocalTime cutoffTime;

	private final GeneratedLottoRepository generatedLottoRepository;
	private final OfficialLottoRepository officialLottoRepository;
	private final MemberRepository memberRepository;
	private final ApplicationEventPublisher eventPublisher;

	// 6개의 번호로 된 랜덤 로또 조합 생성
	@Override
	@Transactional
	public List<LottoGenerateResponse> generateLotto(LottoGenerateRequest request, Authentication authentication) {

		Member creator = identifyCreator(authentication);

		List<Integer> includeNumbers = request.getIncludeNumbers();
		List<Integer> excludeNumbers = request.getExcludeNumbers();

		OfficialLotto latestOfficialLotto = getLatestOfficialLotto();
		// 생성된 로또가 적용될 추첨 회차
		Integer drawNumber = calculateDrawNumber(latestOfficialLotto);
		// 생성된 로또가 적용될 추첨 날짜
		LocalDate drawDate = calculateDrawDate(latestOfficialLotto);

		List<LottoGenerateResponse> generatedLottoList = new ArrayList<>();
		for (int i = 0; i < request.count(); i++) {
			List<Integer> numbers = Stream.concat(
					Stream.generate(() -> new Random().nextInt(45) + 1)
						.filter(n -> !excludeNumbers.contains(n))
						.filter(n -> !includeNumbers.contains(n))
						.distinct()
						.limit(6 - includeNumbers.size()),
					includeNumbers.stream()
				)
				.sorted()
				.toList();

			GeneratedLotto generatedLotto = GeneratedLotto.from(
				drawNumber, drawDate, numbers, includeNumbers, excludeNumbers, creator);
			generatedLottoList.add(LottoGenerateResponse.of(generatedLottoRepository.save(generatedLotto)));

			log.info(
				"New lotto numbers generated: {} | ID: [{}] | creatorUsername: [{}] | Include numbers: {} | Exclude numbers: {}",
				generatedLotto.toNumberList(), generatedLotto.getId(),
				creator != null ? creator.getUsername() : "WhoAmI", includeNumbers, excludeNumbers);
		}

		return generatedLottoList;
	}

	@Override
	public List<LottoListResponse> getAllGeneratedLotto() {
		return generatedLottoRepository.findAll().stream()
			.map(LottoListResponse::of)
			.toList();
	}

	@Override
	@Transactional
	@Cacheable(cacheNames = "winning_lotto", key = "#root.methodName")
	public WinningLottoRankGroupResponse fetchAllWinningLotto() {

		OfficialLotto latestOfficialLotto = getLatestOfficialLotto();
		LocalDateTime latestCutoffTime = latestOfficialLotto.getDrawDate().atTime(cutoffTime);

		List<GeneratedLotto> allGeneratedLotto = generatedLottoRepository.findByCreateDateBetween(
			latestCutoffTime.minusWeeks(1), latestCutoffTime);

		// 이미 check 된 로또는 등수별로 그룹핑
		Map<WinningRank, List<WinningLottoListResponse>> checkedMap = allGeneratedLotto
			.stream()
			.filter(GeneratedLotto::isChecked)
			.collect(Collectors.groupingBy(
				generatedLotto -> WinningRank.valueOfRank(generatedLotto.getPrizeRank()),
				Collectors.mapping(WinningLottoListResponse::of, Collectors.toList())
			));

		// check 되지 않은 로또는 당첨 등수 업데이트 및 check
		Map<WinningRank, List<WinningLottoListResponse>> newlyRankedMap = allGeneratedLotto
			.stream()
			.filter(generatedLotto -> !generatedLotto.isChecked())
			.collect(Collectors.groupingBy(
				generatedLotto -> {
					WinningRank rank = WinningRank.from(generatedLotto, latestOfficialLotto);
					generatedLotto.updatePrizeRank(rank.getRank());
					return rank;
				},
				Collectors.mapping(WinningLottoListResponse::of, Collectors.toList())
			));

		// 당첨된 로또가 존재하면 칭호 생성 및 부여하는 이벤트 발행
		if (hasAnyWinners(newlyRankedMap)) {
			eventPublisher.publishEvent(new WinningLottoDetectedEvent(this, newlyRankedMap));
		}

		// checkedMap 과 newlyRankedMap 병합
		Map<WinningRank, List<WinningLottoListResponse>> resultMap = new EnumMap<>(checkedMap);
		newlyRankedMap.forEach((rank, list) ->
			resultMap.merge(rank, list, (existing, replacement) -> {
				existing.addAll(replacement);
				return existing;
			}));

		return WinningLottoRankGroupResponse.builder()
			.firstPrizes(resultMap.getOrDefault(WinningRank.FIRST, List.of()))
			.secondPrizes(resultMap.getOrDefault(WinningRank.SECOND, List.of()))
			.thirdPrizes(resultMap.getOrDefault(WinningRank.THIRD, List.of()))
			.fourthPrizes(resultMap.getOrDefault(WinningRank.FOURTH, List.of()))
			.fifthPrizes(resultMap.getOrDefault(WinningRank.FIFTH, List.of()))
			.build();
	}

	private boolean hasAnyWinners(Map<WinningRank, List<WinningLottoListResponse>> resultMap) {
		return resultMap.keySet()
			.stream()
			.anyMatch(winningRank -> winningRank != WinningRank.NOTHING && !resultMap.get(winningRank).isEmpty());
	}

	private OfficialLotto getLatestOfficialLotto() {
		return officialLottoRepository.findTopByOrderByDrawDateDesc()
			.orElseThrow(() -> new EntityNotFoundException("Official lotto results not found"));
	}

	private Member identifyCreator(Authentication authentication) {
		if (authentication instanceof AnonymousAuthenticationToken) {
			return null;
		}
		return memberRepository.findByUsername(authentication.getName())
			.orElseThrow(
				() -> new UsernameNotFoundException("No Member found with username: " + authentication.getName()));
	}

	private Integer calculateDrawNumber(OfficialLotto latestOfficialLotto) {

		if (enableThisWeek(latestOfficialLotto)) {
			return latestOfficialLotto.getDrawNumber() + 1;
		}

		return latestOfficialLotto.getDrawNumber() + 2;
	}

	private LocalDate calculateDrawDate(OfficialLotto latestOfficialLotto) {

		if (enableThisWeek(latestOfficialLotto)) {
			return latestOfficialLotto.getDrawDate().plusWeeks(1);
		}

		return latestOfficialLotto.getDrawDate().plusWeeks(2);
	}

	private boolean enableThisWeek(OfficialLotto latestOfficialLotto) {
		LocalDate nextDrawDate = latestOfficialLotto.getDrawDate().plusWeeks(1);
		LocalDateTime nextCutoffTime = nextDrawDate.atTime(cutoffTime);

		return !(LocalDateTime.now().isAfter(nextCutoffTime) &&
			LocalDateTime.now().isBefore(nextCutoffTime.plusHours(1)));
	}
}
