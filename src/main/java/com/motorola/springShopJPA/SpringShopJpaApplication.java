package com.motorola.springShopJPA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SpringShopJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringShopJpaApplication.class, args);
		openHomePage();
	}

	private static void openHomePage() {
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://localhost:8080/home");
		} catch (IOException e) {
			throw new RuntimeException("Unable to open homepage");
		}
	}

}
