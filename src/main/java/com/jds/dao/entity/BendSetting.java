package com.jds.dao.entity;

import javax.persistence.*;


@Entity
@Table(name = "bend_setting")
public class BendSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "doortype_id")
    private DoorType doorType;

    @Column(name = "metal")
    private double metal;

    @Column(name = "sealingline")
    private int sealingLine;

    @Column(name = "bend")
    private int bend;

    @Column(name = "guillotine")
    private int guillotine;

    public DoorType getDoorType() {
        return doorType;
    }

    public void setDoorType(DoorType doorType) {
        this.doorType = doorType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DoorType getDoorTypeId() {
        return doorType;
    }

    public void setDoorTypeId(DoorType doorTypeId) {
        this.doorType = doorType;
    }

    public double getMetal() {
        return metal;
    }

    public void setMetal(double metal) {
        this.metal = metal;
    }

    public int getSealingLine() {
        return sealingLine;
    }

    public void setSealingLine(int sealingLine) {
        this.sealingLine = sealingLine;
    }

    public int getBend() {
        return bend;
    }

    public void setBend(int bend) {
        this.bend = bend;
    }

    public int getGuillotine() {
        return guillotine;
    }

    public void setGuillotine(int guillotine) {
        this.guillotine = guillotine;
    }
}
