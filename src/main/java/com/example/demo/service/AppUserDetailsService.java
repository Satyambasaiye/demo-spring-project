package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;

import jakarta.transaction.Transactional;

@Service 
@Transactional 
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository users;

    public AppUserDetailsService(AppUserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser account = users.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.withUsername(account.getEmail())
                .password(account.getPasswordHash())
                .roles("USER")
                .build();
    }
}
