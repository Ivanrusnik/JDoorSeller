package com.jds.dao.entity;

import com.jds.model.LimiItem;
import com.jds.model.SerializingFields;
import com.jds.model.modelEnum.TypeOfFurniture;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Door_Furniture")
public class DoorFurniture implements LimiItem, SerializingFields,Comparable<DoorFurniture> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "idManufacturerProgram", nullable = false)
    private String idManufacturerProgram;

    @Column(name = "typeOfFurniture")
    @Enumerated(EnumType.STRING)
    private TypeOfFurniture typeOfFurniture;

    @Column(name = "name")
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "doorType_id")
    private DoorType doorType;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "itCylinderLock")
    private int itCylinderLock;

    @Column(name = "isTwoSectionLock")
    private int isTwoSectionLock;

    @Column(name = "comment")
    private String comment;

    @Column(name = "longKey")
    private int longKey;

    @Column(name = "bugelHandle")
    private int bugelHandle;

    @Column(name = "armorLock")
    private int armorLock;

    @Column(name = "picturePathFirst")
    private String picturePathFirst;

    @Column(name = "picturePathSecond")
    private String picturePathSecond;

    @Column(name = "sketchPathFirst")
    private String sketchPathFirst;

    @Column(name = "sketchPathSecond")
    private String sketchPathSecond;

    @Column(name = "forWarmDoors")
    private int forWarmDoors;

    @Column(name = "numberOfDoorLeaves")
    private int numberOfDoorLeaves;

    @Column(name = "price")
    private double price;

    @Column(name = "pricecomit")
    private String priceComit;

    //glass

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toning")
    private List<DoorGlass> toning;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeDoorGlass")
    private List<DoorGlass> typeDoorGlass;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "armor")
    private List<DoorGlass> armors;

    //Furniture

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "topLock")
    private List<DoorFurniture> topLock;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "topinternaLockDecoration")
    private List<DoorFurniture> topinternaLockDecoration;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "topouterLockDecoration")
    private List<DoorFurniture> topouterLockDecoration;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toplockCylinder")
    private List<DoorFurniture> toplockCylinder;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lowerLock")
    private List<DoorFurniture> lowerLock;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lowerinternaLockDecoration")
    private List<DoorFurniture> lowerinternaLockDecoration;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lowerouterLockDecoration")
    private List<DoorFurniture> lowerouterLockDecoration;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lowerlockCylinder")
    private List<DoorFurniture> lowerlockCylinder;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "handle")
    private List<DoorFurniture> handle;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "closer")
    private List<DoorFurniture> closer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "endDoorLock")
    private List<DoorFurniture> endDoorLock;

    public DoorFurniture() {
        idManufacturerProgram="";
        quantity = 0;
        itCylinderLock = 0;
        isTwoSectionLock = 0;
        comment = "";
        longKey = 0;
        bugelHandle = 0;
        armorLock = 0;
        picturePathFirst = "";
        picturePathSecond = "";
        sketchPathFirst = "";
        sketchPathSecond = "";
        forWarmDoors = 0;
        numberOfDoorLeaves = 0;
        price = 0;
        priceComit = "";
    }

    public DoorFurniture(@NonNull LimitationDoor lim) {
        this.id = lim.getItemId();
        this.name = lim.getFirstItem();
        this.picturePathFirst = lim.getPicturePath();
    }

    public DoorFurniture setNuulLazyFild() {

        setDoorType(null);

        setToning(null);
        setArmors(null);
        setTypeDoorGlass(null);

        setTopLock(null);
        setTopinternaLockDecoration(null);
        setTopouterLockDecoration(null);
        setToplockCylinder(null);
        setLowerLock(null);
        setLowerinternaLockDecoration(null);
        setLowerouterLockDecoration(null);
        setLowerlockCylinder(null);
        setHandle(null);
        setCloser(null);
        setEndDoorLock(null);
        return this;
    }

    public DoorFurniture clearNonSerializingFields() {
        return setNuulLazyFild();
    }

    public void makeRightNamePictureDoorType() {

        picturePathFirst = picturePathFirst.replace("CalculationDoorsAutonomous", "images");
        picturePathFirst = picturePathFirst + ".jpg";

        sketchPathFirst = sketchPathFirst.replace("CalculationDoorsAutonomous", "images");
        sketchPathSecond = sketchPathSecond.replace("CalculationDoorsAutonomous", "images");
    }

    public int compareTo(DoorFurniture doorFurniture) {
        return doorFurniture.getName().toUpperCase().compareTo(this.getName().toUpperCase());
    }
}
