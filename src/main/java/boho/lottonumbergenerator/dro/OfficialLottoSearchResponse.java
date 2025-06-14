package boho.lottonumbergenerator.dro;

import java.time.LocalDate;

public record OfficialLottoSearchResponse(
	Long DrawNumber,
	LocalDate DrawDate,
	Integer firstPrizeWinnerCount,
	Long firstPrizeAmount,
	Integer firstNumber,
	Integer secondNumber,
	Integer thirdNumber,
	Integer fourthNumber,
	Integer fifthNumber,
	Integer sixthNumber,
	Integer bonusNumber) {
}
