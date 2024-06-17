package com.kj.musinsaproject;

import com.kj.musinsaproject.category.Category;
import com.kj.musinsaproject.category.CategoryRepository;
import com.kj.musinsaproject.category.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class CategoryServiceTest {
    private final CategoryRepository categoryRepository;

    public CategoryServiceTest(@Autowired CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Test
    @DisplayName("카테고리 추가 테스트(성공)")
    void addCategoryTest() {
        // Arrange
        var categoryService = new CategoryService(categoryRepository);
        var category = new Category();
        category.setName("상의");

        // Act
        categoryService.create(category);

        // Assert
        assertThat(categoryRepository.findAll()).hasSize(1);

        var actual = categoryRepository.findAll().get(0);
        assertThat(actual.getName()).isEqualTo(category.getName());
    }


    @Test
    @DisplayName("카테고리 추가 테스트(실패 : 카테고리명 중복)")
    void addCategoryTwiceTest() {
        // Arrange
        var categoryService = new CategoryService(categoryRepository);
        var category = new Category();
        category.setName("상의");
        categoryService.create(category);

        var duplicateCategory = new Category();
        duplicateCategory.setName("상의");

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class,
                () -> categoryService.create(duplicateCategory));
    }

    @Test
    @DisplayName("카테고리 검색 테스트(성공)")
    void findCategoryTest() {
        var categoryService = new CategoryService(categoryRepository);
        var category = new Category();
        category.setName("상의");
        categoryService.create(category);

        // Act
        Optional<Category> foundCategory = categoryService.findByName("상의");

        // Assert
        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getName()).isEqualTo(category.getName());
    }

    @Test
    @DisplayName("카테고리 검색 테스트(실패 : 카테고리명 없음)")
    void findCategoryFailTest() {
        var categoryService = new CategoryService(categoryRepository);
        var category = new Category();
        category.setName("상의");
        categoryService.create(category);

        // Act
        Optional<Category> foundCategory = categoryService.findByName("없는카테고리");

        // Assert
        assertThat(foundCategory).isEmpty();
    }
}
