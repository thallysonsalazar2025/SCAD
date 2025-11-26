package br.com.scad.scad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //todo habilita a classe de auditoria do spring data jpa
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
