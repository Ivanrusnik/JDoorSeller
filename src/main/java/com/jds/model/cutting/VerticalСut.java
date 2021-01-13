package com.jds.model.cutting;

public class VerticalСut {

    Boolean full;
    int index;
    int Hg;
    int Hv;
    int k1;
    int k2;
    Boolean last;

    public VerticalСut(Boolean full,
                       int index,
                       int hg,
                       int hv,
                       int k1,
                       int k2,
                       Boolean last) {
        this.full = full;
        this.index = index;
        Hg = hg;
        Hv = hv;
        this.k1 = k1;
        this.k2 = k2;
        this.last = last;
    }

    @Override
    public String toString() {

        int step = 50;
        String line="";
        String printIndex =  getPrintIndex();

        int w = k1;
        while (w < k2){
            line = line + printIndex;
            w +=step;
        }

        return line;
    }

    private String getPrintIndex(){
        if (!isFull())
         return "-";

        return String.valueOf(index);
    }

    public Boolean isFull() {
        return full;
    }

    public void setFull(Boolean full) {
        this.full = full;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getHg() {
        return Hg;
    }

    public void setHg(int hg) {
        Hg = hg;
    }

    public int getHv() {
        return Hv;
    }

    public void setHv(int hv) {
        Hv = hv;
    }

    public int getK1() {
        return k1;
    }

    public void setK1(int k1) {
        this.k1 = k1;
    }

    public int getK2() {
        return k2;
    }

    public void setK2(int k2) {
        this.k2 = k2;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }
}
