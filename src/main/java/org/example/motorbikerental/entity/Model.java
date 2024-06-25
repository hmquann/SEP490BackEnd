package org.example.motorbikerental.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "[Model]")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="model_name")
    private String modelName;

    @Column(name="cylinder_capacity")
    private int cylinderCapacity;

    @Column(name="fuel_type")
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Column(name="fuel_consumption")
    private float fuelConsumption;
    @Column(name="model_type")
    @Enumerated(EnumType.STRING)
    private ModelType modelType;
    @ManyToOne
    @JoinColumn(name="brand_id")
//    @JsonBackReference()
    private Brand brand;
}
