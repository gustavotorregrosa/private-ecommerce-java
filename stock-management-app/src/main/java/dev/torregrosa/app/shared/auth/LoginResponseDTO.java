package dev.torregrosa.app.shared.auth;

import dev.torregrosa.app.domains.user.UserBaseDTO;

public class LoginResponseDTO {
    public String token;
    public String refreshToken;
    public UserBaseDTO user;

    public LoginResponseDTO(String token, String refreshToken, UserBaseDTO user) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = user;
    }

}
