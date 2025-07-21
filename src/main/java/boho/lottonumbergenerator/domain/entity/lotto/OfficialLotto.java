package boho.lottonumbergenerator.domain.entity.lotto;

import java.util.Set;

import boho.lottonumbergenerator.domain.dto.LottoApiResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "official_lottos")
@Getter
@SuperBuilder
@NoArgsConstructor
public class OfficialLotto extends BaseLottoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "official_lotto_id")
	private Long id;

	@Column(nullable = false)
	private Long totalSalesAmount; // 총 판매 금액

	@Column(nullable = false)
	private Integer firstPrizeWinnerCount; // 1등 당첨 수

	@Column(nullable = false)
	private Long firstPrizeAmount; // 1등 당첨 금액

	@Column(nullable = false)
	private Long totalFirstPrizeAmount; // 1등 총 당첨 금액

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

	public Set<Integer> toNumberSet() {
		return Set.of(
			firstNumber,
			secondNumber,
			thirdNumber,
			fourthNumber,
			fifthNumber,
			sixthNumber
		);
	}
}
