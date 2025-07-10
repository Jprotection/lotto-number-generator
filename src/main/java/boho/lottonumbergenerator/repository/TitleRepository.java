package boho.lottonumbergenerator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import boho.lottonumbergenerator.entity.member.Title;

public interface TitleRepository extends JpaRepository<Title, Long> {
	boolean existsByIdIsNotNull();

	Optional<Title> findByName(String name);
}
