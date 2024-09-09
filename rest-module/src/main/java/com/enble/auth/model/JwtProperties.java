package com.enble.auth.model;

import com.auth0.jwt.algorithms.Algorithm;

public record JwtProperties(
        Algorithm algorithm,
        long atkTTL,
        long rtkTTL
) {
    public static final String PREFIX = "Bearer ";
    public static final String USER_ID = "uid";
    public static final String TYPE = "typ";
    public static final String ACCESS_TOKEN = "atk";
    public static final String REFRESH_TOKEN = "rtk";
}
