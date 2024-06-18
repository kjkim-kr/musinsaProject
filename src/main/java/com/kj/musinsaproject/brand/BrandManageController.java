package com.kj.musinsaproject.brand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("manage/brand")
@RequiredArgsConstructor
@Controller
public class BrandManageController {
    private final BrandService brandService;

    @GetMapping("/")
    public String manageBrands(Model model) {
        List<Brand> brands = brandService.findAll();
        model.addAttribute("brands", brands);
        return "brand";
    }
}
