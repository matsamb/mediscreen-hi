package com.medi.historic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.medi.historic.config.MedConfigs;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableMongoRepositories
@Log4j2
public class HistoricApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HistoricApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {

		log.info(medConfigs.getAngularurl());
		
	}
	
	@Autowired
	private MedConfigs medConfigs;
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				
				registry
					.addMapping("/patient")
					.allowedOrigins(medConfigs.getAngularurl()/*"localhost:4200""${medconfigs.angularurl}"*/);
			}
		};
	}

}
