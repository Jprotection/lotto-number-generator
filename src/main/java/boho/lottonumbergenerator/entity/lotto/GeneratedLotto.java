package boho.lottonumbergenerator.entity.lotto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import boho.lottonumbergenerator.entity.member.Member;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk-generated_lottos-members"))
	private Member member;

	@CreationTimestamp
	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP(6)")
	private LocalDateTime createDate;

	@Builder.Default
	@Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
	private Integer prizeRank = 0;

	@Builder.Default
	@Column(nullable = false)
	private boolean isChecked = false;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "include_numbers", joinColumns = @JoinColumn(name = "generated_lotto_id"),
		foreignKey = @ForeignKey(name = "fk-generated_lottos-include_numbers"))
	@Column(name = "include_number")
	private List<Integer> includeNumbers;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "exclude_numbers", joinColumns = @JoinColumn(name = "generated_lotto_id"),
		foreignKey = @ForeignKey(name = "fk-generated_lottos-exclude_numbers"))
	@Column(name = "exclude_number")
	private List<Integer> excludeNumbers;

	public static GeneratedLotto from(
		Integer drawNumber, LocalDate drawDate, List<Integer> numbers, List<Integer> includeNumbers, List<Integer> excludeNumbers, Member creator) {

		return GeneratedLotto.builder()
			.drawNumber(drawNumber)
			.drawDate(drawDate)
			.member(creator)
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
		markAsChecked();
	}

	public void markAsChecked() {
		this.isChecked = true;
	}
}
