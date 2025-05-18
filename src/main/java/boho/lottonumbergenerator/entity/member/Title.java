package boho.lottonumbergenerator.entity.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "titles")
@Getter
@NoArgsConstructor
public class Title {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "title_id")
	private Long id;

	@Column(nullable = false, columnDefinition = "VARCHAR(30)")
	private String name;

	public Title(String name) {
		this.name = name;
	}
}
