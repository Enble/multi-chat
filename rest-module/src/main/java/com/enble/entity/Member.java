package com.enble.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity<Long> {

    @Id
    private Long id;
    @Column(unique = true, nullable = false, updatable = false, length = 20)
    private String username;
    @Setter
    private String password;
    @Setter
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public Member(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = MemberRole.NORMAL;
    }

}
