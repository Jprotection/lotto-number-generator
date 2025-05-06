package boho.lottonumbergenerator.entity;

import java.time.LocalDate;

import boho.lottonumbergenerator.dro.LottoApiResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficialLotto {

	@Id
	private Integer drawNumber; // 회차 번호

	private boolean resultStatus;

	private LocalDate drawDate; // 추첨일 (yyyy-MM-dd)

	private Long totalSalesAmount; // 총 판매 금액

	private Integer firstPrizeWinnerCount; // 1등 당첨 인원 수

	private Long firstPrizeAmount; // 1등 1인당 당첨 금액

	private Long firstPrizeTotalAmount; // 1등 누적금

	private Integer number1; // 1번 번호

	private Integer number2; // 2번 번호

	private Integer number3; // 3번 번호

	private Integer number4; // 4번 번호

	private Integer number5; // 5번 번호

	private Integer number6; // 6번 번호

	private Integer bonusNumber; // 보너스 번호

	public static OfficialLotto from(LottoApiResponse response) {
		return OfficialLotto.builder()
			.drawNumber(response.drawNumber())
			.resultStatus(Boolean.parseBoolean(response.resultStatus()))
			.drawDate(response.drawDate())
			.totalSalesAmount(response.totalSalesAmount())
			.firstPrizeWinnerCount(response.firstPrizeWinnerCount())
			.firstPrizeAmount(response.firstPrizeAmount())
			.firstPrizeTotalAmount(response.firstPrizeTotalAmount())
			.number1(response.number1())
			.number2(response.number2())
			.number3(response.number3())
			.number4(response.number4())
			.number5(response.number5())
			.number6(response.number6())
			.bonusNumber(response.bonusNumber())
			.build();
	}
}
