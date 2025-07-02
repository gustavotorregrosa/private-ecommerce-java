package dev.torregrosa.app.shared.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    // Implement the methods of HandlerInterceptor to handle authentication logic
    // For example, you can check if the request has a valid token or session

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Logic to check authentication
//        String authToken = request.getHeader("Authorization");
//        if (authToken == null || !isValidToken(authToken)) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//            return false;
//        }
        System.out.print("point 1.1");
        return true;
    }

    private boolean isValidToken(String token) {
        // Implement your token validation logic here
        return true; // Placeholder for actual validation logic
    }

}
