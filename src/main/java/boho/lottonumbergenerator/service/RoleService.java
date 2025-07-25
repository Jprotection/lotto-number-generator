package boho.lottonumbergenerator.service;

import java.util.List;

import boho.lottonumbergenerator.domain.entity.member.Role;

public interface RoleService {

	void createDefaultRolesIfNotFound();

	Role getAdminRole();

	Role getMemberRole();

	List<String> getAllRoleDescriptions();

	String buildRoleHierarchy();
}
