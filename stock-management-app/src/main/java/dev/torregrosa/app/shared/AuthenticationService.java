package dev.torregrosa.app.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.torregrosa.app.domains.user.UserService;
import dev.torregrosa.app.domains.user.UserWithHashDTO;

@Service
public class AuthenticationService {
    
    @Autowired
    private final UserService userService;

    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    public boolean authenticate(String email, String password) {
        UserWithHashDTO user = userService.getUserWithHashDTO(email);
        if (user == null) {
            return false;
        }

        return verifyPassword(password, user.hash);

    }


    private boolean verifyPassword(String password, String hash) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, hash);
    }
}
