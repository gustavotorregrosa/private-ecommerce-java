package dev.torregrosa.app.domains.category;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.torregrosa.app.shared.HttpCustomResponse;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<HttpCustomResponse<Iterable<CategoryBaseDTO>>> getAllCategories() {

        HttpCustomResponse<Iterable<CategoryBaseDTO>> response = new HttpCustomResponse<>();

        try {
            response.data = categoryService.findAll();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpCustomResponse<CategoryBaseDTO>> getCategoryById(@PathVariable UUID id) {

        HttpCustomResponse<CategoryBaseDTO> response = new HttpCustomResponse<>();
 
        try {
            CategoryBaseDTO category = categoryService.findById(id);
            if (category != null) {

                response.data = category;
                return ResponseEntity.ok(response);
            } else {
                response.errorMessage = "Category not found with ID: " + id;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

            }
        } catch (Exception e) {
            response.errorMessage = "Error retrieving category: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<HttpCustomResponse<CategoryBaseDTO>> createCategory(@RequestBody CategoryBaseDTO category) {
        HttpCustomResponse<CategoryBaseDTO> response = new HttpCustomResponse<>();

        if (category == null) {
            response.errorMessage = "Category cannot be null.";
            return ResponseEntity.badRequest().body(response);
        }

        if (category.id == null) {
            response.errorMessage = "Category ID has to be null.";
            return ResponseEntity.badRequest().body(response);        }

        if (category.name == null || category.name.isEmpty()) {
            response.errorMessage = "Category name cannot be null or empty.";
            return ResponseEntity.badRequest().body(response);
        }

        try {
            CategoryBaseDTO _category = categoryService.save(category);
            response.data = _category;
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.errorMessage = "Error creating category: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpCustomResponse<CategoryBaseDTO>> updateCategory(@PathVariable UUID id, @RequestBody CategoryBaseDTO category) {
         HttpCustomResponse<CategoryBaseDTO> response = new HttpCustomResponse<>();

        if (category == null) {
            response.errorMessage = "Category cannot be null.";
            return ResponseEntity.badRequest().body(response);

        }

        if (category.id == null || !category.id.equals(id)) {
            response.errorMessage = "Category ID must match the path variable.";
            return ResponseEntity.badRequest().body(response);
        }

        if (category.name == null || category.name.isEmpty()) {
            response.errorMessage = "Category name cannot be null or empty.";
            return ResponseEntity.badRequest().body(response);
        }

        CategoryBaseDTO existingCategory = categoryService.findById(id);
        if (existingCategory == null) {
            response.errorMessage = "Category not found with ID: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        try {

            category.id = id; 
            category = categoryService.save(category);
            response.data = category;
            return ResponseEntity.ok(response);


        } catch (Exception e) {
            response.errorMessage = "Error updating category: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        
    
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpCustomResponse<CategoryBaseDTO>> deleteCategory(@PathVariable UUID id) {

        HttpCustomResponse<CategoryBaseDTO> response = new HttpCustomResponse<>();
        
        if (id == null) {
            response.errorMessage = "Category ID cannot be null.";
            return ResponseEntity.badRequest().body(response);
        }

        CategoryBaseDTO existingCategory = categoryService.findById(id);
        if (existingCategory == null) {
            response.errorMessage = "Category not found with ID: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        try {
            categoryService.deleteById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.errorMessage = "Error deleting category: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }
}