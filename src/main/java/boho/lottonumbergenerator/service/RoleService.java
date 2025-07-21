package boho.lottonumbergenerator.service;

import boho.lottonumbergenerator.domain.entity.member.Role;

public interface RoleService {

	void createDefaultRolesIfNotFound();

	Role getAdminRole();

	Role getMemberRole();
}
