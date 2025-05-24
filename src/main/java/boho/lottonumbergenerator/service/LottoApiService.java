package boho.lottonumbergenerator.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import boho.lottonumbergenerator.dro.LottoApiResponse;
import boho.lottonumbergenerator.dro.OfficialLottoListResponse;
import boho.lottonumbergenerator.entity.lotto.OfficialLotto;
import boho.lottonumbergenerator.repository.OfficialLottoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LottoApiService {

	private final WebClient webClient;
	private final ObjectMapper objectMapper;
	private final OfficialLottoRepository officialLottoRepository;

	@Async
	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	@CacheEvict(cacheNames = "winning_lotto", allEntries = true)
	public void fetchAllOfficialLotto() {

		if (officialLottoRepository.existsByDrawNumberIsNotNull()) {
			return;
		}

		List<LottoApiResponse> responses = IntStream.iterate(1, i -> i + 1)
			.mapToObj(this::getOfficialLottoWithApi)
			.takeWhile(rawBody -> rawBody.contains("\"returnValue\":\"success\""))
			.map(rawBody -> {
				try {
					return objectMapper.readValue(rawBody, LottoApiResponse.class);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			})
			.toList();

		List<OfficialLotto> officialLottoList = responses.stream()
			.map(OfficialLotto::from)
			.toList();

		officialLottoRepository.saveAll(officialLottoList);
		log.info("{}회차까지 저장 완료", officialLottoList.size());
	}

	public List<OfficialLottoListResponse> getAllOfficialLotto() {
		return officialLottoRepository.findAll()
			.stream()
			.map(OfficialLottoListResponse::of)
			.toList();
	}

	@Transactional
	public void fetchLatestOfficialLotto() {

		try {
			officialLottoRepository.save(
				OfficialLotto.from(
					objectMapper.readValue(
						getOfficialLottoWithApi(officialLottoRepository.count() + 1),
						LottoApiResponse.class)));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private String getOfficialLottoWithApi(Number number) {
		return webClient.get()
			.uri(uriBuilder -> uriBuilder
				.path("/common.do")
				.queryParam("method", "getLottoNumber")
				.queryParam("drwNo", number)
				.build())
			.retrieve()
			.bodyToMono(String.class)
			.block(); // 비동기 말고 동기로 결과 기다리기
	}
}
