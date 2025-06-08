package dev.torregrosa.app.domains.category;

import dev.torregrosa.app.shared.IService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService implements IService<CategoryBaseDTO, UUID> {

    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryBaseDTO save(CategoryBaseDTO dto) {
        Category category = new Category();
        category.setName(dto.name);
        category = categoryRepository.save(category);
        dto.id = category.getId();
        return dto;
    }

    @Override
    public CategoryBaseDTO findById(UUID id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    CategoryBaseDTO dto = new CategoryBaseDTO();
                    dto.id = category.getId();
                    dto.name = category.getName();
                    return dto;
                })
                .orElse(null);
    }

    @Override
    public Iterable<CategoryBaseDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> {
                    CategoryBaseDTO dto = new CategoryBaseDTO();
                    dto.id = category.getId();
                    dto.name = category.getName();
                    return dto;
                })
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        categoryRepository.deleteById(id);
    }


}
