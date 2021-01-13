package com.jds.model.cutting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Sheet {

    int width;
    int height;
    double amountWorkSpace;
    List<HorizontalCut> horCutList;
    List<DoorPart> containsParts;

    public Sheet() {

    }

    public Sheet(int width, int height) {
        this.width = width;
        this.height = height;
        this.horCutList = new LinkedList<>();
        this.containsParts = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            VerticalСut verticalСut = new VerticalСut(false, 0, height - i, width, 0, width, true);
            this.horCutList.add(new HorizontalCut(verticalСut));
        }

    }

    public Sheet(Sheet sheet) {
        this(sheet.getWidth(), sheet.getHeight());
    }

    public double getResidueSpace() {
        double S = (width * height) / 1000000;
        return (S - amountWorkSpace) / S;
    }

    public void calculateAmountWorkSpace() {
        for (DoorPart doorPart : containsParts) {
            amountWorkSpace += (double) ((doorPart.getHeight()) * (doorPart.getWidth())) / 1000000;
        }
    }

    public double getAmountWorkSpace() {
        return amountWorkSpace;
    }

    public void setAmountWorkSpace(double amountWorkSpace) {
        this.amountWorkSpace = amountWorkSpace;
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

    public List<HorizontalCut> getHorCutList() {
        return horCutList;
    }

    public void setHorCutList(List<HorizontalCut> horCutList) {
        this.horCutList = horCutList;
    }

    public void printSheet() {

        int step = 50;
        for (int i = 0; i < horCutList.size(); i += step) {

            String printLine = "";
            String residueCut = "";
            List<VerticalСut> verСutList = horCutList.get(i).getVerticalСutList();
            for (int h = 0; h < verСutList.size(); h++) {
                VerticalСut verСut = verСutList.get(h);
                if (verСut.isFull() | !verСut.getLast())
                    printLine = printLine + verСut.toString();
                else
                    residueCut = verСut.toString();
            }
            System.out.println(printLine + residueCut);
        }
    }

    public List<DoorPart> getContainsParts() {
        return containsParts;
    }

    public void setContainsParts(List<DoorPart> containsParts) {
        this.containsParts = containsParts;
    }


}
