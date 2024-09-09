package com.enble.auth.model;

import com.enble.entity.Member;

public record AuthResult(
        Member member,
        String accessToken,
        String refreshToken,
        long accessTokenExpiresIn,
        long refreshTokenExpiresIn
) {}
