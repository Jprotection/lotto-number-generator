package boho.lottonumbergenerator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import boho.lottonumbergenerator.entity.lotto.OfficialLotto;

public interface OfficialLottoRepository extends JpaRepository<OfficialLotto, Long> {
	List<OfficialLotto> findTop2ByOrderByDrawDateDesc();

	boolean existsByDrawNumberIsNotNull();
}
