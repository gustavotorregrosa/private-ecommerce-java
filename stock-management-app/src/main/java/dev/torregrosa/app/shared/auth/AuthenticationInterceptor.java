package dev.torregrosa.app.shared.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    // Implement the methods of HandlerInterceptor to handle authentication logic
    // For example, you can check if the request has a valid token or session

    @Autowired
    AuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            String token = getToken(request);
            System.out.println("token: " + token);
            authenticationService.getUserFromToken(token);

            // Here you can validate the token and extract user information
            // For example, you can decode a JWT or look up a session in a database
            // UserBaseDTO user = getUserFromToken(token, authenticationService);

            // if (user == null) {
            //     response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
            //     return false;
            // }

            // If the token is valid, you can set the user in the request context
            // request.setAttribute("user", user);

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid token: " + e.getMessage());
            // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
            // return false;
        }

        return true;

        // response.setHeader("Access-Control-Allow-Origin", "*");
        // response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        // response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        // if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
        //     response.setStatus(HttpServletResponse.SC_OK);
        //     return false;
        // }

        // response.setContentType("application/json");

        // response.getWriter().write("{\"error\": \"try again...\"}");
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access 123");
        // return false;
    }


    private String getToken(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or does not start with 'Bearer '");
        }

        String token = authHeader.substring(7);
        return token;
 
    }

    private void getUserFromToken(String token, AuthenticationService authenticationService) {
        // Implement your logic to extract user information from the token
        // This could involve decoding a JWT or looking up a session in a database
        // return null; // Placeholder for user extraction logic
    }
}
