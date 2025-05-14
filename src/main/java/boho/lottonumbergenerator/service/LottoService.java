package boho.lottonumbergenerator.service;

import java.util.List;
import java.util.Random;

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

	@Value("${lotto.draw.hour}")
	private Integer drawHour;

	@Value("${lotto.draw.minute}")
	private Integer drawMinute;

	@Value("${lotto.draw.second}")
	private Integer drawSecond;

	private final GeneratedLottoRepository generatedLottoRepository;
	private final OfficialLottoRepository officialLottoRepository;

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

	@Cacheable(cacheNames = "winning_lotto")
	public List<WinningLottoListResponse> getAllWinningLotto() {

		return generatedLottoRepository.findByCreatedAtBetween(
				officialLottoRepository.findTop2ByOrderByDrawDateDesc() // 두 번째로 최신의 로또
					.get(1)
					.getDrawDate()
					.atTime(drawHour, drawMinute, drawSecond),
				officialLottoRepository.findTop2ByOrderByDrawDateDesc() // 가장 최신의 로또
					.getFirst()
					.getDrawDate()
					.atTime(drawHour, drawMinute, drawSecond)
			)
			.stream()
			.filter(generatedLotto -> isLottoWinning(
				generatedLotto, officialLottoRepository.findTop2ByOrderByDrawDateDesc().getFirst()))
			.map(WinningLottoListResponse::of)
			.toList();
	}

	private boolean isLottoWinning(GeneratedLotto generatedLotto, OfficialLotto officialLotto) {
		return (generatedLotto.getFirstNumber().equals(officialLotto.getFirstNumber()) &&
			generatedLotto.getSecondNumber().equals(officialLotto.getSecondNumber()) &&
			generatedLotto.getThirdNumber().equals(officialLotto.getThirdNumber()) &&
			generatedLotto.getFourthNumber().equals(officialLotto.getFourthNumber()) &&
			generatedLotto.getFifthNumber().equals(officialLotto.getFifthNumber()) &&
			generatedLotto.getSixthNumber().equals(officialLotto.getSixthNumber()));
	}
}
