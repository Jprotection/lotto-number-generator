package boho.lottonumbergenerator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import boho.lottonumbergenerator.domain.entity.lotto.OfficialLotto;

public interface OfficialLottoRepository extends JpaRepository<OfficialLotto, Long>, OfficialLottoCustomRepository {
	Optional<OfficialLotto> findTopByOrderByDrawDateDesc();

	Optional<OfficialLotto> findTopByOrderByDrawNumberDesc();

	boolean existsByDrawNumberIsNotNull();
}
