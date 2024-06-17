package com.kj.musinsaproject.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kj.musinsaproject.brand.Brand;
import com.kj.musinsaproject.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Entity
@ToString
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"brand_id", "category_id", "name"})
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column(nullable = false)
    @Setter
    private Integer price;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime addDate;

    @PrePersist
    protected void onCreate() {
        addDate = LocalDateTime.now();
    }

    @Builder
    private Product(Long id, Brand brand, Category category,
                    String name, Integer price, LocalDateTime addDate) {
        this.id = id;
        this.brand = brand;
        this.category = category;
        this.name = name;
        this.price = price;
        this.addDate = addDate;
    }
}
