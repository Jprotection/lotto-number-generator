package boho.lottonumbergenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import boho.lottonumbergenerator.domain.dto.OfficialLottoSearchRequest;
import boho.lottonumbergenerator.domain.dto.OfficialLottoSearchResponse;

public interface OfficialLottoCustomRepository {

	Page<OfficialLottoSearchResponse> findOfficialLottoWithPaging(
		OfficialLottoSearchRequest request, Pageable pageable);
}
