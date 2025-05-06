package boho.lottonumbergenerator.dro;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record LottoApiResponse(
	@JsonProperty("returnValue") // 요청 결과 (success / fail)
	String resultStatus,

	@JsonProperty("drwNo") // 회차 번호
	Integer drawNumber,

	@JsonProperty("drwNoDate") // 추첨일 (yyyy-MM-dd)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate drawDate,

	@JsonProperty("totSellamnt") // 총 판매 금액
	Long totalSalesAmount,

	@JsonProperty("firstPrzwnerCo") // 1등 당첨 인원 수
	Integer firstPrizeWinnerCount,

	@JsonProperty("firstWinamnt") // 1등 1인당 당첨 금액
	Long firstPrizeAmount,

	@JsonProperty("firstAccumamnt") // 1등 누적금
	Long firstPrizeTotalAmount,

	@JsonProperty("drwtNo1") // 1번 번호
	Integer number1,

	@JsonProperty("drwtNo2") // 2번 번호
	Integer number2,

	@JsonProperty("drwtNo3") // 3번 번호
	Integer number3,

	@JsonProperty("drwtNo4") // 4번 번호
	Integer number4,

	@JsonProperty("drwtNo5") // 5번 번호
	Integer number5,

	@JsonProperty("drwtNo6") // 6번 번호
	Integer number6,

	@JsonProperty("bnusNo") // 보너스 번호
	Integer bonusNumber
) {
}
