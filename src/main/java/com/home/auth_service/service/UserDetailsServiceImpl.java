package com.home.auth_service.service;

import com.home.auth_service.model.UserAccount;
import com.home.auth_service.model.UserAccountDetails;
import com.home.auth_service.model.dto.UserAccountRequestDto;
import com.home.auth_service.model.dto.UserAccountResponseDto;
import com.home.auth_service.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "This user with this username address was not found. Username: " + username));
        return UserAccountDetails.fromUserAccount(userAccount);
    }

    public UserAccountResponseDto registerUser(UserAccountRequestDto requestDto) {
        UserAccount userAccount = new UserAccount();

        return null;
    }
}
