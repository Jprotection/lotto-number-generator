package boho.lottonumbergenerator.entity.member;

import lombok.Getter;

@Getter
public enum AuthorityType {

	ROLE_MEMBER("회원"), ROLE_ADMIN("관리자");

	private final String displayName;

	AuthorityType(String displayName) {
		this.displayName = displayName;
	}
}
