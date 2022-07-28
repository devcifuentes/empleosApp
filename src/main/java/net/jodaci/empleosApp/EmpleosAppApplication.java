package net.jodaci.empleosApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class EmpleosAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpleosAppApplication.class, args);
	}

}
