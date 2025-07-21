package boho.lottonumbergenerator.domain.entity;

import boho.lottonumbergenerator.domain.entity.member.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles_and_resources")
@Getter
@NoArgsConstructor
public class RoleResource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_and_resource_id")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk-role_resource_mappings-roles"))
	private Role role;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "resource_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk-role_resource_mappings-resources"))
	private Resource resource;

	public RoleResource(Role role, Resource resource) {
		this.role = role;
		this.resource = resource;
	}
}
