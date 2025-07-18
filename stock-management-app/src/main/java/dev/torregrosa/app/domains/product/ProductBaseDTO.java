package dev.torregrosa.app.domains.product;

import java.util.UUID;

import dev.torregrosa.app.domains.category.CategoryBaseDTO;

public class ProductBaseDTO {

     
    public ProductBaseDTO() {
    }

    public ProductBaseDTO(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    public UUID id;

    public String name;

    public String description;

    public UUID categoryId;

    public CategoryBaseDTO category;

    public void setCategory(CategoryBaseDTO category){
        this.category = category;
    }
   
}
