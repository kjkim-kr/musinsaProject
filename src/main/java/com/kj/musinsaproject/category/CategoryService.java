package com.kj.musinsaproject.category;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public boolean deleteByName(String name){
        long d = categoryRepository.deleteByName(name);
        return d > 0;
    }

    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<Category> findAll() {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("addDate"));
        return categoryRepository.findAll(Sort.by(sorts));
    }
}
