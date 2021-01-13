package com.jds.dao.entity;

import com.jds.model.modelEnum.TypeOfSalaryConst;

import javax.persistence.*;

@Entity
@Table(name = "salary_constants")
public class SalaryConstants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private TypeOfSalaryConst name;

    @Column(name = "value")
    private double value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TypeOfSalaryConst getName() {
        return name;
    }

    public void setName(TypeOfSalaryConst name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
