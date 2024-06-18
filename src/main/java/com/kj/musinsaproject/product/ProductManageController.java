package com.kj.musinsaproject.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("manage/product")
@RequiredArgsConstructor
@Controller
public class ProductManageController {
    private final ProductService productService;

    @GetMapping("/")
    public String manageProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "product";
    }

    @GetMapping("/api")
    public String manageProductApis() {
        return "product_apitest";
    }
}
