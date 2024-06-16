package com.kj.musinsaproject.product;

import lombok.Data;

@Data
public class SimpleProductByBrand {
    private final String categoryName;
    private final int price;

    public SimpleProductByBrand(Product product) {
        categoryName = product.getCategory().getName();
        price = product.getPrice();
    }
}
