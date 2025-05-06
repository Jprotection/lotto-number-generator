package boho.lottonumbergenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import boho.lottonumbergenerator.entity.OfficialLotto;

public interface OfficialLottoRepository extends JpaRepository<OfficialLotto, Integer> {
}
