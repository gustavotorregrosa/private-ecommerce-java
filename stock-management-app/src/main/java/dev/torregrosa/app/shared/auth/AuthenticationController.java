package dev.torregrosa.app.shared.auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.torregrosa.app.domains.user.UserCreateDTO;
import dev.torregrosa.app.shared.HttpCustomResponse;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    

    @Autowired
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }           


    @PostMapping("/login")
    public ResponseEntity<HttpCustomResponse<LoginResponseDTO>> login(@RequestBody LoginRequest request) {
        HttpCustomResponse<LoginResponseDTO> response = new HttpCustomResponse<>();
        try {
            LoginResponseDTO loginResponseData = authenticationService.authenticate(request.getEmail(), request.getPassword());
            response.data = loginResponseData;
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.errorMessage = e.getMessage();
            return ResponseEntity.badRequest().body(response);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<HttpCustomResponse<String>> register(@RequestBody UserCreateDTO user) {
        HttpCustomResponse<String> response = new HttpCustomResponse<>();
        try {
            authenticationService.register(user);
            response.data = "User registered successfully";
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.errorMessage = e.getMessage();
            return ResponseEntity.badRequest().body(response);
        }
    }

}
