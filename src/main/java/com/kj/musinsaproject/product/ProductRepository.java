package com.kj.musinsaproject.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    List<Product> findByBrandId(long brandId);
    List<Product> findByCategoryId(long categoryId);

    @Query(value = """
        SELECT p.*
        FROM Product p
        WHERE p.brand_id = (
            SELECT p2.brand_id
            FROM Product p2
            GROUP BY p2.brand_id
            ORDER BY SUM(p2.price)
            LIMIT 1
        )
        """, nativeQuery = true)
    List<Product> findByMinTotalPriceBrand();
}
