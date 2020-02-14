package ng.com.neodezigns.app.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ng.com.neodezigns.app.ws.Security.AppProperties;

@SpringBootApplication
public class NeoDezignsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeoDezignsApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPassword() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext(); 
	}
	
	@Bean(name="AppProperties")
	public AppProperties appProperties() {
		return new AppProperties(); 
	}
}
