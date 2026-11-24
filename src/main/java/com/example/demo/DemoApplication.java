package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // String password = "admin123";

        // String hash = encoder.encode(password);

        // System.out.println("BCrypt Hash: " + hash);
	}
@Bean
PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
}
