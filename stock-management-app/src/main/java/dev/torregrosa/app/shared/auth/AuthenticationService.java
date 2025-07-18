package dev.torregrosa.app.shared.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Autowired
    private StringRedisTemplate redisTemplate;

    public AuthenticationService(UserService userService, StringRedisTemplate redisTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
       
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

    public LoginResponseDTO refreshUser(String token){
        
        var claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token);

        String email = (String) claims.getBody().get("email");
        UserBaseDTO user = userService.getUserByEmail(email);
        
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        String newToken = generateJwtToken(user);
        String refreshToken = generateJwtToken(user, true);
        
        return new LoginResponseDTO(newToken, refreshToken, user);

    }

    public LoginResponseDTO authenticate(String email, String password) {
        UserWithHashDTO user = userService.getUserWithHashDTO(email);

        if (user == null) {
            throw new IllegalArgumentException("User or password not found");
        }

        if(!verifyPassword(password, user.hash)){
            throw new IllegalArgumentException("User or password not found");
        }

        UserBaseDTO userBase = new UserBaseDTO();
        userBase.id = user.id;
        userBase.name = user.name;
        userBase.email = user.email;
        
        String token = generateJwtToken(userBase);
        String refreshToken = generateJwtToken(userBase, true);
        LoginResponseDTO loginResponse = new LoginResponseDTO(token, refreshToken, userBase);

        return loginResponse;
    }

    public UserBaseDTO getUserFromToken(String token) {
        var claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token);
           
        UserBaseDTO userDTO = userService.getUserByEmail((String) claims.getBody().get("email"));
        if (userDTO == null) {
            throw new IllegalArgumentException("User not found");   
        }

        return userDTO;
    }

    private boolean verifyPassword(String password, String hash) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, hash);
    }
}
