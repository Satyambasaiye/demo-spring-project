package com.example.demo;
// package com.example.employeeservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseTest {

    @Bean
    CommandLineRunner testDatabase(JdbcTemplate jdbcTemplate) {
        return args -> {

            String result = jdbcTemplate.queryForObject(
                "SELECT DATABASE()",
                String.class
            );
            

            System.out.println("Connected Database: " + result);
        };
    }
}
