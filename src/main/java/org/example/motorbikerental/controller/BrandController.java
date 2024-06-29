package org.example.motorbikerental.controller;


import jakarta.persistence.EntityNotFoundException;
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

    @GetMapping("/getAllBrand/{page}/{pageSize}")
    public ResponseEntity<Page<Brand>> listBrandWithPagination(@PathVariable int page, @PathVariable int pageSize) {
        Page<Brand> brandPage = brandService.getBrandWithPagination(page,pageSize);
        return ResponseEntity.ok(brandPage);
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
    @PatchMapping("/updateBrand/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @RequestBody Brand brand) {
        try {
            Brand updatedBrand = brandService.updateBrand(id, brand);
            if (updatedBrand == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(updatedBrand);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/deleteBrand/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id){
        try{
            brandService.deleteBrand(id);
            return new ResponseEntity<>("Brand with ID " + id + " is deleted", HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
