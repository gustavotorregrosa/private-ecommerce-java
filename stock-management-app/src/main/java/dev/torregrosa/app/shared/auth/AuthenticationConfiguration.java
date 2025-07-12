package dev.torregrosa.app.shared.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthenticationConfiguration implements WebMvcConfigurer {

    @Autowired
    AuthenticationInterceptor authenticationInterceptor;

     @Override
     public void addInterceptors(InterceptorRegistry registry) {
        //  registry.addInterceptor(authenticationInterceptor)
        //          .addPathPatterns("/**")
        //          .excludePathPatterns("/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/ws/**", "/ws");
     }


}
