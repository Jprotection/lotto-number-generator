package boho.lottonumbergenerator.service.impl;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import boho.lottonumbergenerator.common.exception.OfficialLottoParsingException;
import boho.lottonumbergenerator.domain.dto.LottoApiResponse;
import boho.lottonumbergenerator.domain.dto.OfficialLottoResponse;
import boho.lottonumbergenerator.domain.dto.OfficialLottoSearchRequest;
import boho.lottonumbergenerator.domain.dto.OfficialLottoSearchResponse;
import boho.lottonumbergenerator.domain.entity.lotto.OfficialLotto;
import boho.lottonumbergenerator.repository.OfficialLottoRepository;
import boho.lottonumbergenerator.service.OfficialLottoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfficialLottoServiceImpl implements OfficialLottoService {

	private final RestClient restClient;
	private final ObjectMapper objectMapper;
	private final OfficialLottoRepository officialLottoRepository;

	@Override
	@Transactional
	@CacheEvict(cacheNames = "winning_lotto", allEntries = true)
	public void fetchAllOfficialLottoIfNotFound() {
		if (!isOfficialLottoNotLoaded()) {
			log.info("Official lotto results already exist. Skipping initialization.");
			return;
		}

		List<OfficialLotto> officialLottoList = IntStream.iterate(1, i -> i + 1)
			.mapToObj(this::fetchRawLottoJsonFromApi)
			.takeWhile(rawBody -> rawBody.contains("\"returnValue\":\"success\""))
			.map(this::parseLottoJsonToResponse)
			.map(OfficialLotto::from)
			.toList();

		officialLottoRepository.saveAll(officialLottoList);
		log.info("Official lotto results saved up to draw number [{}]", officialLottoList.size());
	}

	@Override
	@Transactional
	public void fetchLatestOfficialLotto() {

		officialLottoRepository.findTopByOrderByDrawNumberDesc()
			.stream()
			.map(officialLotto -> fetchRawLottoJsonFromApi(officialLotto.getDrawNumber() + 1))
			.map(this::parseLottoJsonToResponse)
			.map(OfficialLotto::from)
			.forEach(officialLottoRepository::save);
	}

	@Override
	public Page<OfficialLottoSearchResponse> findOfficialLottoWithPaging(
		OfficialLottoSearchRequest request, Pageable pageable) {
		return officialLottoRepository.findOfficialLottoWithPaging(request, pageable);
	}

	@Override
	public boolean isOfficialLottoNotLoaded() {
		return !officialLottoRepository.existsByDrawNumberIsNotNull();
	}

	@Override
	@Cacheable(cacheNames = "winning_lotto", key = "#root.methodName")
	public OfficialLottoResponse getLatestOfficialLotto() {
		return OfficialLottoResponse.of(
			officialLottoRepository.findTopByOrderByDrawDateDesc()
			.orElseThrow(() -> new EntityNotFoundException("Official lotto results not found")));
	}

	private String fetchRawLottoJsonFromApi(Integer drawNumber) {
		return restClient.get()
			.uri(uriBuilder -> uriBuilder
				.path("/common.do")
				.queryParam("method", "getLottoNumber")
				.queryParam("drwNo", drawNumber)
				.build())
			.retrieve()
			.body(String.class);
	}

	private LottoApiResponse parseLottoJsonToResponse(String rawBody) {
		try {
			return objectMapper.readValue(rawBody, LottoApiResponse.class);
		} catch (JsonProcessingException e) {
			log.error("Failed to parse official lotto API json: {}", rawBody, e);
			throw new OfficialLottoParsingException("Failed to parse official lotto API json", e);
		}
	}
}
