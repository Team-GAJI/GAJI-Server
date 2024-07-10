package gaji.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.TimeZone;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ServiceApplication {

	public static void main(String[] args) {
		//TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		SpringApplication.run(ServiceApplication.class, args);
	}

}
