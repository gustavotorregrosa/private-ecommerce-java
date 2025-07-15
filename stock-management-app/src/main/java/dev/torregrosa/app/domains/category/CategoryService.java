package dev.torregrosa.app.domains.category;

import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.torregrosa.app.shared.IService;

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
        if(dto.id != null) category.setId(dto.id);
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
                .sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
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
