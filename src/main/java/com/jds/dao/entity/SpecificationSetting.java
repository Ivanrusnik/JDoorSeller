package com.jds.dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "specification_setting")
public class SpecificationSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "rawMaterials_id")
    private RawMaterials rawMaterials;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "formula_id")
    private MaterialFormula formula;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "doorType_id")
    private DoorType doorType;

    @Column(name = "resource")
    private double resource;

    @Column(name = "metal")
    private double metal;

    public double countByTheFormula(DoorEntity door){

       return formula.Calculation(door);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RawMaterials getRawMaterials() {
        return rawMaterials;
    }

    public void setRawMaterials(RawMaterials rawMaterials) {
        this.rawMaterials = rawMaterials;
    }

    public MaterialFormula getFormula() {
        return formula;
    }

    public void setFormula(MaterialFormula formula) {
        this.formula = formula;
    }

    public double getResource() {
        return resource;
    }

    public void setResource(double resource) {
        this.resource = resource;
    }

    public DoorType getDoorType() {
        return doorType;
    }

    public void setDoorType(DoorType doorType) {
        this.doorType = doorType;
    }

    public double getMetal() {
        return metal;
    }

    public void setMetal(double metal) {
        this.metal = metal;
    }


}
