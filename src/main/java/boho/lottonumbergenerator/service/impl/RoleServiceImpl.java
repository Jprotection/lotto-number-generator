package boho.lottonumbergenerator.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boho.lottonumbergenerator.domain.entity.member.Role;
import boho.lottonumbergenerator.repository.RoleRepository;
import boho.lottonumbergenerator.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String ROLE_MEMBER = "ROLE_MEMBER";
	private final RoleRepository roleRepository;

	@Override
	@Transactional
	public void createDefaultRolesIfNotFound() {
		roleRepository.findByRoleName(ROLE_ADMIN)
			.ifPresentOrElse(this::logRoleAlreadyExists,
				() -> logRoleCreated(roleRepository.save(new Role(ROLE_ADMIN, "관리자"))));

		roleRepository.findByRoleName(ROLE_MEMBER)
			.ifPresentOrElse(this::logRoleAlreadyExists,
				() -> logRoleCreated(
					roleRepository.save(new Role(ROLE_MEMBER, "회원", getAdminRole()))));
	}

	@Override
	public Role getAdminRole() {
		return roleRepository.findByRoleName(ROLE_ADMIN)
			.orElseThrow(() -> new EntityNotFoundException("[" + ROLE_ADMIN + "] role not found"));
	}

	@Override
	public Role getMemberRole() {
		return roleRepository.findByRoleName(ROLE_MEMBER)
			.orElseThrow(() -> new EntityNotFoundException("[" + ROLE_MEMBER + "] role not found"));
	}

	@Override
	public List<String> getAllRoleDescriptions() {
		return roleRepository.findAll()
			.stream()
			.map(Role::getDescription)
			.toList();
	}

	@Override
	public String buildRoleHierarchy() {
		return roleRepository.findAll()
			.stream()
			.filter(role -> role.getParent() != null)
			.map(role -> role.getParent().getRoleName() + " > " + role.getRoleName())
			.collect(Collectors.joining("\n"));
	}

	private void logRoleAlreadyExists(Role role) {
		log.info("[{}] role already exists", role.getRoleName());
	}

	private void logRoleCreated(Role role) {
		log.info("[{}] role created", role.getRoleName());
	}
}
