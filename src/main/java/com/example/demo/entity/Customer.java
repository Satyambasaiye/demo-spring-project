package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    protected Customer() {}

    public Long getId() { return id; }
    public String getEmail() { return email; }
}