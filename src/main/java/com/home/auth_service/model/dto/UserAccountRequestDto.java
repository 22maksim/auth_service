package com.home.auth_service.model.dto;

import com.home.auth_service.model.enums.RoleUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountRequestDto {

    @Size(min = 4, max = 255)
    @NotBlank
    private String username;

    @Size(min = 4, max = 255)
    @NotBlank
    private String password;

    @Email
    private String email;

    @NotNull
    private RoleUser role;

}
