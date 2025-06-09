package dev.torregrosa.app.domains.user;

import java.util.UUID;

public class UserWithHashDTO extends UserBaseDTO {

    public UserWithHashDTO() {
        super();
    }

    public UserWithHashDTO(UUID id, String name, String email, String hash) {
        super(id, name, email);
        this.hash = hash;
    }

    public String hash;
    
}
