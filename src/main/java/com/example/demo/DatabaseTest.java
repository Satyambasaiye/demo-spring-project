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
                "SELECT current_database()",
                String.class
            );
            String result1 = jdbcTemplate.queryForObject(
                "SELECT current_schema()",
                String.class
            );
            System.out.println("Connected Schema: " + result1);

            System.out.println("Connected Database: " + result);
        };
    }
}
