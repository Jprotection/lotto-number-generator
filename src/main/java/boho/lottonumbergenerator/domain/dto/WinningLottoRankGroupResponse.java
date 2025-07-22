package boho.lottonumbergenerator.domain.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record WinningLottoRankGroupResponse(
	List<WinningLottoListResponse> firstPrizes,
	List<WinningLottoListResponse> secondPrizes,
	List<WinningLottoListResponse> thirdPrizes,
	List<WinningLottoListResponse> fourthPrizes,
	List<WinningLottoListResponse> fifthPrizes) {

	public static WinningLottoRankGroupResponse of(
		List<WinningLottoListResponse> firstPrizes,
		List<WinningLottoListResponse> secondPrizes,
		List<WinningLottoListResponse> thirdPrizes,
		List<WinningLottoListResponse> fourthPrizes,
		List<WinningLottoListResponse> fifthPrizes) {

		return WinningLottoRankGroupResponse.builder()
			.firstPrizes(firstPrizes)
			.secondPrizes(secondPrizes)
			.thirdPrizes(thirdPrizes)
			.fourthPrizes(fourthPrizes)
			.fifthPrizes(fifthPrizes)
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
