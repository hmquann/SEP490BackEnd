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
    public Page<Brand> getBrandWithPagination(int page, int pageSize){
        return brandRepository.findAll(PageRequest.of(page,pageSize));
    }



    @Override
    public Brand createNewBrand(Brand brand) {
        if(brandRepository.existsByBrandName(brand.getBrandName())){
            throw new ValidationException("Brand name already exists");
        }
        return brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(Long id) {
        if (!brandRepository.existsByBrandId(id)) {
            throw new EntityNotFoundException("Brand with ID " + id + " not found");
        }
        brandRepository.deleteById(id);
    }

    @Override
    public Brand updateBrand(Long id, Brand brand) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isEmpty()) {
            throw new EntityNotFoundException("Brand with ID " + id + " not found");
        }else{
            Brand brandToUpdate = brandOptional.get();
            if(brand.getBrandName() != null && !brand.getBrandName().equals(brandToUpdate.getBrandName())){
                if(brandRepository.existsByBrandName(brand.getBrandName())){
                    throw new ValidationException("Brand name already exists");
                }
                brandToUpdate.setBrandName(brand.getBrandName());
            }
            brandToUpdate.setOrigin(brand.getOrigin());
            return brandRepository.save(brandToUpdate);
        }
    }


}
