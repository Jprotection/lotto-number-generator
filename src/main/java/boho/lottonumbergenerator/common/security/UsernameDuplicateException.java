package boho.lottonumbergenerator.common.security;

public class UsernameDuplicateException extends RuntimeException {
	public UsernameDuplicateException(String message) {
		super(message);
	}
}
