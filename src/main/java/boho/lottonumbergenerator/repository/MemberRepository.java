package boho.lottonumbergenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import boho.lottonumbergenerator.entity.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
