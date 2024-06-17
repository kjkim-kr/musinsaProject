package com.kj.musinsaproject;

import com.kj.musinsaproject.brand.Brand;
import com.kj.musinsaproject.brand.BrandRepository;
import com.kj.musinsaproject.brand.BrandService;
import com.kj.musinsaproject.category.Category;
import com.kj.musinsaproject.category.CategoryRepository;
import com.kj.musinsaproject.category.CategoryService;
import com.kj.musinsaproject.product.Product;
import com.kj.musinsaproject.product.ProductRepository;
import com.kj.musinsaproject.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class ProductServiceTest {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceTest(
            @Autowired ProductRepository productRepository,
            @Autowired BrandRepository brandRepository,
            @Autowired CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    @Test
    @DisplayName("상품 추가 테스트(성공)")
    void addProductTest() {
        var brandService = new BrandService(brandRepository);
        var categoryService = new CategoryService(categoryRepository);

        var createBrand = new Brand();
        createBrand.setName("테스트브랜드");
        brandService.create(createBrand);

        var createCategory = new Category();
        createCategory.setName("상의");
        categoryService.create(createCategory);

        var productService = new ProductService(productRepository, categoryRepository);
        var brand = brandRepository.findAll().get(0);
        var category = categoryRepository.findAll().get(0);

        var product = Product.builder()
                .name("테스트상품")
                .price(12345)
                .category(category)
                .brand(brand)
                .build();

        // Act
        productService.create(product);

        // Assert
        assertThat(productRepository.findAll()).hasSize(1);
        var actual = productRepository.findAll().get(0);
        assertThat(actual.getName()).isEqualTo(product.getName());
        assertThat(actual.getPrice()).isEqualTo(product.getPrice());
        assertThat(actual.getCategory().getName()).isEqualTo(product.getCategory().getName());
        assertThat(actual.getBrand().getName()).isEqualTo(product.getBrand().getName());
    }


    @Test
    @DisplayName("상품 추가 테스트(실패 : 없는 브랜드)")
    void addProductFailByNoBrandTest() {
        var brandService = new BrandService(brandRepository);
        var categoryService = new CategoryService(categoryRepository);

        var createBrand = new Brand();
        createBrand.setName("테스트브랜드");
        brandService.create(createBrand);

        var createCategory = new Category();
        createCategory.setName("상의");
        categoryService.create(createCategory);

        var productService = new ProductService(productRepository, categoryRepository);
        var category = categoryRepository.findAll().get(0);
        var noBrand = Brand.builder()
                .id(2L)
                .name("NoBrand")
                .addDate(LocalDateTime.now())
                .build();


        var product = Product.builder()
                .name("테스트상품")
                .price(12345)
                .category(category)
                .brand(noBrand)
                .build();

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class,
                () -> productService.create(product));
    }

    @Test
    @DisplayName("상품 추가 테스트(실패 : 없는 카테고리)")
    void addProductFailByNoCategoryTest() {
        var brandService = new BrandService(brandRepository);
        var categoryService = new CategoryService(categoryRepository);

        var createBrand = new Brand();
        createBrand.setName("테스트브랜드");
        brandService.create(createBrand);

        var createCategory = new Category();
        createCategory.setName("상의");
        categoryService.create(createCategory);

        var productService = new ProductService(productRepository, categoryRepository);
        var brand = brandRepository.findAll().get(0);
        var noCategory = Category.builder()
                .name("NoCategory")
                .addDate(LocalDateTime.now())
                .id(2L)
                .build();


        var product = Product.builder()
                .name("테스트상품")
                .price(12345)
                .category(noCategory)
                .brand(brand)
                .build();

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class,
                () -> productService.create(product));
    }

    @Test
    @DisplayName("상품 삭제 테스트(성공)")
    void deleteProductTest() {
        var brandService = new BrandService(brandRepository);
        var categoryService = new CategoryService(categoryRepository);

        var createBrand = new Brand();
        createBrand.setName("테스트브랜드");
        brandService.create(createBrand);

        var createCategory = new Category();
        createCategory.setName("상의");
        categoryService.create(createCategory);

        var productService = new ProductService(productRepository, categoryRepository);
        var brand = brandRepository.findAll().get(0);
        var category = categoryRepository.findAll().get(0);

        var createProduct = Product.builder()
                .name("테스트상품")
                .price(12345)
                .category(category)
                .brand(brand)
                .build();

        var product = productService.create(createProduct);

        // Act
        boolean isDeleted = productService.deleteProductById(product.getId());

        // Assert
        assertThat(isDeleted).isTrue();
        assertThat(productRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("상품 삭제 테스트(실패 : 대상 상품 없음)")
    void deleteProductFailTest() {
        var brandService = new BrandService(brandRepository);
        var categoryService = new CategoryService(categoryRepository);

        var createBrand = new Brand();
        createBrand.setName("테스트브랜드");
        brandService.create(createBrand);

        var createCategory = new Category();
        createCategory.setName("상의");
        categoryService.create(createCategory);

        var productService = new ProductService(productRepository, categoryRepository);
        var brand = brandRepository.findAll().get(0);
        var category = categoryRepository.findAll().get(0);

        var createProduct = Product.builder()
                .name("테스트상품")
                .price(12345)
                .category(category)
                .brand(brand)
                .build();
        productService.create(createProduct);

        var nonExistProduct = Product.builder()
                .name("없는상품")
                .price(12345)
                .category(category)
                .brand(brand)
                .id(2L)
                .build();

        // Act
        boolean isDeleted = productService.deleteProduct(nonExistProduct);

        // Assert
        assertThat(isDeleted).isFalse();
        assertThat(productRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("상품 업데이트 테스트(성공)")
    void updateProductTest() {
        var brandService = new BrandService(brandRepository);
        var categoryService = new CategoryService(categoryRepository);

        var createBrand = new Brand();
        createBrand.setName("테스트브랜드");
        brandService.create(createBrand);

        var createCategory = new Category();
        createCategory.setName("상의");
        categoryService.create(createCategory);

        var productService = new ProductService(productRepository, categoryRepository);
        var brand = brandRepository.findAll().get(0);
        var category = categoryRepository.findAll().get(0);

        var createProduct = Product.builder()
                .name("테스트상품")
                .price(12345)
                .category(category)
                .brand(brand)
                .build();

        var product = productService.create(createProduct);
        product.setName("테스트상품변경이름");
        product.setPrice(11111);

        // Act
        var updatedProduct = productService.updateProductById(
                product.getId(), product
        );

        // Assert
        assertThat(productRepository.findAll()).hasSize(1);
        assertThat(updatedProduct.getId()).isEqualTo(product.getId());
        assertThat(updatedProduct.getName()).isEqualTo(product.getName());
        assertThat(updatedProduct.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    @DisplayName("상품 업데이트 테스트(실패 : 업데이트 상품명 = null)")
    void updateBrandFailTest() {
        // Arrange
        var brandService = new BrandService(brandRepository);
        var categoryService = new CategoryService(categoryRepository);

        var createBrand = new Brand();
        createBrand.setName("테스트브랜드");
        brandService.create(createBrand);

        var createCategory = new Category();
        createCategory.setName("상의");
        categoryService.create(createCategory);

        var productService = new ProductService(productRepository, categoryRepository);
        var brand = brandRepository.findAll().get(0);
        var category = categoryRepository.findAll().get(0);

        var createProduct = Product.builder()
                .name("테스트상품")
                .price(12345)
                .category(category)
                .brand(brand)
                .build();

        var product = productService.create(createProduct);
        var updateProductId = product.getId();

        // empty brand name
        var updateProduct = new Product();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> productService.updateProductById(updateProductId, updateProduct));
        assertThat(productService.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("상품 검색 테스트(성공)")
    void findProductTest() {
        // Arrange
        var brandService = new BrandService(brandRepository);
        var categoryService = new CategoryService(categoryRepository);

        var createBrand = new Brand();
        createBrand.setName("테스트브랜드");
        brandService.create(createBrand);

        var createCategory = new Category();
        createCategory.setName("상의");
        categoryService.create(createCategory);

        var productService = new ProductService(productRepository, categoryRepository);
        var brand = brandRepository.findAll().get(0);
        var category = categoryRepository.findAll().get(0);

        var createProduct = Product.builder()
                .name("테스트상품")
                .price(12345)
                .category(category)
                .brand(brand)
                .build();

        var product = productService.create(createProduct);

        // Act
        Optional<Product> foundProduct = productService.findByName(product.getName());

        // Assert
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo(product.getName());
    }

    @Test
    @DisplayName("상품 검색 테스트(실패 : 상품명 없음)")
    void findProductFailTest() {
        // Arrange
        var brandService = new BrandService(brandRepository);
        var categoryService = new CategoryService(categoryRepository);

        var createBrand = new Brand();
        createBrand.setName("테스트브랜드");
        brandService.create(createBrand);

        var createCategory = new Category();
        createCategory.setName("상의");
        categoryService.create(createCategory);

        var productService = new ProductService(productRepository, categoryRepository);
        var brand = brandRepository.findAll().get(0);
        var category = categoryRepository.findAll().get(0);

        var createProduct = Product.builder()
                .name("테스트상품")
                .price(12345)
                .category(category)
                .brand(brand)
                .build();

        productService.create(createProduct);

        // Act
        Optional<Product> foundProduct = productService.findByName("없는상품명");

        // Assert
        assertThat(foundProduct).isEmpty();
    }


    // Api Test
    @Test
    @DisplayName("API 테스트 - 카테고리별 최저가격 브랜드, 가격, 총액")
    void findProductsByCategoryTest() {
        List<String> brandList = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I");
        List<String> cateList = List.of(
                "상의", "아우터", "바지", "스니커즈",
                "가방", "모자", "양말", "액세서리");
        List<Integer> priceList = List.of(
                11200, 5500, 4200, 9000, 2000, 1700, 1800, 2300,
                10500, 5900, 3800, 9100, 2100, 2000, 2000, 2200,
                10000, 6200, 3300, 9200, 2200, 1900, 2200, 2100,
                10100, 5100, 3000, 9500, 2500, 1500, 2400, 2000,
                10700, 5000, 3800, 9900, 2300, 1800, 2100, 2100,
                11200, 7200, 4000, 9300, 2100, 1600, 2300, 1900,
                10500, 5800, 3900, 9000, 2200, 1700, 2100, 2000,
                10800, 6300, 3100, 9700, 2100, 1600, 2000, 2000,
                11400, 6700, 3200, 9500, 2400, 1700, 1700, 2400
        );
        // Arrange
        var brandService = new BrandService(brandRepository);
        var categoryService = new CategoryService(categoryRepository);
        var productService = new ProductService(productRepository, categoryRepository);

        brandList.forEach(b -> brandService.create(Brand.builder().name(b).build()));
        cateList.forEach(c -> categoryService.create(Category.builder().name(c).build()));

        for(int i = 0; i < brandList.size(); i++) {
            var brand = brandService.findByName(brandList.get(i));

            if (brand.isEmpty()) throw new RuntimeException("");
            for (int j = 0; j < cateList.size(); j++) {
                var cate = categoryService.findByName(cateList.get(j));

                if(cate.isEmpty()) throw new RuntimeException("");
                var product = Product.builder()
                        .name(cateList.get(j) + brandList.get(i))
                        .price(priceList.get(i * cateList.size() + j))
                        .category(cate.get())
                        .brand(brand.get())
                        .build();

                productService.create(product);
            }
        }

        // Act
        List<Product> minProductsByCategory = productService.findMinPriceProductOverCategories();

        // Assert
        assertThat(minProductsByCategory).hasSameSizeAs(cateList);

        assertThat(minProductsByCategory.stream().mapToInt(Product::getPrice).sum())
                .isEqualTo(34100);
    }

    @Test
    @DisplayName("API 테스트 - 최저가격 브랜드 조회")
    void findProductsByBrandTest() {
        List<String> brandList = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I");
        List<String> cateList = List.of(
                "상의", "아우터", "바지", "스니커즈",
                "가방", "모자", "양말", "액세서리");
        List<Integer> priceList = List.of(
                11200, 5500, 4200, 9000, 2000, 1700, 1800, 2300,
                10500, 5900, 3800, 9100, 2100, 2000, 2000, 2200,
                10000, 6200, 3300, 9200, 2200, 1900, 2200, 2100,
                10100, 5100, 3000, 9500, 2500, 1500, 2400, 2000,
                10700, 5000, 3800, 9900, 2300, 1800, 2100, 2100,
                11200, 7200, 4000, 9300, 2100, 1600, 2300, 1900,
                10500, 5800, 3900, 9000, 2200, 1700, 2100, 2000,
                10800, 6300, 3100, 9700, 2100, 1600, 2000, 2000,
                11400, 6700, 3200, 9500, 2400, 1700, 1700, 2400
        );
        // Arrange
        var brandService = new BrandService(brandRepository);
        var categoryService = new CategoryService(categoryRepository);
        var productService = new ProductService(productRepository, categoryRepository);

        brandList.forEach(b -> brandService.create(Brand.builder().name(b).build()));
        cateList.forEach(c -> categoryService.create(Category.builder().name(c).build()));

        for(int i = 0; i < brandList.size(); i++) {
            var brand = brandService.findByName(brandList.get(i));

            if (brand.isEmpty()) throw new RuntimeException("");
            for (int j = 0; j < cateList.size(); j++) {
                var cate = categoryService.findByName(cateList.get(j));

                if(cate.isEmpty()) throw new RuntimeException("");
                var product = Product.builder()
                        .name(cateList.get(j) + brandList.get(i))
                        .price(priceList.get(i * cateList.size() + j))
                        .category(cate.get())
                        .brand(brand.get())
                        .build();

                productService.create(product);
            }
        }

        // Act
        List<Product> minProductsByBrandList = productService.findByMinTotalPriceBrand();

        // Assert
        assertThat(minProductsByBrandList.get(0).getBrand().getName()).isEqualTo("D");
        assertThat(minProductsByBrandList.stream().mapToInt(Product::getPrice).sum()).isEqualTo(36100);
    }

    @Test
    @DisplayName("API 테스트 - 카테고리별 최저, 최고가격 브랜드 조회")
    void findProductsByCategoryNameTest() {
        List<String> brandList = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I");
        List<String> cateList = List.of(
                "상의", "아우터", "바지", "스니커즈",
                "가방", "모자", "양말", "액세서리");
        List<Integer> priceList = List.of(
                11200, 5500, 4200, 9000, 2000, 1700, 1800, 2300,
                10500, 5900, 3800, 9100, 2100, 2000, 2000, 2200,
                10000, 6200, 3300, 9200, 2200, 1900, 2200, 2100,
                10100, 5100, 3000, 9500, 2500, 1500, 2400, 2000,
                10700, 5000, 3800, 9900, 2300, 1800, 2100, 2100,
                11200, 7200, 4000, 9300, 2100, 1600, 2300, 1900,
                10500, 5800, 3900, 9000, 2200, 1700, 2100, 2000,
                10800, 6300, 3100, 9700, 2100, 1600, 2000, 2000,
                11400, 6700, 3200, 9500, 2400, 1700, 1700, 2400
        );
        // Arrange
        var brandService = new BrandService(brandRepository);
        var categoryService = new CategoryService(categoryRepository);
        var productService = new ProductService(productRepository, categoryRepository);

        brandList.forEach(b -> brandService.create(Brand.builder().name(b).build()));
        cateList.forEach(c -> categoryService.create(Category.builder().name(c).build()));

        for(int i = 0; i < brandList.size(); i++) {
            var brand = brandService.findByName(brandList.get(i));

            if (brand.isEmpty()) throw new RuntimeException("");
            for (int j = 0; j < cateList.size(); j++) {
                var cate = categoryService.findByName(cateList.get(j));

                if(cate.isEmpty()) throw new RuntimeException("");
                var product = Product.builder()
                        .name(cateList.get(j) + brandList.get(i))
                        .price(priceList.get(i * cateList.size() + j))
                        .category(cate.get())
                        .brand(brand.get())
                        .build();

                productService.create(product);
            }
        }

        // Act
        List<Product> productsByCategoryList = productService.findByCategoryName("상의");
        OptionalInt minPrice;
        OptionalInt maxPrice;
        minPrice = productsByCategoryList.stream().mapToInt(Product::getPrice).min();
        maxPrice = productsByCategoryList.stream().mapToInt(Product::getPrice).max();

        // Assert
        assertThat(productsByCategoryList).isNotEmpty();
        assertThat(minPrice).isNotEmpty();
        assertThat(maxPrice).isNotEmpty();

        assertThat(minPrice.getAsInt()).isEqualTo(10000);
        assertThat(
                productsByCategoryList
                        .stream()
                        .filter(p -> p.getPrice() == minPrice.getAsInt())
                        .toList().get(0).getBrand().getName()
        ).isEqualTo("C");

        assertThat(maxPrice.getAsInt()).isEqualTo(11400);
        assertThat(
                productsByCategoryList
                        .stream()
                        .filter(p -> p.getPrice() == maxPrice.getAsInt())
                        .toList().get(0).getBrand().getName()
        ).isEqualTo("I");
    }
}
