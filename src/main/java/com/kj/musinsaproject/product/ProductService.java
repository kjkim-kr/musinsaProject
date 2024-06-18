package com.kj.musinsaproject.product;

import com.kj.musinsaproject.category.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public boolean deleteProduct(Product product){
        // 삭제된 행의 개수를 받아서, 삭제 여부를 판단한다.
        int d = productRepository.deleteProduct(
                product.getBrand().getId(),
                product.getCategory().getId(),
                product.getName()
        );
        return d > 0;
    }

    @Transactional
    public boolean deleteProductById(long id){
        // 삭제된 행의 개수를 받아서, 삭제 여부를 판단한다.
        long d = productRepository.deleteProductById(id);
        return d > 0;
    }

    @Transactional
    public Product updateProductById(long id, Product product) {
        if (product.getName() == null && product.getPrice() == null) {
            throw new IllegalArgumentException("Product name and price cannot be null");
        }

        return productRepository.findById(id)
                .map(oldProduct -> {

                    // 이름, 가격만 수정 가능하도록 함
                    if(product.getName() != null) oldProduct.setName(product.getName());
                    if(product.getPrice() != null) oldProduct.setPrice(product.getPrice());

                    return productRepository.save(oldProduct);
                })
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> findAll() {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("addDate"));
        return productRepository.findAll(Sort.by(sorts));
    }

    public List<Product> findByCategoryName(String categoryName) {
        // 특정 카테고리를 찾아서 같은 카테고리의 상품들을 전부 리턴한다.
        return categoryRepository.findByName(categoryName)
                .map(category -> productRepository.findByCategoryId(category.getId()))
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public List<Product> findByMinTotalPriceBrand() {
        return productRepository.findByMinTotalPriceBrand();
    }

    public List<Product> findMinPriceProductOverCategories(){
        // 각 카테고리별 최저가를 찾는다. 최저가의 데이터는 중복일 수 있으므로 카테고리 별 중복 제거
        // 상의 카테고리에 ('A', 9000), ('G', 9000) 등이 등장하면, 그 중 하나만을 선택한다.
        return productRepository.findMinPriceProductOverCategories()
                .stream()
                .collect(
                        Collectors.toMap(
                                Product::getCategory,
                                p -> p,
                                (existing, replace) -> replace
                        )
                )
                .values().stream().toList();
    }
}
