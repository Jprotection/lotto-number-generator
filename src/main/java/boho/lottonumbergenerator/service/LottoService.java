package boho.lottonumbergenerator.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boho.lottonumbergenerator.dro.LottoGenerateResponse;
import boho.lottonumbergenerator.dro.LottoListResponse;
import boho.lottonumbergenerator.dro.WinningLottoListResponse;
import boho.lottonumbergenerator.entity.GeneratedLotto;
import boho.lottonumbergenerator.entity.OfficialLotto;
import boho.lottonumbergenerator.repository.GeneratedLottoRepository;
import boho.lottonumbergenerator.repository.OfficialLottoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LottoService {

	@Value("${lotto.draw.time}")
	private LocalTime drawTime;

	private final GeneratedLottoRepository generatedLottoRepository;
	private final OfficialLottoRepository officialLottoRepository;

	// 6개의 번호로 된 랜덤 로또 조합 생성
	@Transactional
	public LottoGenerateResponse generateLotto() {
		List<Integer> numbers = new Random().ints()
			.map(i -> new Random(i).nextInt(45) + 1)
			.distinct()
			.limit(6)
			.sorted()
			.boxed()
			.toList();

		GeneratedLotto generatedLotto = GeneratedLotto.from(numbers);

		return LottoGenerateResponse.of(generatedLottoRepository.save(generatedLotto));
	}

	public List<LottoListResponse> getAllLotto() {
		return generatedLottoRepository.findAll().stream()
			.map(LottoListResponse::of)
			.toList();
	}

	// 1등 당첨 로또를 찾는 메서드
	@Cacheable(cacheNames = "winning_lotto", key = "#root.methodName")
	public List<WinningLottoListResponse> findAllFirstPrizeLotto() {

		return findWinningLotto(generatedLotto -> isFirstPrizeLotto(
			generatedLotto, officialLottoRepository.findTop2ByOrderByDrawDateDesc().getFirst()));
	}

	// 2등 당첨 로또를 찾는 메서드
	@Cacheable(cacheNames = "winning_lotto", key = "#root.methodName")
	public List<WinningLottoListResponse> findAllSecondPrizeLotto() {

		return findWinningLotto(generatedLotto -> isSecondPrizeLotto(
			generatedLotto, officialLottoRepository.findTop2ByOrderByDrawDateDesc().getFirst()));
	}

	public boolean isOfficialLottoLoaded() {
		return officialLottoRepository.existsByDrawNumberIsNotNull();
	}

	// predicate에 따라 각 등수에 당첨된 로또를 탐색
	private List<WinningLottoListResponse> findWinningLotto(Predicate<GeneratedLotto> predicate) {
		if (!isOfficialLottoLoaded()) {
			log.warn("공식 로또 데이터가 존재하지 않습니다.");
			return List.of();
		}

		return generatedLottoRepository.findByCreatedAtBetween(
				officialLottoRepository.findTop2ByOrderByDrawDateDesc() // 두 번째로 최신의 로또
					.get(1)
					.getDrawDate()
					.atTime(drawTime),
				officialLottoRepository.findTop2ByOrderByDrawDateDesc() // 가장 최신의 로또
					.getFirst()
					.getDrawDate()
					.atTime(drawTime)
			)
			.stream()
			.filter(predicate)
			.map(WinningLottoListResponse::of)
			.toList();
	}

	// 1등 당첨 조건: 6개의 숫자가 모두 일치해야 함
	private boolean isFirstPrizeLotto(GeneratedLotto generatedLotto, OfficialLotto officialLotto) {
		return (generatedLotto.getFirstNumber().equals(officialLotto.getFirstNumber()) &&
			generatedLotto.getSecondNumber().equals(officialLotto.getSecondNumber()) &&
			generatedLotto.getThirdNumber().equals(officialLotto.getThirdNumber()) &&
			generatedLotto.getFourthNumber().equals(officialLotto.getFourthNumber()) &&
			generatedLotto.getFifthNumber().equals(officialLotto.getFifthNumber()) &&
			generatedLotto.getSixthNumber().equals(officialLotto.getSixthNumber()));
	}

	// 2등 당첨 조건: 5개의 숫자와 보너스 숫자가 일치해야 함
	private boolean isSecondPrizeLotto(GeneratedLotto generatedLotto, OfficialLotto officialLotto) {
		// OfficialLotto 번호를 Set으로 변환 (해시 값을 이용한 O(1)의 탐색 성능을 위해)
		Set<Integer> officialLottoNumbers = Set.of(
			officialLotto.getFirstNumber(),
			officialLotto.getSecondNumber(),
			officialLotto.getThirdNumber(),
			officialLotto.getFourthNumber(),
			officialLotto.getFifthNumber(),
			officialLotto.getSixthNumber()
		);

		// GeneratedLotto 번호를 리스트로 변환
		List<Integer> generatedLottoNumbers = List.of(
			generatedLotto.getFirstNumber(),
			generatedLotto.getSecondNumber(),
			generatedLotto.getThirdNumber(),
			generatedLotto.getFourthNumber(),
			generatedLotto.getFifthNumber(),
			generatedLotto.getSixthNumber()
		);

		// 1~6번 숫자와의 일치 개수 확인
		long matchedCount = generatedLottoNumbers.stream()
			.filter(officialLottoNumbers::contains)
			.count();

		// 2등 조건 확인 (5개의 숫자 + 보너스 번호)
		return matchedCount == 5 && generatedLottoNumbers.contains(officialLotto.getBonusNumber());
	}
}
