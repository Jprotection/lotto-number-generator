package boho.lottonumbergenerator.entity.lotto;

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
