package boho.lottonumbergenerator.domain.entity.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long id;

	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String roleName;

	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_role_id", referencedColumnName = "role_id")
	private Role parent;

	public Role(String roleName, String description) {
		this.roleName = roleName;
		this.description = description;
	}

	public Role(String roleName, String description, Role parent) {
		this.roleName = roleName;
		this.description = description;
		this.parent = parent;
	}
}
