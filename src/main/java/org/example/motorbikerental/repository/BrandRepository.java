package org.example.motorbikerental.repository;

import org.example.motorbikerental.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByBrandName(String brandName);

    boolean existsByBrandId(Long id);

}
