package boho.lottonumbergenerator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class WebConfig {

	// GET POST만 가능한 <form>에서 _method 라는 숨겨진 필드를 통해 다른 Http 메서드로 오버라이딩 가능
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}
}
