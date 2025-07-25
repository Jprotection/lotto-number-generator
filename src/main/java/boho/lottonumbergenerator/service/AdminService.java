package boho.lottonumbergenerator.service;

import boho.lottonumbergenerator.domain.dto.AdminLottoGenerateRequest;

public interface AdminService {

	void lottoGenerateByAdmin(AdminLottoGenerateRequest request);
}
