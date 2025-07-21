package boho.lottonumbergenerator.domain.entity.member;

import lombok.Getter;

@Getter
public enum GenderType {

	MALE("남성"), FEMALE("여성"), OTHER("기타");

	private final String displayName;

	GenderType(String displayName) {
		this.displayName = displayName;
	}
}
