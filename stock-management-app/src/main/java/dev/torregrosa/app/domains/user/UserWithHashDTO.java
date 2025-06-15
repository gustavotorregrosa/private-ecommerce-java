package dev.torregrosa.app.domains.user;

import java.util.UUID;

public class UserWithHashDTO extends UserBaseDTO {

    public String hash;

    public  UserWithHashDTO(UUID id, String name, String email, String password) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.hash = password;
    }
    
}
