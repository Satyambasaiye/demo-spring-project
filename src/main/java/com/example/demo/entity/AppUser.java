package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table (name = "app_users")
@Getter 
@Setter 
@NoArgsConstructor 
public class AppUser {
    @Id 
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    @Column (name = "id", updatable = false, nullable = false)
    private Long id;
    @Column (name = "email", unique = true, nullable = false)
    private String email;
    @Column (name = "password_hash", nullable = false)
    private String passwordHash;

}
