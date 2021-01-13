package com.jds.dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "Size_Door_Parts")
public class SizeOfDoorParts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "doorType_id")
    private DoorType doorType;

    @Column(name = "condition")
    private String condition;

    @Column(name = "width")
    private String width;

    @Column(name = "height")
    private String height;

    @Column(name = "quantity")
    private String quantity;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void decodeAfterJson(){
        this.condition = this.condition.replace("//","*");
        this.condition = this.condition.replace("/","+");

        this.height = this.height.replace("//","*");
        this.height = this.height.replace("/","+");

        this.width = this.width.replace("//","*");
        this.width = this.width.replace("/","+");
    }

}
