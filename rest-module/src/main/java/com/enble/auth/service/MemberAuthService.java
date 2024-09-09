package com.enble.auth.service;

import com.enble.auth.model.AuthResult;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberAuthService extends UserDetailsService {

    boolean isUsernameAvailable(String username);
    AuthResult register(String username, String password);
    AuthResult login(String username, String password);
    AuthResult reissue(String refreshToken);
    UserDetails authorization(String accessToken);
    void withdraw(String password);
}
