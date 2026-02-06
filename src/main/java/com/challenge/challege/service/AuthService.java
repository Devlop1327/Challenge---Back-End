package com.challenge.challege.service;

import com.challenge.challege.dto.AuthRequestDTO;
import com.challenge.challege.dto.AuthResponseDTO;
import com.challenge.challege.dto.RegisterRequestDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterRequestDTO request);
    AuthResponseDTO authenticate(AuthRequestDTO request);
}
