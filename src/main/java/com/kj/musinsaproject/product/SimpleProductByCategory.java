package com.kj.musinsaproject.product;

import lombok.Data;

@Data
public class SimpleProductByCategory {
    private final String brandName;
    private final int price;

    public SimpleProductByCategory(Product product) {
        brandName = product.getBrand().getName();
        price = product.getPrice();
    }
}
