package boho.lottonumbergenerator.entity.member;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import boho.lottonumbergenerator.dto.MemberRegisterRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String username;

	@Column(nullable = false, columnDefinition = "VARCHAR(100)")
	private String password;

	@Column(nullable = false, columnDefinition = "VARCHAR(10)")
	@Enumerated(EnumType.STRING)
	private GenderType gender;

	@Builder.Default
	@Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'MEMBER'")
	@Enumerated(EnumType.STRING)
	private AuthorityType authority = AuthorityType.ROLE_MEMBER;

	@Builder.Default
	@Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE'")
	@Enumerated(EnumType.STRING)
	private StatusType status = StatusType.ACTIVE;

	@CreationTimestamp
	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP(6)")
	private LocalDateTime createDate;

	@Column(columnDefinition = "TIMESTAMP(6)")
	private LocalDateTime lastLoginDate;

	@Column(columnDefinition = "TIMESTAMP(6)")
	private LocalDateTime withdrawDate;

	public static Member from(MemberRegisterRequest request) {
		return Member.builder()
			.username(request.username())
			.password(request.password())
			.gender(request.gender())
			.build();
	}
}
