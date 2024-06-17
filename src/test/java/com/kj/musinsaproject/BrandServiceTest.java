package com.kj.musinsaproject;

import com.kj.musinsaproject.brand.Brand;
import com.kj.musinsaproject.brand.BrandRepository;
import com.kj.musinsaproject.brand.BrandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BrandServiceTest {
    private final BrandRepository brandRepository;

    public BrandServiceTest(@Autowired BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Test
    @DisplayName("브랜드 추가 테스트(성공)")
    void addBrandTest() {
        // Arrange
        var brandService = new BrandService(brandRepository);
        var brand = new Brand();
        brand.setName("테스트브랜드");

        // Act
        brandService.create(brand);

        // Assert
        assertThat(brandRepository.findAll()).hasSize(1);

        var actual = brandRepository.findAll().get(0);
        assertThat(actual.getName()).isEqualTo(brand.getName());
    }

    @Test
    @DisplayName("브랜드 추가 테스트(실패 : 브랜드명 중복)")
    void addBrandTwiceTest() {
        // Arrange
        var brandService = new BrandService(brandRepository);
        var brand = new Brand();
        brand.setName("테스트브랜드");
        brandService.create(brand);

        var duplicateBrand = new Brand();
        duplicateBrand.setName("테스트브랜드");

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class,
                () -> brandService.create(duplicateBrand));
    }

    @Test
    @DisplayName("브랜드 삭제 테스트(성공)")
    void deleteBrandTest() {
        // Arrange
        var brandService = new BrandService(brandRepository);
        var brand = new Brand();
        brand.setName("테스트브랜드");
        brandService.create(brand);

        // Act
        boolean isDeleted = brandService.deleteByName("테스트브랜드");

        // Assert
        assertThat(brandRepository.findAll()).isEmpty();
        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("브랜드 삭제 테스트(실패 : 대상 브랜드명 없음)")
    void deleteBrandFailTest() {
        // Arrange
        var brandService = new BrandService(brandRepository);
        var brand = new Brand();
        brand.setName("테스트브랜드");
        brandService.create(brand);

        // Act
        boolean isDeleted = brandService.deleteByName("없는브랜드명");

        // Assert
        assertThat(brandRepository.findAll()).hasSize(1);
        assertFalse(isDeleted);
    }

    @Test
    @DisplayName("브랜드 업데이트 테스트(성공)")
    void updateBrandTest() {
        // Arrange
        var brandService = new BrandService(brandRepository);
        var brand = new Brand();
        brand.setName("테스트브랜드");

        var addedBrand = brandService.create(brand);

        var updatedBrand = new Brand();
        updatedBrand.setName("브랜드명업데이트");

        // Act
        brandService.updateBrandById(addedBrand.getId(), updatedBrand);

        // Assert
        assertThat(brandRepository.findAll()).hasSize(1);
        var actual = brandRepository.findAll().get(0);
        assertThat(actual.getName()).isEqualTo(updatedBrand.getName());
    }

    @Test
    @DisplayName("브랜드 업데이트 테스트(실패 : 업데이트 브랜드명 = null)")
    void updateBrandFailTest() {
        // Arrange
        var brandService = new BrandService(brandRepository);
        var brand = new Brand();
        brand.setName("테스트브랜드");

        var addedBrand = brandService.create(brand);
        var addedBrandId = addedBrand.getId();

        // empty brand name
        var updatedBrand = new Brand();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> brandService.updateBrandById(addedBrandId, updatedBrand));
        assertThat(brandRepository.findAll()).hasSize(1);
    }
}
