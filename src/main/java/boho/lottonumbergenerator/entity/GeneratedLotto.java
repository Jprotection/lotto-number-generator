package boho.lottonumbergenerator.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "generated_lottos")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneratedLotto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "generated_lotto_id")
	private Long id;

	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private Integer firstNumber;

	@Column(nullable = false)
	private Integer secondNumber;

	@Column(nullable = false)
	private Integer thirdNumber;

	@Column(nullable = false)
	private Integer fourthNumber;

	@Column(nullable = false)
	private Integer fifthNumber;

	@Column(nullable = false)
	private Integer sixthNumber;

	public static GeneratedLotto from(List<Integer> numbers) {
		return GeneratedLotto.builder()
			.firstNumber(numbers.getFirst())
			.secondNumber(numbers.get(1))
			.thirdNumber(numbers.get(2))
			.fourthNumber(numbers.get(3))
			.fifthNumber(numbers.get(4))
			.sixthNumber(numbers.get(5))
			.build();
	}
}
