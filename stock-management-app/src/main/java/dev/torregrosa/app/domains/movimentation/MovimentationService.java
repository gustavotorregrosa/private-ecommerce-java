package dev.torregrosa.app.domains.movimentation;

import java.util.UUID;

public class MovimentationService {
    
    private final IMovimentationRepository movimentationRepository;

    public MovimentationService(IMovimentationRepository movimentationRepository) {
        this.movimentationRepository = movimentationRepository;
    }

    public MovimentationBaseDTO save(MovimentationBaseDTO movimentationBaseDTO) {
        Movimentation movimentation = movimentationBaseDTO.toEntity();
        movimentation = movimentationRepository.save(movimentation);
        movimentationBaseDTO.id = movimentation.getId();
        movimentationBaseDTO.createdAt = movimentation.getCreatedAt();
        return movimentationBaseDTO;
    }

    public MovimentationBaseDTO findById(UUID id) {
        return movimentationRepository.findById(id)
                .map(movimentation -> {
                    MovimentationBaseDTO dto = new MovimentationBaseDTO();
                    dto.id = movimentation.getId();
                    dto.productId = movimentation.getProductId().toString();
                    dto.quantity = movimentation.getQuantity();
                    dto.createdAt = movimentation.getCreatedAt();
                    return dto;
                })
                .orElse(null);
    }

}
