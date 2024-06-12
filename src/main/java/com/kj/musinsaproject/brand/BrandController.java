package com.kj.musinsaproject.brand;

import com.kj.musinsaproject.response.ErrorCode;
import com.kj.musinsaproject.response.JsonGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequestMapping("api/brand")
@RequiredArgsConstructor
@RestController
public class BrandController {
    private final BrandService brandService;

    @PostMapping("/add")
    public String addBrand(@RequestBody Brand brand) {
        try {
            Brand addBrand = brandService.create(brand);
            return JsonGenerator.getSuccessJsonResponse(addBrand);
        }
        catch(DataIntegrityViolationException dataIntegrityViolationException) {
            if(brand.getName() == null)
                return JsonGenerator.getErrorJsonResponse(ErrorCode.UNEXPECTED_ATTRIBUTE);

            return JsonGenerator.getErrorJsonResponse(ErrorCode.UNIQUE_KEY_VIOLATION);
        }
    }

    @DeleteMapping("/delete")
    public String deleteBrandByName(@RequestBody Brand brand) {
        if (brand.getName() == null)
            return JsonGenerator.getErrorJsonResponse(ErrorCode.UNEXPECTED_ATTRIBUTE);

        boolean isDeleted = brandService.deleteByName(brand.getName());

        return (isDeleted)?
                JsonGenerator.getSuccessJsonResponse()
                : JsonGenerator.getErrorJsonResponse(ErrorCode.DELETE_FAILED);
    }

    @PutMapping("/update/{id}")
    public String updateBrandByName(
            @PathVariable long id,
            @RequestBody Brand newBrand) {
        try {
            Brand updatedBrand = brandService.updateBrandById(id, newBrand);
            return JsonGenerator.getSuccessJsonResponse(updatedBrand);
        }
        catch(DataIntegrityViolationException dataIntegrityViolationException) {
            // 바꾸려는 이름이 이미 있는 경우 + 이름이 null 일 경우
            if(newBrand.getName() == null)
                return JsonGenerator.getErrorJsonResponse(ErrorCode.UNEXPECTED_ATTRIBUTE);

            return JsonGenerator.getErrorJsonResponse(ErrorCode.UNIQUE_KEY_VIOLATION);
        }
        catch(RuntimeException runtimeException){
            // name != empty
            // 바꾸려는 데이터가 없을 경우
            return JsonGenerator.getErrorJsonResponse(ErrorCode.DATA_NOT_FOUND);
        }
    }

    @GetMapping("/list/all")
    public String getAllBrands() {
        return JsonGenerator.getSuccessJsonResponse(brandService.findAll());
    }
    @PostMapping("/list")
    public String findBrandByName(@RequestBody Brand brand) {
        if (brand.getName() == null)
            return JsonGenerator.getErrorJsonResponse(ErrorCode.UNEXPECTED_ATTRIBUTE);

        Optional<Brand> findBrand = brandService.findByName(brand.getName());
        return (findBrand.isPresent())?
                JsonGenerator.getSuccessJsonResponse(findBrand.get())
                : JsonGenerator.getErrorJsonResponse(ErrorCode.DATA_NOT_FOUND);
    }
}
