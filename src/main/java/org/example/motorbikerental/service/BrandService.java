package org.example.motorbikerental.service;


import org.example.motorbikerental.entity.Brand;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrandService {
    public List<Brand> getAllBrand();
    Brand createNewBrand(Brand brand);


}
