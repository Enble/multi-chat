package com.enble.common.config.bean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.enble.auth.model.JwtProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    private final Algorithm algorithm;
    private final long atkTTL;
    private final long rtkTTL;

    public JwtConfig(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.atk-ttl}") long atkTTL,
            @Value("${jwt.rtk-ttl}") long rtkTTL
    ) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.atkTTL = atkTTL;
        this.rtkTTL = rtkTTL;
    }

    @Bean
    public JwtProperties jwtProperties() {
        return new JwtProperties(algorithm, atkTTL, rtkTTL);
    }

    @Bean
    public JWTVerifier jwtVerifier() {
        return JWT.require(algorithm).build();
    }
}
