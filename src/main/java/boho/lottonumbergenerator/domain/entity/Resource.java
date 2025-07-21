package boho.lottonumbergenerator.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resources")
@Getter
@NoArgsConstructor
public class Resource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "resource_id")
	private Long id;

	@Column(nullable = false, columnDefinition = "VARCHAR(30)")
	private String resourceUrl;
}
