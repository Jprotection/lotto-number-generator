package boho.lottonumbergenerator.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import boho.lottonumbergenerator.domain.dto.OfficialLottoResponse;
import boho.lottonumbergenerator.domain.dto.OfficialLottoSearchRequest;
import boho.lottonumbergenerator.domain.dto.OfficialLottoSearchResponse;

public interface OfficialLottoService {

	void fetchAllOfficialLottoIfNotFound();

	void fetchLatestOfficialLotto();

	Page<OfficialLottoSearchResponse> findOfficialLottoWithPaging(OfficialLottoSearchRequest request, Pageable pageable);

	boolean isOfficialLottoNotLoaded();

	OfficialLottoResponse getLatestOfficialLotto();
}
