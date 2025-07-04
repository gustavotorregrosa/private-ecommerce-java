package dev.torregrosa.app.shared.auth;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import dev.torregrosa.app.domains.user.UserBaseDTO;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthenticationContext {
   
    private UserBaseDTO currentUser;

    public UserBaseDTO getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserBaseDTO currentUser) {
        this.currentUser = currentUser;
    }

    
}
