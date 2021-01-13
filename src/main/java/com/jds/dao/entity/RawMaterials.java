package com.jds.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "raw_materials")
public class RawMaterials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "idManufacturerProgram", nullable = false)
    private String idManufacturerProgram;

    @Column(name = "price", nullable = false)
    private double price;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rawMaterials", cascade = CascadeType.ALL)
    private List<SpecificationSetting> specificationSettings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdManufacturerProgram() {
        return idManufacturerProgram;
    }

    public void setIdManufacturerProgram(String idManufacturerProgram) {
        this.idManufacturerProgram = idManufacturerProgram;
    }

    public List<SpecificationSetting> getSpecificationSettings() {
        return specificationSettings;
    }

    public void setSpecificationSettings(List<SpecificationSetting> specificationSettings) {
        this.specificationSettings = specificationSettings;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
