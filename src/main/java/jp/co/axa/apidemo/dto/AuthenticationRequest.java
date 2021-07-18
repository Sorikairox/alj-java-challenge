package jp.co.axa.apidemo.dto;

import lombok.Getter;
import lombok.Setter;

public class AuthenticationRequest {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    public AuthenticationRequest() {
    }
}
