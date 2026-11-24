package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class LoginRequest {
    @Email 
private String email;
@JsonProperty (access=JsonProperty.Access.READ_ONLY)
private String password;
}
