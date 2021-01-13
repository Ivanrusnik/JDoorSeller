package com.jds.dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "salary_setting")
public class SalarySetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "metal")
    private double metal;

    @Column(name = "weldingForOneLeaf")
    private int weldingForOneLeaf;

    @Column(name = "weldingForTwoLeaf")
    private int weldingForTwoLeaf;

    @Column(name = "weldingForOneLeafMDF")
    private int weldingForOneLeafMDF;

    @Column(name = "weldingForTwoLeafMDF")
    private int weldingForTwoLeafMDF;

    @Column(name = "weldingForOneLeafHot")
    private int weldingForOneLeafHot;

    @Column(name = "weldingForTwoLeafHot")
    private int weldingForTwoLeafHot;

    @Column(name = "contactweldingforoneleaf")
    private int contactWeldingForOneLeaf;

    @Column(name = "contactweldingfornwoleaf")
    private int contactWeldingForTwoLeaf;

    public SalarySetting() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMetal() {
        return metal;
    }

    public void setMetal(double metal) {
        this.metal = metal;
    }

    public int getContactWeldingForOneLeaf() {
        return contactWeldingForOneLeaf;
    }

    public void setContactWeldingForOneLeaf(int contactWeldingForOneLeaf) {
        this.contactWeldingForOneLeaf = contactWeldingForOneLeaf;
    }

    public int getContactWeldingForTwoLeaf() {
        return contactWeldingForTwoLeaf;
    }

    public void setContactWeldingForTwoLeaf(int contactWeldingForTwoLeaf) {
        this.contactWeldingForTwoLeaf = contactWeldingForTwoLeaf;
    }

    public int getWeldingForOneLeaf() {
        return weldingForOneLeaf;
    }

    public void setWeldingForOneLeaf(int weldingForOneLeaf) {
        this.weldingForOneLeaf = weldingForOneLeaf;
    }

    public int getWeldingForTwoLeaf() {
        return weldingForTwoLeaf;
    }

    public void setWeldingForTwoLeaf(int weldingForTwoLeaf) {
        this.weldingForTwoLeaf = weldingForTwoLeaf;
    }

    public int getWeldingForOneLeafMDF() {
        return weldingForOneLeafMDF;
    }

    public void setWeldingForOneLeafMDF(int weldingForOneLeafMDF) {
        this.weldingForOneLeafMDF = weldingForOneLeafMDF;
    }

    public int getWeldingForTwoLeafMDF() {
        return weldingForTwoLeafMDF;
    }

    public void setWeldingForTwoLeafMDF(int weldingForTwoLeafMDF) {
        this.weldingForTwoLeafMDF = weldingForTwoLeafMDF;
    }

    public int getWeldingForOneLeafHot() {
        return weldingForOneLeafHot;
    }

    public void setWeldingForOneLeafHot(int weldingForOneLeafHot) {
        this.weldingForOneLeafHot = weldingForOneLeafHot;
    }

    public int getWeldingForTwoLeafHot() {
        return weldingForTwoLeafHot;
    }

    public void setWeldingForTwoLeafHot(int weldingForTwoLeafHot) {
        this.weldingForTwoLeafHot = weldingForTwoLeafHot;
    }
}
