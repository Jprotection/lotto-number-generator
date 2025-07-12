package boho.lottonumbergenerator.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boho.lottonumbergenerator.dto.ExcludeNumberRequest;
import boho.lottonumbergenerator.dto.IncludeNumberRequest;
import boho.lottonumbergenerator.dto.LottoGenerateResponse;
import boho.lottonumbergenerator.dto.LottoListResponse;
import boho.lottonumbergenerator.dto.OfficialLottoResponse;
import boho.lottonumbergenerator.dto.WinningLottoListResponse;
import boho.lottonumbergenerator.entity.lotto.GeneratedLotto;
import boho.lottonumbergenerator.entity.lotto.OfficialLotto;
import boho.lottonumbergenerator.entity.member.Member;
import boho.lottonumbergenerator.repository.GeneratedLottoRepository;
import boho.lottonumbergenerator.repository.MemberRepository;
import boho.lottonumbergenerator.repository.OfficialLottoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LottoService {

	@Value("${lotto.purchase.cutoff-time}")
	private LocalTime cutoffTime;

	private final GeneratedLottoRepository generatedLottoRepository;
	private final OfficialLottoRepository officialLottoRepository;
	private final MemberRepository memberRepository;

	// 6개의 번호로 된 랜덤 로또 조합 생성
	@Transactional
	public List<LottoGenerateResponse> generateLotto(
		Integer count, IncludeNumberRequest includeNumberRequest, ExcludeNumberRequest excludeNumberRequest,
		Authentication authentication) {

		Member creator = identifyCreator(authentication);

		List<Integer> includeNumbers = includeNumberRequest.toIncludeNumberList();
		List<Integer> excludeNumbers = excludeNumberRequest.toExcludeNumberList();

		OfficialLotto latestOfficialLotto = findLatestOfficialLotto();
		// 생성된 로또가 적용될 추첨 회차
		Integer drawNumber = calculateDrawNumber(latestOfficialLotto);
		// 생성된 로또가 적용될 추첨 날짜
		LocalDate drawDate = calculateDrawDate(latestOfficialLotto);

		List<LottoGenerateResponse> generatedLottoList = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			List<Integer> generatedNumbers = new Random().ints()
				.map(n -> new Random(n).nextInt(45) + 1)
				.filter(n -> !excludeNumbers.contains(n))
				.filter(n -> !includeNumbers.contains(n))
				.distinct()
				.limit(6 - includeNumbers.size())
				.boxed()
				.toList();

			List<Integer> numbers = Stream.concat(
					includeNumbers.stream(),
					generatedNumbers.stream()
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

	public List<LottoListResponse> getAllGeneratedLotto() {
		return generatedLottoRepository.findAll().stream()
			.map(LottoListResponse::of)
			.toList();
	}

	// 1등 당첨 로또를 찾는 메서드
	@Transactional
	@Cacheable(cacheNames = "winning_lotto", key = "#root.methodName")
	public List<WinningLottoListResponse> findAllFirstPrizeLotto() {

		return findWinningLotto(
			generatedLotto -> isFirstPrizeLotto(generatedLotto, findLatestOfficialLotto()), 1
		);
	}

	// 2등 당첨 로또를 찾는 메서드
	@Transactional
	@Cacheable(cacheNames = "winning_lotto", key = "#root.methodName")
	public List<WinningLottoListResponse> findAllSecondPrizeLotto() {

		return findWinningLotto(
			generatedLotto -> isSecondPrizeLotto(generatedLotto, findLatestOfficialLotto()), 2
		);
	}

	// 3등 당첨 로또를 찾는 메서드
	@Transactional
	@Cacheable(cacheNames = "winning_lotto", key = "#root.methodName")
	public List<WinningLottoListResponse> findAllThirdPrizeLotto() {

		return findWinningLotto(
			generatedLotto -> isThirdPrizeLotto(generatedLotto, findLatestOfficialLotto()), 3
		);
	}

	// 4등 당첨 로또를 찾는 메서드
	@Transactional
	@Cacheable(cacheNames = "winning_lotto", key = "#root.methodName")
	public List<WinningLottoListResponse> findAllFourthPrizeLotto() {

		return findWinningLotto(
			generatedLotto -> isFourthPrizeLotto(generatedLotto, findLatestOfficialLotto()), 4
		);
	}

	// 5등 당첨 로또를 찾는 메서드
	@Transactional
	@Cacheable(cacheNames = "winning_lotto", key = "#root.methodName")
	public List<WinningLottoListResponse> findAllFifthPrizeLotto() {

		return findWinningLotto(
			generatedLotto -> isFifthPrizeLotto(generatedLotto, findLatestOfficialLotto()), 5
		);
	}

	// 미당첨 로또를 찾는 메서드
	@Transactional
	@Cacheable(cacheNames = "winning_lotto", key = "#root.methodName")
	public void fetchNotWinningLotto() {

		if (!isOfficialLottoLoaded()) {
			log.warn("공식 로또 데이터가 존재하지 않습니다.");
			return;
		}

		LocalDateTime latestCutoffTime = findLatestOfficialLotto().getDrawDate().atTime(cutoffTime);

		generatedLottoRepository.findByCreateDateBetween(latestCutoffTime.minusWeeks(1), latestCutoffTime)
			.stream()
			.filter(generatedLotto -> isNotWinningLotto(generatedLotto, findLatestOfficialLotto()))
			.forEach(GeneratedLotto::markAsChecked);
	}

	public boolean isOfficialLottoLoaded() {
		return officialLottoRepository.existsByDrawNumberIsNotNull();
	}

	@Cacheable(cacheNames = "winning_lotto", key = "#root.methodName")
	public OfficialLottoResponse getLatestOfficialLottoInfo() {
		return OfficialLottoResponse.of(findLatestOfficialLotto());
	}

	private OfficialLotto findLatestOfficialLotto() {
		return officialLottoRepository.findTopByOrderByDrawDateDesc()
			.orElseThrow(() -> new NoSuchElementException("로또 정보가 아직 로딩중입니다!"));
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

	// predicate에 따라 각 등수에 당첨된 로또를 탐색
	private List<WinningLottoListResponse> findWinningLotto(Predicate<GeneratedLotto> predicate, Integer prizeRank) {

		if (!isOfficialLottoLoaded()) {
			log.warn("공식 로또 데이터가 존재하지 않습니다.");
			return List.of();
		}

		LocalDateTime latestCutoffTime = findLatestOfficialLotto().getDrawDate().atTime(cutoffTime);

		return generatedLottoRepository.findByCreateDateBetween(latestCutoffTime.minusWeeks(1), latestCutoffTime)
			.stream()
			.filter(predicate)
			.peek(lotto -> lotto.updatePrizeRank(prizeRank))
			.map(WinningLottoListResponse::of)
			.toList();
	}

	// 1등 당첨 조건: 6개의 숫자가 모두 일치해야 함
	private boolean isFirstPrizeLotto(GeneratedLotto generatedLotto, OfficialLotto officialLotto) {
		return getNumberMatchedCount(generatedLotto, officialLotto) == 6;
	}

	// 2등 당첨 조건: 5개의 숫자와 보너스 숫자가 일치해야 함
	private boolean isSecondPrizeLotto(GeneratedLotto generatedLotto, OfficialLotto officialLotto) {

		// 2등 조건 확인 (5개의 숫자 + 보너스 번호 일치)
		return (getNumberMatchedCount(generatedLotto, officialLotto) == 5 &&
			generatedLotto.toNumberList().contains(officialLotto.getBonusNumber()));
	}

	// 3등 당첨 조건: 5개의 숫자가 일치해야 함
	private boolean isThirdPrizeLotto(GeneratedLotto generatedLotto, OfficialLotto officialLotto) {
		// 3등 조건 확인 (5개의 숫자 일치 + 보너스 번호 불일치)
		return (getNumberMatchedCount(generatedLotto, officialLotto) == 5 &&
			!generatedLotto.toNumberList().contains(officialLotto.getBonusNumber()));
	}

	// 4등 당첨 조건: 4개의 숫자가 일치해야 함
	private boolean isFourthPrizeLotto(GeneratedLotto generatedLotto, OfficialLotto officialLotto) {
		// 4등 조건 확인 (4개의 숫자 일치)
		return getNumberMatchedCount(generatedLotto, officialLotto) == 4;
	}

	// 5등 당첨 조건: 3개의 숫자가 일치해야 함
	private boolean isFifthPrizeLotto(GeneratedLotto generatedLotto, OfficialLotto officialLotto) {
		// 5등 조건 확인 (3개의 숫자 일치)
		return getNumberMatchedCount(generatedLotto, officialLotto) == 3;
	}

	// 미당첨: 2개 이하의 숫자가 일치
	private boolean isNotWinningLotto(GeneratedLotto generatedLotto, OfficialLotto officialLotto) {

		return getNumberMatchedCount(generatedLotto, officialLotto) <= 2;
	}

	// GeneratedLotto와 OfficialLotto의 각 숫자 일치 비교
	private long getNumberMatchedCount(GeneratedLotto generatedLotto, OfficialLotto officialLotto) {
		// OfficialLotto 번호를 Set으로 변환 (해시 값을 이용한 O(1)의 탐색 성능을 위해)
		Set<Integer> officialLottoNumbers = officialLotto.toNumberSet();

		// GeneratedLotto 번호를 리스트로 변환
		List<Integer> generatedLottoNumbers = generatedLotto.toNumberList();

		// 1~6번 숫자와의 일치 개수
		return generatedLottoNumbers.stream()
			.filter(officialLottoNumbers::contains)
			.count();
	}
}
