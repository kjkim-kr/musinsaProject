package com.kj.musinsaproject.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public boolean deleteProduct(Product product){
        int d = productRepository.deleteProduct(
                product.getBrand().getId(),
                product.getCategory().getId(),
                product.getName()
        );
        return d > 0;
    }

    @Transactional
    public boolean deleteProductById(long id){
        long d = productRepository.deleteProductById(id);
        return d > 0;
    }

    @Transactional
    public Product updateProductById(long id, Product product) {
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
}