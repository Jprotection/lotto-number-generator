package boho.lottonumbergenerator.domain.entity.member;

import java.util.Random;

import lombok.Getter;

@Getter
public enum GenderType {

	MALE("남성"), FEMALE("여성"), OTHER("기타");

	private final String displayName;

	GenderType(String displayName) {
		this.displayName = displayName;
	}

	public static GenderType random() {
		return values()[new Random().nextInt(values().length)];
	}
}
