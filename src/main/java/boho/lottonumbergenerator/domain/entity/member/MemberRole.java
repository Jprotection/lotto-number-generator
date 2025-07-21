package boho.lottonumbergenerator.domain.entity.member;

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
@Table(name = "members_and_roles")
@Getter
@NoArgsConstructor
public class MemberRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_and_role_id")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk-members_and_roles-members"))
	private Member member;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk-members_and_roles-roles"))
	private Role role;

	public MemberRole(Member member, Role role) {
		this.member = member;
		this.role = role;
	}
}
