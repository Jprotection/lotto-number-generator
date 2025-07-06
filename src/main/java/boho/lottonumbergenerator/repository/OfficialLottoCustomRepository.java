package boho.lottonumbergenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import boho.lottonumbergenerator.dto.OfficialLottoSearchRequest;
import boho.lottonumbergenerator.dto.OfficialLottoSearchResponse;

public interface OfficialLottoCustomRepository {

	Page<OfficialLottoSearchResponse> findOfficialLottoWithPaging(
		OfficialLottoSearchRequest request, Pageable pageable);
}
