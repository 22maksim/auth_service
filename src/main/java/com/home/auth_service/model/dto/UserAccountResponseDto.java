package com.home.auth_service.model.dto;

import com.home.auth_service.model.enums.RoleUser;
import com.home.auth_service.model.enums.UserAccountStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountResponseDto {
    private UUID id;
    private String username;
    private String email;
    private RoleUser role;
    private UserAccountStatus userAccountStatus;
    private String createdAt;
    private String updatedAt;
}
