package boho.lottonumbergenerator.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import boho.lottonumbergenerator.entity.GeneratedLotto;

public interface GeneratedLottoRepository extends JpaRepository<GeneratedLotto, Long> {
	List<GeneratedLotto> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
