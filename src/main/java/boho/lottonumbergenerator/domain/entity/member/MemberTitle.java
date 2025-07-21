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
@Table(name = "members_and_titles")
@Getter
@NoArgsConstructor
public class MemberTitle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_and_title_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk-members_and_titles-members"))
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "title_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk-members_and_titles-titles"))
	private Title title;

	public MemberTitle(Member member, Title title) {
		this.member = member;
		this.title = title;
	}
}
