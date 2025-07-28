package dev.torregrosa.app.domains.movimentation;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.torregrosa.app.shared.HttpCustomResponse;
import dev.torregrosa.app.shared.socket.WebSocketHandler;

@RestController
@RequestMapping("/movimentations")
public class MovimentationController {
    
    @Autowired
    private MovimentationService movimentationService;

    @Autowired
    private WebSocketHandler webSocketHandler;


    @GetMapping("/{id}")
    public ResponseEntity<HttpCustomResponse<Iterable<MovimentationBaseDTO>>> getMovimentationsById(@PathVariable UUID id) {

        HttpCustomResponse<Iterable<MovimentationBaseDTO>> response = new HttpCustomResponse<>();

        try {
            Iterable<MovimentationBaseDTO> movimentations = movimentationService.findAllByProductId(id);
            ArrayList<MovimentationBaseDTO> stockPositions = new ArrayList<MovimentationBaseDTO>();

            int totalAmount = 0;
            for (MovimentationBaseDTO movimentation : movimentations) {

                totalAmount += movimentation.amount;
               
                MovimentationBaseDTO stockPosition = new MovimentationBaseDTO();
                stockPosition.id = movimentation.id;
                stockPosition.productId = movimentation.productId;
                stockPosition.amount = totalAmount;
                stockPosition.createdAt = movimentation.createdAt;
                stockPositions.add(stockPosition);
            }

            if (!stockPositions.isEmpty()) {

                response.data = stockPositions;
                return ResponseEntity.ok(response);
            } else {
                response.errorMessage = "Movimentations not found with ID: " + id;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

            }
        } catch (Exception e) {
            response.errorMessage = "Error retrieving movimentations: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
