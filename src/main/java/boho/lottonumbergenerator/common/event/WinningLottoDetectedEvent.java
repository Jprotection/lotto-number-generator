package boho.lottonumbergenerator.common.event;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationEvent;

import boho.lottonumbergenerator.domain.dto.WinningLottoListResponse;
import boho.lottonumbergenerator.domain.entity.lotto.WinningRank;
import lombok.Getter;

@Getter
public class WinningLottoDetectedEvent extends ApplicationEvent {

	private final Map<WinningRank, List<WinningLottoListResponse>> winningLotto;

	public WinningLottoDetectedEvent(Object source, Map<WinningRank, List<WinningLottoListResponse>> winningLotto) {
		super(source);
		this.winningLotto = winningLotto;
	}
}
