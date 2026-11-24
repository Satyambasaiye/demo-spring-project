package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AppUserRepository  extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);


}
