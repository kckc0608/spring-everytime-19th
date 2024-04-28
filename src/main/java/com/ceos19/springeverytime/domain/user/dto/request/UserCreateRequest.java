package com.ceos19.springeverytime.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserCreateRequest {
    private final String loginId;
    private final String password;
    private final String name;
    private final String nickname;
    private final String major;
    private final String admissionYear;
    private final String email;
}