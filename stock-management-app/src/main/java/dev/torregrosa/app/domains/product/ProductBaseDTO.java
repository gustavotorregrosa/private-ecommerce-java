package dev.torregrosa.app.domains.product;

import java.util.UUID;

public class ProductBaseDTO {
    
    public UUID id;

    public String name;

    public String description;

    public UUID categoryId;
    
    public ProductBaseDTO() {
    }

    public ProductBaseDTO(UUID id, String name, String description, UUID categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
    }
}
