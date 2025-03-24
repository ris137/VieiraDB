package io.vieira.app;

import io.vieira.config.VieiraServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = VieiraServiceConfiguration.class)
public class VieiraDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(VieiraDbApplication.class, args);
	}

}
