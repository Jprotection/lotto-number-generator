package boho.lottonumbergenerator.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record LottoApiResponse(

	@JsonProperty("drwNo") // 회차 번호
	Long drawNumber,

	@JsonProperty("drwNoDate") // 추첨일 (yyyy-MM-dd)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate drawDate,

	@JsonProperty("totSellamnt") // 총 판매 금액
	Long totalSalesAmount,

	@JsonProperty("firstPrzwnerCo") // 1등 당첨 수
	Integer firstPrizeWinnerCount,

	@JsonProperty("firstWinamnt") // 1등 당첨 금액
	Long firstPrizeAmount,

	@JsonProperty("firstAccumamnt") // 1등 총 당첨 금액
	Long totalFirstPrizeAmount,

	@JsonProperty("drwtNo1") // 1번 번호
	Integer firstNumber,

	@JsonProperty("drwtNo2") // 2번 번호
	Integer secondNumber,

	@JsonProperty("drwtNo3") // 3번 번호
	Integer thirdNumber,

	@JsonProperty("drwtNo4") // 4번 번호
	Integer fourthNumber,

	@JsonProperty("drwtNo5") // 5번 번호
	Integer fifthNumber,

	@JsonProperty("drwtNo6") // 6번 번호
	Integer sixthNumber,

	@JsonProperty("bnusNo") // 2등 보너스 번호
	Integer bonusNumber
) {
}
