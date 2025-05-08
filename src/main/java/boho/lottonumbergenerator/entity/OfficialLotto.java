package boho.lottonumbergenerator.entity;

import java.time.LocalDate;

import boho.lottonumbergenerator.dro.LottoApiResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "official_lottos")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficialLotto {

	@Id
	private Long drawNumber; // 회차 번호

	@Column(nullable = false)
	private LocalDate drawDate; // 추첨일 (yyyy-MM-dd)

	@Column(nullable = false)
	private Long totalSalesAmount; // 총 판매 금액

	@Column(nullable = false)
	private Integer firstPrizeWinnerCount; // 1등 당첨 수

	@Column(nullable = false)
	private Long firstPrizeAmount; // 1등 당첨 금액

	@Column(nullable = false)
	private Long totalFirstPrizeAmount; // 1등 총 당첨 금액

	@Column(nullable = false)
	private Integer firstNumber; // 1번 번호

	@Column(nullable = false)
	private Integer secondNumber; // 2번 번호

	@Column(nullable = false)
	private Integer thirdNumber; // 3번 번호

	@Column(nullable = false)
	private Integer fourthNumber; // 4번 번호

	@Column(nullable = false)
	private Integer fifthNumber; // 5번 번호

	@Column(nullable = false)
	private Integer sixthNumber; // 6번 번호

	@Column(nullable = false)
	private Integer bonusNumber; // 2등 보너스 번호

	public static OfficialLotto from(LottoApiResponse response) {
		return OfficialLotto.builder()
			.drawNumber(response.drawNumber())
			.drawDate(response.drawDate())
			.totalSalesAmount(response.totalSalesAmount())
			.firstPrizeWinnerCount(response.firstPrizeWinnerCount())
			.firstPrizeAmount(response.firstPrizeAmount())
			.totalFirstPrizeAmount(response.totalFirstPrizeAmount())
			.firstNumber(response.firstNumber())
			.secondNumber(response.secondNumber())
			.thirdNumber(response.thirdNumber())
			.fourthNumber(response.fourthNumber())
			.fifthNumber(response.fifthNumber())
			.sixthNumber(response.sixthNumber())
			.bonusNumber(response.bonusNumber())
			.build();
	}
}
