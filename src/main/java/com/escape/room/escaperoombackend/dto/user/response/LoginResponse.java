package com.escape.room.escaperoombackend.dto.user.response;

import lombok.Getter;

@Getter
public class LoginResponse {
    private final String email;
    private final String nickname;
    private final String token;

    public LoginResponse(String email, String nickname, String token) {
        this.email = email;
        this.nickname = nickname;
        this.token = token;
    }
}