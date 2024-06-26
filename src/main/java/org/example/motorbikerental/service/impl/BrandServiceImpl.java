package org.example.motorbikerental.service.impl;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.motorbikerental.entity.Brand;
import org.example.motorbikerental.exception.ValidationException;
import org.example.motorbikerental.repository.BrandRepository;
import org.example.motorbikerental.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    @Autowired
    private final BrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrand() {
        return brandRepository.findAll();
    }



    @Override
    public Brand createNewBrand(Brand brand) {
        if(brandRepository.existsByBrandName(brand.getBrandName())){
            throw new ValidationException("Brand name already exists");
        }
        return brandRepository.save(brand);
    }




}
