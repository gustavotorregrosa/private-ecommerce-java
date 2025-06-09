package dev.torregrosa.app.domains.user;

import java.util.UUID;

public class UserBaseDTO {

    public UserBaseDTO() {
    }

    public UserBaseDTO(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    public UUID id;

    public String name;
    
    public String email;

}
