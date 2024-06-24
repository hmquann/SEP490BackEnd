package org.example.motorbikerental.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "[Brand]")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "brandId")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name="origin")
    private String origin;


    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @JsonManagedReference()
    @JsonIgnore
    private List<Model>modelSet=new ArrayList<>();

    public void addModel(Model model) {
        modelSet.add(model);
        model.setBrand(this);
    }

}
