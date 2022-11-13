package com.medi.historic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableMongoRepositories
public class HistoricApplication {

	public static void main(String[] args) {
		SpringApplication.run(HistoricApplication.class, args);
	}

}
