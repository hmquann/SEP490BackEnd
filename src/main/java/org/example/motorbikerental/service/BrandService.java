package org.example.motorbikerental.service;


import org.example.motorbikerental.entity.Brand;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrandService {
    Page<Brand> getBrandWithPagination(int page, int pageSize);
    Brand createNewBrand(Brand brand);
    void deleteBrand(Long id);
    Brand updateBrand(Long id, Brand brand);


}
