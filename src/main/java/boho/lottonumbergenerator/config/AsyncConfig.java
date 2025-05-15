package boho.lottonumbergenerator.config;

import java.time.Duration;

import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

	// @Async는 기본적으로 "taskExecutor" Bean을 찾는다.
	@Bean(name = "taskExecutor")
	public ThreadPoolTaskExecutor threadPoolTaskExecutor(ThreadPoolTaskExecutorBuilder builder) {
		int cores = Runtime.getRuntime().availableProcessors();

		return builder
			.corePoolSize(cores * 2)
			.maxPoolSize(cores * 4)
			.queueCapacity(100)
			.keepAlive(Duration.ofSeconds(30))
			.threadNamePrefix("OptimizedAsyncExecutor-")
			.build();
	}
}
