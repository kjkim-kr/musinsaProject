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
        // 삭제된 행의 개수를 받아서, 삭제 여부를 판단한다.
        long d = brandRepository.deleteByName(name);
        return d > 0;
    }

    @Transactional
    public Brand updateBrandById(long id, Brand brand) {
        if(brand.getName() == null)
            throw new IllegalArgumentException("Brand name cannot be null");

        // 수정하려는 brand를 id로 찾아서, 이름을 수정하여 저장한다.
        // 해당 id가 없거나, 바꾸려는 이름이 중복인 경우 에러가 발생한다.
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
