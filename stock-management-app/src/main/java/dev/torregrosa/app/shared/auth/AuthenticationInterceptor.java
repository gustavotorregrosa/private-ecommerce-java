package dev.torregrosa.app.shared.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    // Implement the methods of HandlerInterceptor to handle authentication logic
    // For example, you can check if the request has a valid token or session

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return false;
        }

        response.setContentType("application/json");

        response.getWriter().write("{\"error\": \"try again...\"}");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access 123");

        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "try again...")
        return false;
    }

    private boolean isValidToken(String token) {
        // Implement your token validation logic here
        return true; // Placeholder for actual validation logic
    }

}
