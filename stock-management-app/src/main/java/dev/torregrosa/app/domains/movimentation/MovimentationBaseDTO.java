package dev.torregrosa.app.domains.movimentation;

import java.time.Instant;
import java.util.UUID;

public class MovimentationBaseDTO {

    public UUID id;

    public String productId;

    public Integer quantity;

    public Instant createdAt;

    public MovimentationBaseDTO() {
    }

    public MovimentationBaseDTO(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Movimentation toEntity() {
        Movimentation movimentation = new Movimentation();
        movimentation.setQuantity(this.quantity);
        
        return movimentation;
    }
}
