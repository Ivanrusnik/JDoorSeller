package com.jds.model;

public class LineCostList {

    private String name;
    private  int group;
    private boolean headline;
    private int cost;

    public LineCostList(String name, int group, boolean headline, int cost) {
        this.name = name;
        this.group = group;
        this.headline = headline;
        this.cost = cost;
    }

    public LineCostList() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public boolean getHeadline() {
        return headline;
    }

    public void setHeadline(boolean headline) {
        this.headline = headline;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isHeadline() {
        return headline;
    }
}
