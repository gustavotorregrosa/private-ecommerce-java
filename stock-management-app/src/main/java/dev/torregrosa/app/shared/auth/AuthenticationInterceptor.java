package dev.torregrosa.app.shared.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AuthenticationContext authenticationContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");

        // if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
        //     response.setStatus(HttpServletResponse.SC_OK);
        //     return false;
        // }

        // try {
        //     String token = getToken(request);
        //     UserBaseDTO user = authenticationService.getUserFromToken(token);
        //     authenticationContext.setCurrentUser(user);

        // } catch (IllegalArgumentException e) {
        //     System.out.println("Invalid token: " + e.getMessage());
        //     response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access: " + e.getMessage());
        //     return false;
        // }

        return true;

    }


    private String getToken(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or does not start with 'Bearer '");
        }

        String token = authHeader.substring(7);
        return token;
 
    }

}
