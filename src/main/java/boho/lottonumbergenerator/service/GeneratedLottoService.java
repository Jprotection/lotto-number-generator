package boho.lottonumbergenerator.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import boho.lottonumbergenerator.domain.dto.LottoGenerateRequest;
import boho.lottonumbergenerator.domain.dto.LottoGenerateResponse;
import boho.lottonumbergenerator.domain.dto.LottoListResponse;
import boho.lottonumbergenerator.domain.dto.WinningLottoRankGroupResponse;

public interface GeneratedLottoService {

	List<LottoGenerateResponse> generateLotto(LottoGenerateRequest request, Authentication authentication);

	List<LottoListResponse> getAllGeneratedLotto();

	WinningLottoRankGroupResponse fetchAllWinningLotto();
}
