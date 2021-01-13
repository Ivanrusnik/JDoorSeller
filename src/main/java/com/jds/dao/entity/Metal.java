package com.jds.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Metal")
public class Metal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "id_manufacturer_program", nullable = false)
    private String idManufacturerProgram;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_displayed", nullable = false)
    private double nameDisplayed;

    @Column(name = "index_heft")
    private int indexHeft;

    @Column(name = "is_used")
    private int isUsed;

    @Column(name = "price")
    private int price;

    public Metal() {
        isUsed = 1;
    }
}
