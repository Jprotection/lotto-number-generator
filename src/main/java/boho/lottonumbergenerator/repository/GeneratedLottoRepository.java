package boho.lottonumbergenerator.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import boho.lottonumbergenerator.domain.entity.lotto.GeneratedLotto;

public interface GeneratedLottoRepository extends JpaRepository<GeneratedLotto, Long> {
	List<GeneratedLotto> findByCreateDateBetween(LocalDateTime startDate, LocalDateTime endDate);

	List<GeneratedLotto> findByPrizeRank(Integer prizeRank);
}
