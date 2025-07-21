package boho.lottonumbergenerator.domain.entity.member;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor
public class BaseEntity {

	@CreationTimestamp
	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP(6)")
	protected LocalDateTime createDate;

	@Column(columnDefinition = "TIMESTAMP(6)")
	protected LocalDateTime updateDate;
}
