package boho.lottonumbergenerator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import boho.lottonumbergenerator.domain.entity.member.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleName(String roleName);

	Optional<Role> findByDescription(String description);
}
