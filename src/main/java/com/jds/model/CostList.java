package com.jds.model;

import java.util.ArrayList;
import java.util.List;

public class CostList {

    int totalCost;
    List<LineCostList> list;

    public void addLine(String name,int group,boolean headline,int cost){
        addLine(new LineCostList(name,group,headline,cost));
   }

    public void addLine(LineCostList line){
        list.add(line);
        totalCost +=line.getCost();
    }

    public void addAllLine(List<LineCostList> list){
        for (LineCostList line: list) {
            addLine(line);
        }
    }

    public CostList() {
        totalCost = 0;
        list = new ArrayList<>();
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public List<LineCostList> getCostList() {
        return list;
    }

    public void setCostList(List<LineCostList> list) {
        this.list = list;
    }

    public List<LineCostList> getList() {
        return list;
    }

    public void setList(List<LineCostList> list) {
        this.list = list;
    }

    public int getCostByGroup(int group){
        return list.stream()
                .filter(elem -> elem.getGroup() == group)
                .mapToInt((s) -> s.getCost())
                .sum();
    }
}
