package boho.lottonumbergenerator.domain.dto;

import java.util.List;
import java.util.Map;

import boho.lottonumbergenerator.domain.entity.lotto.WinningRank;
import lombok.Builder;

@Builder
public record WinningLottoRankGroupResponse(
	List<WinningLottoListResponse> firstPrizes,
	List<WinningLottoListResponse> secondPrizes,
	List<WinningLottoListResponse> thirdPrizes,
	List<WinningLottoListResponse> fourthPrizes,
	List<WinningLottoListResponse> fifthPrizes) {

	public static WinningLottoRankGroupResponse of(Map<WinningRank, List<WinningLottoListResponse>> resultMap) {
		return WinningLottoRankGroupResponse.builder()
			.firstPrizes(resultMap.getOrDefault(WinningRank.FIRST, List.of()))
			.secondPrizes(resultMap.getOrDefault(WinningRank.SECOND, List.of()))
			.thirdPrizes(resultMap.getOrDefault(WinningRank.THIRD, List.of()))
			.fourthPrizes(resultMap.getOrDefault(WinningRank.FOURTH, List.of()))
			.fifthPrizes(resultMap.getOrDefault(WinningRank.FIFTH, List.of()))
			.build();
	}

	public boolean isEmptyAll() {
		return firstPrizes.isEmpty()
			&& secondPrizes.isEmpty()
			&& thirdPrizes.isEmpty()
			&& fourthPrizes.isEmpty()
			&& fifthPrizes.isEmpty();
	}
}
