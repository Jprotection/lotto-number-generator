package boho.lottonumbergenerator.service;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import boho.lottonumbergenerator.dro.LottoGenerateResponse;
import boho.lottonumbergenerator.dro.LottoListResponse;
import boho.lottonumbergenerator.entity.GeneratedLotto;
import boho.lottonumbergenerator.repository.GeneratedLottoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LottoService {

	private final GeneratedLottoRepository generatedLottoRepository;

	public LottoGenerateResponse generate() {
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


}
