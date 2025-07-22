package boho.lottonumbergenerator.common.security;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import boho.lottonumbergenerator.repository.RoleResourceRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PersistentUrlRoleMapper {

	private final RoleResourceRepository roleResourceRepository;

	public Map<String, String> getUrlRoleMappings() {
		return roleResourceRepository.findAll()
			.stream()
			.collect(Collectors.toMap(
				roleResource -> roleResource.getResource().getResourceUrl(),
				roleResource -> roleResource.getRole().getRoleName()
			));
	}
}
