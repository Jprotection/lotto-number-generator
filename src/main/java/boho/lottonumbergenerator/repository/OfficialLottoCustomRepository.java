package boho.lottonumbergenerator.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import boho.lottonumbergenerator.dro.OfficialLottoSearchRequest;
import boho.lottonumbergenerator.dro.OfficialLottoSearchResponse;

public interface OfficialLottoCustomRepository {

	Page<OfficialLottoSearchResponse> findOfficialLottoWithPaging(
		OfficialLottoSearchRequest request, Pageable pageable);
}
