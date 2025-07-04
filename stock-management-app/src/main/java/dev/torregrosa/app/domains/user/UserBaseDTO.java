package dev.torregrosa.app.domains.user;

import java.util.UUID;

public class UserBaseDTO {
    
    public UUID id;

    public String name;
    
    public String email;

    @Override
    public String toString() {
        return "{" +
                "id: '" + id + '\'' +
                ", name: '" + name + '\'' +
                ", email: '" + email + '\'' +
                '}';
    }

}
