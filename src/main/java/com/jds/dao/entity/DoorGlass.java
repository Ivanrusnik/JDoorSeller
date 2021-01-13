package com.jds.dao.entity;

import com.jds.model.modelEnum.TypeOfFurniture;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "glass")
public class DoorGlass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "typeDoorGlass")
    private DoorFurniture typeDoorGlass;

    @ManyToOne()
    @JoinColumn(name = "toning")
    private DoorFurniture toning;

    @ManyToOne()
    @JoinColumn(name = "armor")
    private DoorFurniture armor;

    @Column(name = "glassWidth")
    private int glassWidth;

    @Column(name = "glassHeight")
    private int glassHeight;

    @Column(name = "leftGlassPosition")
    private int leftGlassPosition;

    @Column(name = "bottomGlassPosition")
    private int bottomGlassPosition;

    @OneToOne(mappedBy = "doorGlass",fetch = FetchType.LAZY)
    private DoorEntity door;

    public DoorGlass() {

    }

    public boolean exists(){

        if ((typeDoorGlass!=null)&&(glassWidth>0)&&(glassHeight>0)){
            return true;
        }
        return false;
    }

    public double getSpace(){

        double S =((double)glassWidth*(double) glassHeight)/1000000;

        return S;
    }

    public DoorFurniture getTypeDoorGlass() {
        return typeDoorGlass;
    }

    public int getCost(TypeOfFurniture type,double space){

        if(type==TypeOfFurniture.TYPE_GLASS){
            return (int)(typeDoorGlass.getPrice()*space);
        }
        if(armor!=null && type==TypeOfFurniture.ARMOR_GLASS_PELLICLE){
            return (int)(armor.getPrice()*space);
        }
        if(toning!=null && type==TypeOfFurniture.GLASS_PELLICLE){
            return (int)(toning.getPrice()*space);
        }

        return 0;
    }

    public DoorGlass clearNonSerializingFields(){

        door = null;

        if(toning!=null){
            toning.setNuulLazyFild();
        }
        if(armor!=null){
            armor.setNuulLazyFild();
        }
        if(typeDoorGlass!=null){
            typeDoorGlass.setNuulLazyFild();
        }
        return this;
    }

    public void setTypeDoorGlass(DoorFurniture typeDoorGlass) {
        this.typeDoorGlass = typeDoorGlass;
    }

    public DoorFurniture getToning() {
        return toning;
    }

    public void setToning(DoorFurniture toning) {
        this.toning = toning;
    }

    public DoorFurniture getArmor() {
        return armor;
    }

    public void setArmor(DoorFurniture armor) {
        this.armor = armor;
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

    public int getLeftGlassPosition() {
        return leftGlassPosition;
    }

    public void setLeftGlassPosition(int leftGlassPosition) {
        this.leftGlassPosition = leftGlassPosition;
    }

    public int getBottomGlassPosition() {
        return bottomGlassPosition;
    }

    public void setBottomGlassPosition(int bottomGlassPosition) {
        this.bottomGlassPosition = bottomGlassPosition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DoorEntity getDoor() {
        return door;
    }

    public void setDoor(DoorEntity door) {
        this.door = door;
    }

    public int getGlassCost (List<LimitationDoor> templateGlass , int glassId){

        LimitationDoor lim = templateGlass.stream()
                .filter((p)-> p.getItemId() == glassId)
                .findFirst().orElse(null);

        if (lim != null){
            return lim.getCost();
        } else return 0;
    }


}
