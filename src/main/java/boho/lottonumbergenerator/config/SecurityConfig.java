package boho.lottonumbergenerator.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import boho.lottonumbergenerator.common.security.MemberAccessDeniedHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final AuthenticationProvider authenticationProvider;
	private final AuthenticationSuccessHandler successHandler;
	private final AuthenticationFailureHandler failureHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
			.authorizeHttpRequests(auth -> auth
				// 정적 자원 접근 허용
				// CSS("/css/**"), JAVA_SCRIPT("/js/**"), IMAGES("/images/**"), WEB_JARS("/webjars/**"), FAVICON("/favicon.*", "/*/icon-*")
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.requestMatchers("/members/**").authenticated()
				.requestMatchers("/logout","/denied").authenticated()
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll())
			.formLogin(form -> form
				.loginPage("/login")
				.successHandler(successHandler)
				.failureHandler(failureHandler)
				.permitAll())
			.authenticationProvider(authenticationProvider)
			.exceptionHandling(exception -> exception
				.accessDeniedHandler(new MemberAccessDeniedHandler("/denied")));

		return http.build();
	}
}
