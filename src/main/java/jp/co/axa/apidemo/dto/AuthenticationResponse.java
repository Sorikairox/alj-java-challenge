package jp.co.axa.apidemo.dto;

import lombok.Getter;

public class AuthenticationResponse {

    @Getter
    private String jwt;


    public AuthenticationResponse(String token) {
        this.jwt = token;
    }

    public AuthenticationResponse() {}
}
