package boho.lottonumbergenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import boho.lottonumbergenerator.domain.entity.RoleResource;

public interface RoleResourceRepository extends JpaRepository<RoleResource, Long> {
}
