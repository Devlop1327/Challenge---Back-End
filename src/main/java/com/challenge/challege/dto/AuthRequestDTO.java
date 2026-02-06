package com.challenge.challege.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class AuthRequestDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
