package com.kj.musinsaproject.product;

import com.kj.musinsaproject.response.DataExceptionHandler;
import com.kj.musinsaproject.response.ErrorCode;
import com.kj.musinsaproject.response.JsonGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("api/product")
@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public String addProduct(@RequestBody Product product) {
        try {
            Product addProduct = productService.create(product);
            return JsonGenerator.getSuccessJsonResponse(addProduct);
        }
        catch(DataIntegrityViolationException dataIntegrityViolationException) {
            if(product.getName() == null)
                return JsonGenerator.getErrorJsonResponse(ErrorCode.UNEXPECTED_ATTRIBUTE);

            return DataExceptionHandler.handleDataException(
                    dataIntegrityViolationException.getCause()
            );
        }
    }

    @DeleteMapping("/delete")
    public String deleteProduct(@RequestBody Product product) {
        if (product.getName() == null)
            return JsonGenerator.getErrorJsonResponse(ErrorCode.UNEXPECTED_ATTRIBUTE);

        boolean isDeleted = productService.deleteProduct(product);

        return (isDeleted)?
                JsonGenerator.getSuccessJsonResponse()
                : JsonGenerator.getErrorJsonResponse(ErrorCode.DELETE_FAILED);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProductById(@PathVariable long id) {
        boolean isDeleted = productService.deleteProductById(id);
        return (isDeleted)?
                JsonGenerator.getSuccessJsonResponse()
                : JsonGenerator.getErrorJsonResponse(ErrorCode.DELETE_FAILED);
    }

    @PutMapping("/update/{id}")
    public String updateProduct(
            @PathVariable long id,
            @RequestBody Product newProduct) {
        try {
            Product updatedBrand = productService.updateProductById(id, newProduct);
            return JsonGenerator.getSuccessJsonResponse(updatedBrand);
        }
        catch(DataIntegrityViolationException dataIntegrityViolationException) {
            // 바꾸려는 이름이 이미 있는 경우 + 이름이 null 일 경우
            if(newProduct.getName() == null)
                return JsonGenerator.getErrorJsonResponse(ErrorCode.UNEXPECTED_ATTRIBUTE);

            // 그 외, PK Error
            return DataExceptionHandler.handleDataException(
                    dataIntegrityViolationException.getCause()
            );
        }
        catch(RuntimeException runtimeException){
            // name != empty
            // 바꾸려는 데이터가 없을 경우
            return JsonGenerator.getErrorJsonResponse(ErrorCode.DATA_NOT_FOUND);
        }
    }

    @GetMapping("/list/all")
    public String getAllProducts() {
        return JsonGenerator.getSuccessJsonResponse(productService.findAll());
    }
    @PostMapping("/list")
    public String findProduct(@RequestBody Product product) {
        if (product.getName() == null)
            return JsonGenerator.getErrorJsonResponse(ErrorCode.UNEXPECTED_ATTRIBUTE);

        Optional<Product> foundProduct = productService.findByName(product.getName());
        return (foundProduct.isPresent())?
                JsonGenerator.getSuccessJsonResponse(foundProduct.get())
                : JsonGenerator.getErrorJsonResponse(ErrorCode.DATA_NOT_FOUND);
    }

    @GetMapping("/list/category/{name}")
    public String getProductsByCategory(@PathVariable String name) {
        try {
            List<Product> productList = productService.findByCategoryName(name);

            // 최저가, 최고가 리스트 필터링
            List<SimpleProductByCategory> minProducts = getFilteredProducts(
                    productList,
                    productList.stream().mapToInt(Product::getPrice).min().orElseThrow(RuntimeException::new)
            );
            List<SimpleProductByCategory> maxProducts = getFilteredProducts(
                    productList,
                    productList.stream().mapToInt(Product::getPrice).max().orElseThrow(RuntimeException::new)
            );

            return JsonGenerator.getMinMaxProductJsonResponse(name, minProducts, maxProducts);
        } catch(RuntimeException runtimeException){
            return JsonGenerator.getErrorJsonResponse(ErrorCode.DATA_NOT_FOUND);
        }
    }

    private List<SimpleProductByCategory> getFilteredProducts(List<Product> productList, int price) {
        return productList.stream()
                .filter(p -> p.getPrice() == price)
                .map(SimpleProductByCategory::new)
                .toList();
    }

    @GetMapping("/list/min_price_brand")
    public String getProductsByMinPrice() {
        List<Product> minTotalPriceBrandList = productService.findByMinTotalPriceBrand();

        if(minTotalPriceBrandList.isEmpty())
            return JsonGenerator.getErrorJsonResponse(ErrorCode.DATA_NOT_FOUND);

        return JsonGenerator.getMinTotalPriceProductJsonResponse(
                minTotalPriceBrandList.get(0).getBrand().getName(),
                minTotalPriceBrandList.stream().map(SimpleProductByBrand::new).toList()
        );
    }

    @GetMapping("/list/min_price_category")
    public String getProductsByMinPriceOverCategories() {
        List<Product> minTotalPriceBrandList = productService.findMinPriceProductOverCategories();

        if(minTotalPriceBrandList.isEmpty())
            return JsonGenerator.getErrorJsonResponse(ErrorCode.DATA_NOT_FOUND);

        // 카테고리 별 중복 제거
        // 상의 카테고리에 ('A', 9000), ('G', 9000) 등이 등장하면, 그 중 하나만을 선택한다.
        return JsonGenerator.getMinPriceProductOverCategoryJsonResponse(
                minTotalPriceBrandList.stream()
                        .collect(
                                Collectors.toMap(
                                        Product::getCategory,
                                        p -> p,
                                        (existing, replace) -> replace
                                )
                        )
                        .values().stream()
                        .map(SimpleProduct::new).toList()
        );
    }
}
