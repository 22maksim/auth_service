package com.home.auth_service.model.dto;

import com.home.auth_service.model.enums.RoleUser;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountResponseDto {
    private Long id;
    private String username;
    private String email;
    private RoleUser role;
    private String createdAt;
    private String updatedAt;
}
