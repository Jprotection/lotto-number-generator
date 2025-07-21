package boho.lottonumbergenerator.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import boho.lottonumbergenerator.domain.entity.member.MemberRole;
import boho.lottonumbergenerator.domain.entity.member.Role;
import boho.lottonumbergenerator.domain.entity.member.Member;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

	List<MemberRole> findByMember(Member member);

	Optional<MemberRole> findByMemberAndRole(Member member, Role role);
}
