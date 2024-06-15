package com.kj.musinsaproject.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    @Modifying(clearAutomatically = true)
    @Query("""
        DELETE from Product P
        where P.name = :productName
          and P.brand.id = :brandId
          and P.category.id = :categoryId
        """)
    int deleteProduct(long brandId, long categoryId, String productName);
    long deleteProductById(long id);
}
