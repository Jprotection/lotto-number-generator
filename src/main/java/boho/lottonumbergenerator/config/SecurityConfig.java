package boho.lottonumbergenerator.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
			.authorizeHttpRequests(auth -> auth
				// StaticResourceLocation - 정적 자원 접근 허용
				// CSS("/css/**"), JAVA_SCRIPT("/js/**"), IMAGES("/images/**"), WEB_JARS("/webjars/**"), FAVICON("/favicon.*", "/*/icon-*")
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.anyRequest().permitAll())
				// .anyRequest().authenticated())
			.formLogin(form -> form
				.loginPage("/login")
				.permitAll());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// 기본적으로 Bcrypt 방식의 암호화 사용
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("useruser").password("{noop}useruser").roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}
}
