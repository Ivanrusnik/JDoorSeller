package com.jds.model.cutting;

import com.jds.dao.entity.DoorEntity;
import com.jds.dao.entity.Metal;
import com.jds.dao.entity.SizeOfDoorParts;
import com.udojava.evalex.Expression;

import java.util.ArrayList;
import java.util.List;

public class DoorPart implements Comparable<DoorPart> {

    private String name;
    private int width;
    private int height;
    private int quantity;
    private boolean posted;
    private boolean inverted;
    private int index;
    private Metal metal;
    private double space;
    private int positioningTop;
    private int positioningLeft;

    public DoorPart() {
    }

    public static DoorPart getInstansInverted(DoorPart doorPart) {
        DoorPart newDoorPart = new DoorPart();
        newDoorPart.setName(doorPart.getName());
        newDoorPart.setWidth(doorPart.getHeight());
        newDoorPart.setHeight(doorPart.getWidth());
        newDoorPart.setIndex(doorPart.getIndex());
        newDoorPart.setInverted(true);
        newDoorPart.setQuantity(doorPart.getQuantity());
        newDoorPart.setSpace(doorPart.getSpace());
        newDoorPart.setMetal(doorPart.getMetal());
        newDoorPart.setPosted(false);
        return newDoorPart;
    }

    @Override
    public int compareTo(DoorPart o) {
        return (int) ((o.getSpace() - this.getSpace()) * 1000);
    }

    public DoorPart(String name, int width, int height, int quantity) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.quantity = quantity;
        this.space = (double) (width * height) / 1000000;
        this.inverted = false;
        this.posted = false;
    }

    public static List<DoorPart> getDoopPartsList(List<SizeOfDoorParts> list, DoorEntity Door) {

        List<DoorPart> partList = new ArrayList<>();


        for (SizeOfDoorParts parts : list) {

            String size = parsePatternForCalculation(parts.getCondition(), Door, true);
            int booCondition = eval(size);

            if (booCondition == 1) {

                DoorPart doorPart = new DoorPart();

                size = parsePatternForCalculation(parts.getHeight(), Door, false);
                doorPart.setHeight(eval(size));

                size = parsePatternForCalculation(parts.getWidth(), Door, false);
                doorPart.setWidth(eval(size));

                doorPart.setName(parts.getName());
                doorPart.setQuantity(Integer.parseInt(parts.getQuantity()));

                partList.add(doorPart);
            }


        }

        return partList;
    }

    public static String parsePatternForCalculation(String condition, DoorEntity door, Boolean provision) {

        //H - widthDoor
        condition = condition.replace("H", String.valueOf(door.getHeightDoor()));

        //L - widthDoor
        condition = condition.replace("L", String.valueOf(door.getWidthDoor()));

        //g - deepnessDoor
        condition = condition.replace("g", String.valueOf(door.getDeepnessDoor()));

        //t - thicknessDoorLeaf
        condition = condition.replace("t", String.valueOf(door.getThicknessDoorLeaf()));

        //OS - аctivDoorLeafWidth
        condition = condition.replace("OS", String.valueOf(door.getActiveDoorLeafWidth()));

        //K - glassWidth
        condition = condition.replace("K", String.valueOf(door.getDoorGlass().getGlassWidth()));

        //J - glassWidth
        condition = condition.replace("J", String.valueOf(door.getDoorGlass().getGlassHeight()));

        if (provision) {
            //"и" - and
            condition = condition.replace("и", "&&");// only for test
        } else {
            //" " - +
            condition = condition.replace(" ", "+");// only for test
        }

        return condition;
    }

    public static int eval(String function) {
        if (function.isEmpty()) {
            return 1;
        }
        return new Expression(function).eval().intValue();

    }

    public static double evalDouble(String function) {
        if (function.isEmpty()) {
            return 1;
        }
        return new Expression(function).eval().doubleValue();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isPosted() {
        return posted;
    }

    public void setPosted(boolean posted) {
        this.posted = posted;
    }

    public boolean isInverted() {
        return inverted;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    public int isIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Metal getMetal() {
        return metal;
    }

    public void setMetal(Metal metal) {
        this.metal = metal;
    }

    public double getSpace() {
        return space;
    }

    public void setSpace(double space) {
        this.space = space;
    }

    public int getIndex() {
        return index;
    }

    public int getPositioningTop() {
        return positioningTop;
    }

    public void setPositioningTop(int positioningTop) {
        this.positioningTop = positioningTop;
    }

    public int getPositioningLeft() {
        return positioningLeft;
    }

    public void setPositioningLeft(int positioningLeft) {
        this.positioningLeft = positioningLeft;
    }
}
