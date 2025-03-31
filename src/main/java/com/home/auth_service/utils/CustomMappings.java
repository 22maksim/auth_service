package com.home.auth_service.utils;

import com.home.auth_service.errors.EntityIsNullException;
import com.home.auth_service.model.UserAccount;
import com.home.auth_service.model.dto.UserAccountResponseDto;

public class CustomMappings {

    public static UserAccountResponseDto userAccountToAccountResponseDto(UserAccount userAccount) {
        if (userAccount == null) {
            throw new EntityIsNullException("userAccount is null");
        }
        return UserAccountResponseDto.builder()
                .id(userAccount.getId())
                .username(userAccount.getUsername())
                .email(userAccount.getEmail())
                .role(userAccount.getRole())
                .userAccountStatus(userAccount.getUserAccountStatus())
                .createdAt(userAccount.getCreatedAt())
                .updatedAt(userAccount.getUpdatedAt())
                .build();
    }
}
