package boho.lottonumbergenerator.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneratedLotto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime createdAt;

	private Integer firstNumber;
	private Integer secondNumber;
	private Integer thirdNumber;
	private Integer fourthNumber;
	private Integer fifthNumber;
	private Integer sixthNumber;

	public static GeneratedLotto from(List<Integer> numbers) {
		return GeneratedLotto.builder()
			.createdAt(LocalDateTime.now())
			.firstNumber(numbers.getFirst())
			.secondNumber(numbers.get(1))
			.thirdNumber(numbers.get(2))
			.fourthNumber(numbers.get(3))
			.fifthNumber(numbers.get(4))
			.sixthNumber(numbers.get(5))
			.build();
	}
}
