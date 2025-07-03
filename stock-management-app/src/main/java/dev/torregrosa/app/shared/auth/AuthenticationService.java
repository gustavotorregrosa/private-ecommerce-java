package dev.torregrosa.app.shared.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.torregrosa.app.domains.user.UserBaseDTO;
import dev.torregrosa.app.domains.user.UserCreateDTO;
import dev.torregrosa.app.domains.user.UserService;
import dev.torregrosa.app.domains.user.UserWithHashDTO;
import io.jsonwebtoken.Claims;
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
        System.out.println("user: " + user.toString());
        long effectiveExpirationTime = isRefreshToken ? expirationRefreshTime : this.expirationTime;

        System.out.println("effectiveExpirationTime: " + effectiveExpirationTime);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + effectiveExpirationTime);

        System.out.println("expiryDate: " + expiryDate.toString());

        System.out.println(secretKey);

        Claims claims = Jwts.claims();
        claims.setSubject(user.toString());
        claims.put("email", user.email);
        claims.put("name", user.name);
        claims.put("id", user.id);

        String token = Jwts.builder().setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        System.out.println("token: " + token);

        return token;
    }

    public String generateJwtToken(UserBaseDTO user) {
        return generateJwtToken(user, false);
    }


    public void register(UserCreateDTO userCreateDTO) {
        // if (userService.getUserWithHashDTO(userCreateDTO.email) != null) {
        //     throw new IllegalArgumentException("User already exists");
        // }

        userService.createUser(userCreateDTO);
    }


    public String authenticate(String email, String password) {
        UserWithHashDTO user = userService.getUserWithHashDTO(email);

        // if (user == null) {
        //     throw new IllegalArgumentException("User or password not found");
        // }

        // if(!verifyPassword(password, user.hash)){
        //     throw new IllegalArgumentException("User or password not found");
        // }

        UserBaseDTO userBase = (UserBaseDTO) user;

        System.out.print("user email: " +   userBase.email);
        String token = generateJwtToken(userBase);
        System.out.println(token);

        return token;

    }


    public void getUserFromToken(String token) {
            var claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token);

            claims.getBody().forEach((key, value) -> {
                System.out.println("Key: " + key + ", Value: " + value);
            });
            
            // Assuming userString is a string representation of UserBaseDTO, e.g., "UserBaseDTO{name='John', email='john@example.com'}"
            // Extract the name using a simple regex or string manipulation
        //     String userName = null;
        //     int nameIndex = userString.indexOf("name='");
        //     if (nameIndex != -1) {
        //         int start = nameIndex + 6;
        //         int end = userString.indexOf("'", start);
        //         if (end != -1) {
        //         userName = userString.substring(start, end);
        //         }
        //     }
        //     System.out.println("Extracted user name: " + userName);

        // System.out.println("userString: " + userString);

        // Assuming UserBaseDTO has a static method fromString or similar constructor
        // If not, you need to parse userString accordingly
        // Example:
        // UserBaseDTO user = UserBaseDTO.fromString(userString);

        // For now, just print the extracted string
        // System.out.println("Extracted user info from token: " + userString);


        // Implement your logic to extract user information from the token
        // This could involve decoding a JWT or looking up a session in a database
        // For now, this method is a placeholder
    }


    private boolean verifyPassword(String password, String hash) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, hash);
    }
}
