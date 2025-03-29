package com.home.auth_service.controller;

import com.home.auth_service.model.dto.UserAccountRequestDto;
import com.home.auth_service.model.dto.UserAccountResponseDto;
import com.home.auth_service.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("api/v1/security")
@RequiredArgsConstructor
public class AuthController {
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/register/user")
    public ResponseEntity<UserAccountResponseDto> registerUser(
            @RequestBody @Valid @NotNull UserAccountRequestDto requestDto
    ) {
        UserAccountResponseDto responseDto = userDetailsServiceImpl.registerUser(requestDto);

        return null;
    }

    @GetMapping("/login")
    public ResponseEntity<UserAccountResponseDto> login(
            @RequestParam(name = "email") @NotBlank @Size(min = 4, max = 255) String email,
            @RequestParam(name = "password") @NotBlank @Size(min = 8, max = 255) String password
    ) {
        return null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/admin")
    public ResponseEntity<UserAccountResponseDto> registerAdmin(
            @RequestBody @Valid @NotNull UserAccountRequestDto requestDto
    ) {

        return null;
    }

    @PostMapping("/recover-password")
    public ResponseEntity<UserAccountResponseDto> recoverPassword(
            @RequestParam(name = "firstname") @NotBlank @Size(min = 4, max = 255) String firstname,
            @RequestParam(name = "lastname") @NotBlank @Size(min = 4, max = 255) String lastname,
            @RequestParam(name = "email") @NotBlank @Size(min = 8, max = 255) String email
    ) {

        return null;
    }
}
