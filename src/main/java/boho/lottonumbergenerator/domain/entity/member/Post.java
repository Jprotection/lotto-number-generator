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
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "posts")
@Getter
@SuperBuilder
@NoArgsConstructor
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk-posts-members"))
	private Member member;

	@Column(nullable = false, columnDefinition = "VARCHAR(100)")
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;
}
