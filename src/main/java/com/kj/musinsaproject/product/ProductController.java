package com.kj.musinsaproject.product;

import com.kj.musinsaproject.response.DataExceptionHandler;
import com.kj.musinsaproject.response.ErrorCode;
import com.kj.musinsaproject.response.JsonGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        if (product.getBrand() == null)
            return JsonGenerator.getErrorJsonResponse(ErrorCode.UNEXPECTED_ATTRIBUTE);
        if (product.getCategory() == null)
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
            // 바꾸려는 이름이 이미 있는 경우 등의 PK Error
            return DataExceptionHandler.handleDataException(
                    dataIntegrityViolationException.getCause()
            );
        }
        catch(IllegalArgumentException illegalArgumentException) {
            // 바꾸려는 데이터의 name, price가 모두 없는 경우
            return JsonGenerator.getErrorJsonResponse(ErrorCode.UNEXPECTED_ATTRIBUTE);
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
            // 해당 카테고리의 데이터를 전부 가져온다.
            List<Product> productList = productService.findByCategoryName(name);

            // 최저가, 최고가에 해당하는 상품을 모은다. (중복 데이터가 있을 수 있음)
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
        // 각 브랜드의 상품의 가격 합이 최저가인 브랜드를 찾고, 해당 브랜드의 상품을 모두 가져온다.
        List<Product> minTotalPriceBrandList = productService.findByMinTotalPriceBrand();

        if(minTotalPriceBrandList.isEmpty())
            return JsonGenerator.getErrorJsonResponse(ErrorCode.DATA_NOT_FOUND);

        // 하나의 브랜드 목록이기 때문에, 0번째 데이터의 이름을 인자로 제공
        return JsonGenerator.getMinTotalPriceProductJsonResponse(
                minTotalPriceBrandList.get(0).getBrand().getName(),
                minTotalPriceBrandList.stream().map(SimpleProductByBrand::new).toList()
        );
    }

    @GetMapping("/list/min_price_category")
    public String getProductsByMinPriceOverCategories() {
        // 각 카테고리별 최저가인 상품 목록을 가져온다.
        // 카테고리별 중복이 발생한 경우, 하나를 임의로 선택한다.
        List<Product> minTotalPriceBrandList = productService.findMinPriceProductOverCategories();

        if(minTotalPriceBrandList.isEmpty())
            return JsonGenerator.getErrorJsonResponse(ErrorCode.DATA_NOT_FOUND);

        return JsonGenerator.getMinPriceProductOverCategoryJsonResponse(
                minTotalPriceBrandList.stream()
                        .map(SimpleProduct::new).toList()
        );
    }
}
