package boho.lottonumbergenerator.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class JasyptConfig {

	// Bean 이름은 반드시 jasyptStringEncryptor로 설정
	@Bean("jasyptStringEncryptor")
	public StringEncryptor stringEncryptor(Environment environment) {

		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(environment.getProperty("JASYPT_PASSWORD")); // 암호화할 때 사용하는 키
		config.setAlgorithm("PBEWithMD5AndDES"); // 암호화 알고리즘 default
		config.setKeyObtentionIterations("1000"); // 반복할 해싱 횟수 default
		config.setPoolSize("1"); // 인스턴스 pool default
		config.setProviderName("SunJCE"); // default
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator"); // salt 생성 클래스 default
		config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
		config.setStringOutputType("base64"); // 인코딩 방식

		encryptor.setConfig(config);

		return encryptor;
	}
}
