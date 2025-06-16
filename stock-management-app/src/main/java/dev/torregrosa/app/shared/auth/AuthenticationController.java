package dev.torregrosa.app.shared.auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.torregrosa.app.domains.user.UserCreateDTO;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    

    @Autowired
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }           


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authenticationService.authenticate(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserCreateDTO user) {
        try {
            authenticationService.register(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            throw e;
        }
        
    }

   
}
