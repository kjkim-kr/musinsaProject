package com.kj.musinsaproject.product;

import lombok.Data;

@Data
public class SimpleProduct {
    private final String brandName;
    private final String categoryName;
    private final int price;

    public SimpleProduct(Product product) {
        brandName = product.getBrand().getName();
        categoryName = product.getCategory().getName();
        price = product.getPrice();
    }
}
