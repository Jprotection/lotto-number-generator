package boho.lottonumbergenerator.common.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import boho.lottonumbergenerator.repository.RoleResourceRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PersistentUrlRoleMapper {

	private final RoleResourceRepository roleResourceRepository;

	public Map<String, String> getUrlRoleMappings() {
		Map<String, String> urlRoleMappings = new HashMap<>();

		roleResourceRepository.findAll()
			.forEach(roleResource ->  urlRoleMappings.put(
				roleResource.getResource().getResourceUrl(), roleResource.getRole().getRoleName()));

		return urlRoleMappings;
	}
}
