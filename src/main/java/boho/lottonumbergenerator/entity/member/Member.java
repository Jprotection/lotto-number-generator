package boho.lottonumbergenerator.entity.member;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
	@Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'MEMBER'")
	@Enumerated(EnumType.STRING)
	private AuthorityType authority = AuthorityType.MEMBER;

	@Builder.Default
	@Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE'")
	@Enumerated(EnumType.STRING)
	private StatusType status = StatusType.ACTIVE;

	@CreationTimestamp
	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP(6)")
	private LocalDateTime createdAt;

	@Column(columnDefinition = "TIMESTAMP(6)")
	private LocalDateTime lastLoginAt;

	@Column(columnDefinition = "TIMESTAMP(6)")
	private LocalDateTime withdrawnAt;
}
