package boho.lottonumbergenerator.config.security;

public class UsernameDuplicateException extends RuntimeException {
	public UsernameDuplicateException(String message) {
		super(message);
	}
}
