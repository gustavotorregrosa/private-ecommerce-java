package dev.torregrosa.app.domains.movimentation;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovimentationRepository extends JpaRepository<Movimentation, UUID> {

    List<Movimentation> findByProductId(UUID productId);

}
