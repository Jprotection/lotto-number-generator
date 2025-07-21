package boho.lottonumbergenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import boho.lottonumbergenerator.domain.entity.member.MemberTitle;

public interface MemberTitleRepository extends JpaRepository<MemberTitle, Long> {
}
