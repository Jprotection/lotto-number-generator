package boho.lottonumbergenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import boho.lottonumbergenerator.entity.member.Title;

public interface TitleRepository extends JpaRepository<Title, Long> {
}
