package boho.lottonumbergenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import boho.lottonumbergenerator.entity.GeneratedLotto;

public interface GeneratedLottoRepository extends JpaRepository<GeneratedLotto, Long> {
}
