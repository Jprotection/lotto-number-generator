package boho.lottonumbergenerator.entity.member;

import lombok.Getter;

@Getter
public enum StatusType {

	ACTIVE("활동"),
	INACTIVE("휴면"),
	WITHDRAWN("탈퇴"),
	DELETED("삭제");

	private final String displayName;

	StatusType(String displayName) {
		this.displayName = displayName;
	}
}
