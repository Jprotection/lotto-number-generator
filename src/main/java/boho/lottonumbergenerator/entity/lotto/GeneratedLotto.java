package boho.lottonumbergenerator.entity.lotto;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "generated_lottos")
@Getter
@SuperBuilder
@NoArgsConstructor
public class GeneratedLotto extends BaseLottoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "generated_lotto_id")
	private Long id;

	@CreationTimestamp
	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP(6)")
	private LocalDateTime createDate;

	@Builder.Default
	@Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
	private Integer prizeRank = 0;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "include_numbers", joinColumns = @JoinColumn(name = "generated_lotto_id"))
	@Column(name = "include_number")
	private List<Integer> includeNumbers;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "exclude_numbers", joinColumns = @JoinColumn(name = "generated_lotto_id"))
	@Column(name = "exclude_number")
	private List<Integer> excludeNumbers;

	public static GeneratedLotto from(
		List<Integer> numbers, List<Integer> includeNumbers, List<Integer> excludeNumbers) {

		return GeneratedLotto.builder()
			.firstNumber(numbers.getFirst())
			.secondNumber(numbers.get(1))
			.thirdNumber(numbers.get(2))
			.fourthNumber(numbers.get(3))
			.fifthNumber(numbers.get(4))
			.sixthNumber(numbers.get(5))
			.includeNumbers(includeNumbers)
			.excludeNumbers(excludeNumbers)
			.build();
	}

	public List<Integer> toNumberList() {
		return List.of(
			firstNumber,
			secondNumber,
			thirdNumber,
			fourthNumber,
			fifthNumber,
			sixthNumber
		);
	}

	public void updatePrizeRank(Integer prizeRank) {
		this.prizeRank = prizeRank;
	}
}
