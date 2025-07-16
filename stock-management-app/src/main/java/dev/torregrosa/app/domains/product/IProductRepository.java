package dev.torregrosa.app.domains.product;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends  JpaRepository<Product, UUID> {
   
    List<Product> findByCategoryId(UUID categoryId);
    
}
