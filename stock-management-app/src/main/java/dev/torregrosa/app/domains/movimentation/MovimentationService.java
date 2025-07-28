package dev.torregrosa.app.domains.movimentation;

import java.util.UUID;

import dev.torregrosa.app.shared.IService;

public class MovimentationService implements IService<MovimentationBaseDTO, UUID> {

    private final IMovimentationRepository movimentationRepository;

    public MovimentationService(IMovimentationRepository movimentationRepository) {
        this.movimentationRepository = movimentationRepository;
    }

    @Override
    public MovimentationBaseDTO save(MovimentationBaseDTO movimentationBaseDTO) {
        Movimentation movimentation = movimentationBaseDTO.toEntity();
        movimentation = movimentationRepository.save(movimentation);
        movimentationBaseDTO.id = movimentation.getId();
        movimentationBaseDTO.createdAt = movimentation.getCreatedAt();
        return movimentationBaseDTO;
    }

    @Override
    public MovimentationBaseDTO findById(UUID id) {
        return movimentationRepository.findById(id)
                .map(movimentation -> {
                    MovimentationBaseDTO dto = new MovimentationBaseDTO();
                    dto.id = movimentation.getId();
                    dto.productId = movimentation.getProduct().getId().toString();
                    dto.amount = movimentation.getQuantity();
                    dto.createdAt = movimentation.getCreatedAt();
                    return dto;
                })
                .orElse(null);
    }

    @Override
    public void deleteById(UUID id) {
        movimentationRepository.deleteById(id);
    }


    @Override
    public Iterable<MovimentationBaseDTO> findAll() {
        return movimentationRepository.findAll()
                .stream()
                .map(movimentation -> {
                    MovimentationBaseDTO dto = new MovimentationBaseDTO();
                    dto.id = movimentation.getId();
                    dto.productId = movimentation.getProduct().getId().toString();
                    dto.amount = movimentation.getQuantity();
                    dto.createdAt = movimentation.getCreatedAt();
                    return dto;
                })
                .toList();
    }

    public Iterable<MovimentationBaseDTO> findAllByProductId(UUID productId) {
        return movimentationRepository.findByProductId(productId).stream()
            .sorted((m1, m2) -> m1.getCreatedAt().compareTo(m2.getCreatedAt()))
            .map(movimentation -> {
                MovimentationBaseDTO dto = new MovimentationBaseDTO();
                dto.id = movimentation.getId();
                dto.productId = movimentation.getProduct().getId().toString();
                dto.amount = movimentation.getQuantity();
                dto.createdAt = movimentation.getCreatedAt();
                return dto;
            })
            .toList();
    }

}
