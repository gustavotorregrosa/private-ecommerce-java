package dev.torregrosa.app.shared.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthenticationConfiguration implements WebMvcConfigurer {

    // This class can be used to configure authentication-related settings
    // For example, you can add interceptors or configure security settings

    // If you need to add an interceptor, you can override the addInterceptors method
    // and register your AuthenticationInterceptor here.

    @Autowired
    AuthenticationInterceptor authenticationInterceptor;

    // Example:
     @Override
     public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(authenticationInterceptor)
                 .addPathPatterns("/**") // Apply to all paths or specify patterns
                 .excludePathPatterns("/auth/**"); // Exclude authentication paths
     }


}
