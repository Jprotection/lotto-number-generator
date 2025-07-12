package boho.lottonumbergenerator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		// 기본적으로 Bcrypt 방식의 암호화 사용
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
