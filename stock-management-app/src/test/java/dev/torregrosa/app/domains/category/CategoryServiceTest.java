package dev.torregrosa.app.domains.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(CategoryService.class)
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Test
    void testCategoryCreation() {
        // Create 'Books' category
        CategoryBaseDTO booksCategory = new CategoryBaseDTO();
        booksCategory.name = "Books";
        categoryService.save(booksCategory);

        // Check that there is only one record in the table
        assertEquals(1, categoryRepository.count(), "There should be 1 category in the database");

        // Create 'Food' category
        CategoryBaseDTO foodCategory = new CategoryBaseDTO();
        foodCategory.name = "Food";
        categoryService.save(foodCategory);

        // Check that there are now two records in the table
        assertEquals(2, categoryRepository.count(), "There should be 2 categories in the database");
    }
}
