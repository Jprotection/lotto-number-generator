package boho.lottonumbergenerator.domain.entity.lotto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseLottoEntity {

	@Column(nullable = false)
	protected Integer drawNumber; // 추첨 회차

	@Column(nullable = false)
	protected LocalDate drawDate; // 추첨일 (yyyy-MM-dd)

	@Column(nullable = false)
	protected Integer firstNumber;

	@Column(nullable = false)
	protected Integer secondNumber;

	@Column(nullable = false)
	protected Integer thirdNumber;

	@Column(nullable = false)
	protected Integer fourthNumber;

	@Column(nullable = false)
	protected Integer fifthNumber;

	@Column(nullable = false)
	protected Integer sixthNumber;
}
