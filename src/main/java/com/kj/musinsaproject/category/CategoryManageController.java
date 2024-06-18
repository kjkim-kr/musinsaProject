package com.kj.musinsaproject.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("manage/category")
@RequiredArgsConstructor
@Controller
public class CategoryManageController {
    private final CategoryService categoryService;

    @GetMapping("/")
    public String manageCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category";
    }
}
