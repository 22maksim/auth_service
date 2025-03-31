package com.home.auth_service.controller;

import com.home.auth_service.model.dto.UserAccountRequestDto;
import com.home.auth_service.model.dto.UserAccountResponseDto;
import com.home.auth_service.service.JwtUtil;
import com.home.auth_service.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

        Map<String, Object> claims = Map.of(
                "role", responseDto.getRole().name(),
                "status", responseDto.getUserAccountStatus().name()
        );
        String token = JwtUtil.generateToken(responseDto.getUsername(), claims);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        return new ResponseEntity<>(responseDto, headers, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<UserAccountResponseDto> login(
            @RequestParam(name = "username") @NotBlank @Size(min = 4, max = 255) String username,
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
            @RequestParam(name = "username") @NotBlank @Size(min = 4, max = 255) String username,
            @RequestParam(name = "email") @NotBlank @Size(min = 8, max = 255) String email
    ) {

        return null;
    }
}
