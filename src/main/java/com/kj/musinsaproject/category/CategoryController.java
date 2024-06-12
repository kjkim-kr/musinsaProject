package com.kj.musinsaproject.category;

import com.kj.musinsaproject.response.ErrorCode;
import com.kj.musinsaproject.response.JsonGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("api/category")
@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    public String addCategory(@RequestBody Category category) {
        try {
            Category addCategory = categoryService.create(category);
            return JsonGenerator.getSuccessJsonResponse(addCategory);
        }
        catch(DataIntegrityViolationException dataIntegrityViolationException) {
            if(category.getName() == null)
                return JsonGenerator.getErrorJsonResponse(ErrorCode.UNEXPECTED_ATTRIBUTE);

            return JsonGenerator.getErrorJsonResponse(ErrorCode.UNIQUE_KEY_VIOLATION);
        }
    }

    @GetMapping("/list/all")
    public String getAllCategories() {
        return JsonGenerator.getSuccessJsonResponse(categoryService.findAll());
    }
    @PostMapping("/list")
    public String findCategoryByName(@RequestBody Category category) {
        if (category.getName() == null)
            return JsonGenerator.getErrorJsonResponse(ErrorCode.UNEXPECTED_ATTRIBUTE);

        Optional<Category> foundCategory = categoryService.findByName(category.getName());
        return (foundCategory.isPresent())?
                JsonGenerator.getSuccessJsonResponse(foundCategory.get())
                : JsonGenerator.getErrorJsonResponse(ErrorCode.DATA_NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public String deleteBrandByName(@RequestBody Category category) {
        if (category.getName() == null)
            return JsonGenerator.getErrorJsonResponse(ErrorCode.UNEXPECTED_ATTRIBUTE);

        boolean isDeleted = categoryService.deleteByName(category.getName());

        return (isDeleted)?
                JsonGenerator.getSuccessJsonResponse()
                : JsonGenerator.getErrorJsonResponse(ErrorCode.DELETE_FAILED);
    }
}
