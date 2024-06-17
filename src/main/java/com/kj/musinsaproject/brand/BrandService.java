package com.kj.musinsaproject.brand;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepository brandRepository;

    @Transactional
    public Brand create(Brand brand) {
        return brandRepository.save(brand);
    }

    @Transactional
    public boolean deleteByName(String name){
        long d = brandRepository.deleteByName(name);
        return d > 0;
    }

    @Transactional
    public Brand updateBrandById(long id, Brand brand) {
        if(brand.getName() == null)
            throw new IllegalArgumentException("Brand name cannot be null");

        return brandRepository.findById(id)
                .map(oldBrand -> {
                    oldBrand.setName(brand.getName());
                    return brandRepository.save(oldBrand);
                })
                .orElseThrow(() -> new RuntimeException("Brand not found"));
    }

    public Optional<Brand> findByName(String name) {
        return brandRepository.findByName(name);
    }

    public List<Brand> findAll() {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("addDate"));
        return brandRepository.findAll(Sort.by(sorts));
    }
}
