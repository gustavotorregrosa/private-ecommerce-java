package dev.torregrosa.app.domains.category;

import java.util.UUID;

public class CategoryBaseDTO {
    
    public UUID id;

    public String name;

    public CategoryBaseDTO() {
    }

    public CategoryBaseDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    
    public Category toEntity(){
        Category category = new Category();
        category.setId(this.id);
        category.setName(this.name);
        return category;
    }

    
}
