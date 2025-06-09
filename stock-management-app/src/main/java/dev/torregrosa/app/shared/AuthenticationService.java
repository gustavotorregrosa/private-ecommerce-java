package dev.torregrosa.app.shared;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.torregrosa.app.domains.user.UserBaseDTO;
import dev.torregrosa.app.domains.user.UserService;
import dev.torregrosa.app.domains.user.UserWithHashDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthenticationService {
    
    @Autowired
    private final UserService userService;

    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration-time}")
    private long expirationTime;

    @Value("${jwt.expiration-refresh-time}")
    private long expirationRefreshTime;

    public String generateJwtToken(UserBaseDTO user, boolean isRefreshToken) {
     
        long effectiveExpirationTime = isRefreshToken ? expirationRefreshTime : this.expirationTime;

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + effectiveExpirationTime);

        return Jwts.builder()
                .setSubject(user.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String generateJwtToken(UserBaseDTO user) {
        return generateJwtToken(user, false);
    }



    public String authenticate(String email, String password) {
        UserWithHashDTO user = userService.getUserWithHashDTO(email);
        if (user == null) {
            throw new IllegalArgumentException("User or password not found");
        }

        if(!verifyPassword(password, user.hash)){
            throw new IllegalArgumentException("User or password not found");
        }

        UserBaseDTO userBase = (UserBaseDTO) user;
        return generateJwtToken(userBase);

    }


    private boolean verifyPassword(String password, String hash) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, hash);
    }
}
