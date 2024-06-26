package org.example.motorbikerental.controller;


import lombok.RequiredArgsConstructor;
import org.example.motorbikerental.entity.Brand;
import org.example.motorbikerental.exception.ValidationException;
import org.example.motorbikerental.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
public class BrandController {

    @Autowired
    private final BrandService brandService;

    @GetMapping("/getAllBrand")
    public ResponseEntity<List<Brand>> listBrand() {
        return ResponseEntity.ok(brandService.getAllBrand());
    }


    @PostMapping("/createBrand")
    public ResponseEntity<?> createBrand(@RequestBody Brand brand) {
        try{
            Brand createBrand = brandService.createNewBrand(brand);
            return ResponseEntity.ok(createBrand);
        }catch (ValidationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }



}
