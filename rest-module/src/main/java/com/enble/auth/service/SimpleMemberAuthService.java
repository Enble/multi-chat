package com.enble.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.enble.auth.model.AuthResult;
import com.enble.auth.model.JwtProperties;
import com.enble.auth.util.IdGeneratorHolder;
import com.enble.common.header.ErrorStatus;
import com.enble.entity.Member;
import com.enble.exception.GeneralException;
import com.enble.repository.MemberRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SimpleMemberAuthService implements MemberAuthService {

    private final MemberRepository memberRepository;
    private final JwtProperties jwtProperties;
    private final PasswordEncoder passwordEncoder;
    private final JWTVerifier jwtVerifier;

    @Override
    public boolean isUsernameAvailable(String username) {
        return !memberRepository.existsByUsername(username);
    }

    @Override
    @Transactional
    public AuthResult register(String username, String password) {
        return publishAuthResult(createMember(username, password));
    }

    @Override
    public AuthResult login(String username, String password) {
        Member member = memberRepository.findByUsernameAndRemovedAtIsNull(username)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        if (passwordEncoder.matches(password, member.getPassword())) {
            return publishAuthResult(member);
        }
        throw new GeneralException(ErrorStatus.PASSWORD_NOT_MATCH);
    }

    @Override
    public AuthResult reissue(String refreshToken) {
        try {
            // TODO
        } catch (JWTVerificationException e) {
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }
    }

    @Override
    public UserDetails authorization(String accessToken) {
        return null;
    }

    @Override
    public void withdraw(String password) {

    }

    /**
     * 더미 멤버 생성을 비활성화하기 위해 deprecated 처리되었습니다. <br>
     * authorization 메서드를 사용해주세요.
     *
     * @throws UnsupportedOperationException
     * @deprecated
     */
    @Override
    @Deprecated(forRemoval = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException();
    }

    private Member createMember(String username, String password) {
        return memberRepository.save(new Member(
                IdGeneratorHolder.MEMBER.getIdGenerator().generate().toLong(),
                username,
                passwordEncoder.encode(password)
        ));
    }

    private AuthResult publishAuthResult(Member member) {
        Instant now = Instant.now();
        return new AuthResult(
                member,
                JWT.create()
                        .withClaim(JwtProperties.USER_ID, member.getId())
                        .withClaim(JwtProperties.TYPE, JwtProperties.ACCESS_TOKEN)
                        .withExpiresAt(now.plus(jwtProperties.atkTTL(), ChronoUnit.SECONDS))
                        .sign(jwtProperties.algorithm()),
                JWT.create()
                        .withClaim(JwtProperties.USER_ID, member.getId())
                        .withClaim(JwtProperties.TYPE, JwtProperties.REFRESH_TOKEN)
                        .withExpiresAt(now.plus(jwtProperties.rtkTTL(), ChronoUnit.SECONDS))
                        .sign(jwtProperties.algorithm()),
                jwtProperties.atkTTL(),
                jwtProperties.rtkTTL()
        );
    }
}
