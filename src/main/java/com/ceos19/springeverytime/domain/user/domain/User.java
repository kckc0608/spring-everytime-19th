package com.ceos19.springeverytime.domain.user.domain;

import com.ceos19.springeverytime.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    private Long userId;

    @NonNull
    @Column(length = 30, nullable = false, name = "id", unique = true)
    private String loginId;

    @NonNull
    @Column(nullable = false)
    private String pw;

    @NonNull
    @Column(length = 30, nullable = false)
    private String nickname;

    @NonNull
    @Column(length = 10, nullable = false)
    private String name;

    @NonNull
    @Column(length = 30, nullable = false)
    private String major;

    @NonNull
    @Column(length = 2, nullable = false)
    private String admissionYear;

    @NonNull
    @Column(length = 30, nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean isEnrolled = false;

    public void authenticateUniversity() {
        this.isEnrolled = true;
    }

    @Builder
    public User(String loginId, String pw, String nickname, String name, String major, String admissionYear, String email) {
        this.loginId = loginId;
        this.pw = pw;
        this.nickname = nickname;
        this.name = name;
        this.major = major;
        this.admissionYear = admissionYear;
        this.email = email;
    }
}
