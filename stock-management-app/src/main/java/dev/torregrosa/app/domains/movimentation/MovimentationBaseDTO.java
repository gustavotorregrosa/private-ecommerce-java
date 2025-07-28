package dev.torregrosa.app.domains.movimentation;

import java.time.Instant;
import java.util.UUID;

import dev.torregrosa.app.domains.product.Product;

public class MovimentationBaseDTO {

    public UUID id;

    public String productId;

    public Integer amount;

    public Instant createdAt;

    public MovimentationBaseDTO() {
    }

    public MovimentationBaseDTO(String productId, Integer quantity) {
        this.productId = productId;
        this.amount = quantity;
    }

    public Movimentation toEntity() {
        Movimentation movimentation = new Movimentation();
        Product product = new Product();
        // System.out.println("Creating MovimentationBaseDTO with productId: " + this.productId + " and amount: " + this.amount);
        product.setId(UUID.fromString(this.productId));
        product.setName("");
        product.setDescription("");
        movimentation.setProduct(product);
        movimentation.setQuantity(this.amount);
        
        return movimentation;
    }
}
