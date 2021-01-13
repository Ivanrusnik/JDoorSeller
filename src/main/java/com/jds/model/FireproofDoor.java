package com.jds.model;

import com.jds.model.cutting.Sheet;

import java.util.List;

public class FireproofDoor {

    private int id;
    private String name;
    private String doorClass;
    private String doorType;
    private int widthDoor;
    private int heightDoor;
    private int аctivDoorLeafWidth;
    private int doorFanlightheight;
    private String metal;
    private int deepnessDoor;
    private int thicknessDoorLeaf;
    private String sideDoorOpen;
    private int innerDoorOpen;
    private int doorstep;
    private int stainlessSteelDoorstep;
    private int topDoorTrim;
    private int leftDoorTrim;
    private int rightDoorTrim;
    private int price;
    private String doorColor;
    private int glassWidth;
    private int glassHeight;
    private List<Sheet> sheets;



    public static FireproofDoor createNewDoor(){

        return new FireproofDoor();
    }

    public int getGlassWidth() {
        return glassWidth;
    }

    public void setGlassWidth(int glassWidth) {
        this.glassWidth = glassWidth;
    }

    public int getGlassHeight() {
        return glassHeight;
    }

    public void setGlassHeight(int glassHeight) {
        this.glassHeight = glassHeight;
    }

    public String getDoorType() {
        return doorType;
    }

    public void setDoorType(String doorType) {
        this.doorType = doorType;
    }

    public int getАctivDoorLeafWidth() {
        return аctivDoorLeafWidth;
    }

    public void setАctivDoorLeafWidth(int аctivDoorLeafWidth) {
        this.аctivDoorLeafWidth = аctivDoorLeafWidth;
    }

    public int getDoorFanlightheight() {
        return doorFanlightheight;
    }

    public void setDoorFanlightheight(int doorFanlightheight) {
        this.doorFanlightheight = doorFanlightheight;
    }

    public String getDoorColor() {
        return doorColor;
    }

    public void setDoorColor(String doorColor) {
        this.doorColor = doorColor;
    }

    public FireproofDoor() {

    }

    public FireproofDoor(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInnerDoorOpen() {
        return innerDoorOpen;
    }

    public void setInnerDoorOpen(int innerDoorOpen) {
        this.innerDoorOpen = innerDoorOpen;
    }

    public int getDoorstep() {
        return doorstep;
    }

    public void setDoorstep(int doorstep) {
        this.doorstep = doorstep;
    }

    public int getStainlessSteelDoorstep() {
        return stainlessSteelDoorstep;
    }

    public void setStainlessSteelDoorstep(int stainlessSteelDoorstep) {
        this.stainlessSteelDoorstep = stainlessSteelDoorstep;
    }

    public int getTopDoorTrim() {
        return topDoorTrim;
    }

    public void setTopDoorTrim(int topDoorTrim) {
        this.topDoorTrim = topDoorTrim;
    }

    public int getLeftDoorTrim() {
        return leftDoorTrim;
    }

    public void setLeftDoorTrim(int leftDoorTrim) {
        this.leftDoorTrim = leftDoorTrim;
    }

    public int getRightDoorTrim() {
        return rightDoorTrim;
    }

    public void setRightDoorTrim(int rightDoorTrim) {
        this.rightDoorTrim = rightDoorTrim;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoorClass() {
        return doorClass;
    }

    public void setDoorClass(String doorClass) {
        this.doorClass = doorClass;
    }

    public int getWidthDoor() {
        return widthDoor;
    }

    public void setWidthDoor(int widthDoor) {
        this.widthDoor = widthDoor;
    }

    public int getHeightDoor() {
        return heightDoor;
    }

    public void setHeightDoor(int heightDoor) {
        this.heightDoor = heightDoor;
    }

    public String getMetal() {
        return metal;
    }

    public void setMetal(String metal) {
        this.metal = metal;
    }

    public int getDeepnessDoor() {
        return deepnessDoor;
    }

    public void setDeepnessDoor(int deepnessDoor) {
        this.deepnessDoor = deepnessDoor;
    }

    public int getThicknessDoorLeaf() {
        return thicknessDoorLeaf;
    }

    public void setThicknessDoorLeaf(int thicknessDoorLeaf) {
        this.thicknessDoorLeaf = thicknessDoorLeaf;
    }

    public String getSideDoorOpen() {
        return sideDoorOpen;
    }

    public void setSideDoorOpen(String sideDoorOpen) {
        this.sideDoorOpen = sideDoorOpen;
    }

    public List<Sheet> getSheets() {
        return sheets;
    }

    public void setSheets(List<Sheet> sheets) {
        this.sheets = sheets;
    }
}



