package boho.lottonumbergenerator.domain.dto;

import java.time.LocalDate;

public record OfficialLottoSearchResponse(
	Integer drawNumber,
	LocalDate drawDate,
	Integer firstPrizeWinnerCount,
	Long firstPrizeAmount,
	Long totalSalesAmount,
	Integer firstNumber,
	Integer secondNumber,
	Integer thirdNumber,
	Integer fourthNumber,
	Integer fifthNumber,
	Integer sixthNumber,
	Integer bonusNumber) {
}
