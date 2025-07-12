package boho.lottonumbergenerator.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import boho.lottonumbergenerator.entity.lotto.OfficialLotto;

public interface OfficialLottoRepository extends JpaRepository<OfficialLotto, Long>, OfficialLottoCustomRepository {
	List<OfficialLotto> findTop2ByOrderByDrawDateDesc();

	Optional<OfficialLotto> findTopByOrderByDrawDateDesc();

	boolean existsByDrawNumberIsNotNull();
}
