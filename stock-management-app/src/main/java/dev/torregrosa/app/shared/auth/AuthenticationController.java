package dev.torregrosa.app.shared.auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.torregrosa.app.domains.user.UserCreateDTO;
import dev.torregrosa.app.shared.HttpCustomResponse;
import dev.torregrosa.app.shared.socket.WebSocketHandler;
import dev.torregrosa.app.shared.socket.WebSocketMessageTemplate;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    

    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private final WebSocketHandler webSocketHandler;

    public AuthenticationController(AuthenticationService authenticationService, WebSocketHandler webSocketHandler) {
        this.authenticationService = authenticationService;
        this.webSocketHandler = webSocketHandler;
    }    
    
    @GetMapping("/refresh")
    public ResponseEntity<HttpCustomResponse<LoginResponseDTO>> refreshToken(HttpServletRequest request){

        HttpCustomResponse<LoginResponseDTO> response = new HttpCustomResponse<>();
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Authorization header is missing or does not start with 'Bearer '");
            }

            String token = authHeader.substring(7);

            LoginResponseDTO loginResponseData = authenticationService.refreshUser(token);
            response.data = loginResponseData;
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.errorMessage = e.getMessage();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<HttpCustomResponse<LoginResponseDTO>> login(@RequestBody LoginRequest loginDTORequest, HttpServletRequest httpRequest) {
        HttpCustomResponse<LoginResponseDTO> response = new HttpCustomResponse<>();
        try {
            String socketSessionID = httpRequest.getHeader("socket-session-id");
            LoginResponseDTO loginResponseData = authenticationService.authenticate(loginDTORequest.getEmail(), loginDTORequest.getPassword());
            response.data = loginResponseData;

            webSocketHandler.sendToRedis(new WebSocketMessageTemplate("User " + response.data.user.name + " just logged in" , socketSessionID, "new-login"));

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
