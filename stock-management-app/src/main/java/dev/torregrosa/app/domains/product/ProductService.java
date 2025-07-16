package dev.torregrosa.app.domains.product;

import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.torregrosa.app.domains.category.CategoryService;
import dev.torregrosa.app.shared.IService;

@Service
public class ProductService implements IService<ProductBaseDTO, UUID> {

    private final IProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(IProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public ProductBaseDTO save(ProductBaseDTO dto) {
        Product product = new Product();
        product.setName(dto.name);
        product.setDescription(dto.description);

        if (dto.categoryId != null) {
            product.setCategory(categoryService.findById(dto.categoryId).toEntity());
        }

        if (dto.id != null) {
            product.setId(dto.id);
        }

        product = productRepository.save(product);

        dto.id = product.getId();
        return dto;
    }

    @Override
    public ProductBaseDTO findById(UUID id) {
        return productRepository.findById(id)
                .map(product -> new ProductBaseDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getCategory() != null ? product.getCategory().getId() : null))
                .orElse(null);
    }

    @Override
    public Iterable<ProductBaseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(product -> new ProductBaseDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getCategory() != null ? product.getCategory().getId() : null))
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }
}
