package boho.lottonumbergenerator.service;

import boho.lottonumbergenerator.domain.dto.MemberRegisterRequest;

public interface AuthService {

	void createAdminIfNotFound();

	void registerMember(MemberRegisterRequest request);

	void updateLastLoginDate(String username);
}
