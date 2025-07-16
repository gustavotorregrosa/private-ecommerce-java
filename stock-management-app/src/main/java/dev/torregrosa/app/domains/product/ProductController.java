package dev.torregrosa.app.domains.product;

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
import dev.torregrosa.app.shared.socket.WebSocketHandler;
import dev.torregrosa.app.shared.socket.WebSocketMessageTemplate;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private WebSocketHandler webSocketHandler;

    @GetMapping
    public ResponseEntity<HttpCustomResponse<Iterable<ProductBaseDTO>>> getAllProducts() {
        HttpCustomResponse<Iterable<ProductBaseDTO>> response = new HttpCustomResponse<>();
        try {
            response.data = productService.findAll();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpCustomResponse<ProductBaseDTO>> getProductById(@PathVariable UUID id) {
        HttpCustomResponse<ProductBaseDTO> response = new HttpCustomResponse<>();
        try {
            ProductBaseDTO product = productService.findById(id);
            if (product != null) {
                response.data = product;
                return ResponseEntity.ok(response);
            } else {
                response.errorMessage = "Product not found with ID: " + id;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.errorMessage = "Error retrieving product: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<HttpCustomResponse<ProductBaseDTO>> createProduct(@RequestBody ProductBaseDTO product) {
        HttpCustomResponse<ProductBaseDTO> response = new HttpCustomResponse<>();
        if (product == null) {
            response.errorMessage = "Product cannot be null.";
            return ResponseEntity.badRequest().body(response);
        }
        if (!(product.id == null || product.id.toString().isEmpty())) {
            response.errorMessage = "Product ID has to be empty.";
            return ResponseEntity.badRequest().body(response);
        }
        if (product.name == null || product.name.isEmpty()) {
            response.errorMessage = "Product name cannot be null or empty.";
            return ResponseEntity.badRequest().body(response);
        }
        try {
            ProductBaseDTO _product = productService.save(product);
            response.data = _product;
            webSocketHandler.sendToRedis(new WebSocketMessageTemplate(null, null, "refresh-products"));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.errorMessage = "Error creating product: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpCustomResponse<ProductBaseDTO>> updateProduct(@PathVariable UUID id, @RequestBody ProductBaseDTO product) {
        HttpCustomResponse<ProductBaseDTO> response = new HttpCustomResponse<>();
        if (product == null) {
            response.errorMessage = "Product cannot be null.";
            return ResponseEntity.badRequest().body(response);
        }
        if (product.id == null || !product.id.equals(id)) {
            response.errorMessage = "Product ID must match the path variable.";
            return ResponseEntity.badRequest().body(response);
        }
        if (product.name == null || product.name.isEmpty()) {
            response.errorMessage = "Product name cannot be null or empty.";
            return ResponseEntity.badRequest().body(response);
        }
        ProductBaseDTO existingProduct = productService.findById(id);
        if (existingProduct == null) {
            response.errorMessage = "Product not found with ID: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        try {
            product.id = id;
            product = productService.save(product);
            response.data = product;
            webSocketHandler.sendToRedis(new WebSocketMessageTemplate(null, null, "refresh-products"));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.errorMessage = "Error updating product: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpCustomResponse<ProductBaseDTO>> deleteProduct(@PathVariable UUID id) {
        HttpCustomResponse<ProductBaseDTO> response = new HttpCustomResponse<>();
        if (id == null) {
            response.errorMessage = "Product ID cannot be null.";
            return ResponseEntity.badRequest().body(response);
        }
        ProductBaseDTO existingProduct = productService.findById(id);
        if (existingProduct == null) {
            response.errorMessage = "Product not found with ID: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        try {
            productService.deleteById(id);
            webSocketHandler.sendToRedis(new WebSocketMessageTemplate(null, null, "refresh-products"));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.errorMessage = "Error deleting product: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
