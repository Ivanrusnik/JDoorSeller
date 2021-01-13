package com.jds.model;

import com.jds.dao.entity.ImageEntity;
import com.jds.dao.entity.DoorFurniture;
import com.jds.dao.entity.LimitationDoor;
import com.jds.dao.entity.Metal;
import com.jds.model.image.Image;
import com.jds.model.modelEnum.TypeOfLimitionDoor;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestrictionOfSelectionFields {

    private int doorTypeid;
    private List<LimitationDoor> metal = new ArrayList<>();
    private List<LimitationDoor> widthDoor = new ArrayList<>();
    private List<LimitationDoor> heightDoor = new ArrayList<>();
    private List<LimitationDoor> widthDoorLeaf = new ArrayList<>();
    private List<LimitationDoor> heightDoorFanlight = new ArrayList<>();
    private List<LimitationDoor> deepnessDoor = new ArrayList<>();
    private List<LimitationDoor> thicknessDoorLeaf = new ArrayList<>();
    private List<LimitationDoor> colors = new ArrayList<>();
    private List<LimitationDoor> design = new ArrayList<>();
    private List<LimitationDoor> doorColor = new ArrayList<>();
    private List<LimitationDoor> doorDesign = new ArrayList<>();

    private List<LimitationDoor> additionalDoorSetting = new ArrayList<>();

    private List<LimitationDoor> doorstep = new ArrayList<>();
    private List<LimitationDoor> stainlessSteelDoorstep = new ArrayList<>();

    private List<LimitationDoor> firstSealingLine = new ArrayList<>();
    private List<LimitationDoor> secondSealingLine = new ArrayList<>();
    private List<LimitationDoor> thirdSealingLine = new ArrayList<>();

    private List<LimitationDoor> topDoorTrim = new ArrayList<>();
    private List<LimitationDoor> leftDoorTrim = new ArrayList<>();
    private List<LimitationDoor> rightDoorTrim = new ArrayList<>();

    private List<LimitationDoor> topDoorTrimSize = new ArrayList<>();
    private List<LimitationDoor> leftDoorTrimSize = new ArrayList<>();
    private List<LimitationDoor> rightDoorTrimSize = new ArrayList<>();

    private List<LimitationDoor> topLock = new ArrayList<>();
    private List<LimitationDoor> lowerLock = new ArrayList<>();
    private List<LimitationDoor> handle = new ArrayList<>();
    private List<LimitationDoor> lockCylinder = new ArrayList<>();
    private List<LimitationDoor> topInLockDecor = new ArrayList<>();
    private List<LimitationDoor> topOutLockDecor = new ArrayList<>();
    private List<LimitationDoor> lowerInLockDecor = new ArrayList<>();
    private List<LimitationDoor> lowerOutLockDecor = new ArrayList<>();
    private List<LimitationDoor> closer = new ArrayList<>();
    private List<LimitationDoor> endDoorLock = new ArrayList<>();

    private List<LimitationDoor> shieldColor = new ArrayList<>();
    private List<LimitationDoor> shieldDesign = new ArrayList<>();
    private List<LimitationDoor> shieldGlass = new ArrayList<>();

    private List<LimitationDoor> typeDoorGlass = new ArrayList<>();
    private List<LimitationDoor> toning = new ArrayList<>();
    private List<LimitationDoor> armor = new ArrayList<>();

    private List<LimitationDoor> sizeCostWidth = new ArrayList<>();
    private List<LimitationDoor> sizeCostHeight = new ArrayList<>();


    //replace with getting data from the database
    public RestrictionOfSelectionFields(int doorTypeId) {

        this.doorTypeid = doorTypeId;

        doorstep = new ArrayList<>();
        doorstep.add(new LimitationDoor("doorstep", 1, 1, 1));
        doorstep.add(new LimitationDoor("doorstep", 0, 1, 0));

        stainlessSteelDoorstep = new ArrayList<>();
        stainlessSteelDoorstep.add(new LimitationDoor("stainlessSteelDoorstep", 1, 1,
                1));
        stainlessSteelDoorstep.add(new LimitationDoor("stainlessSteelDoorstep", 0, 1,
                0));

        firstSealingLine = new ArrayList<>();
        firstSealingLine.add(new LimitationDoor("тонкий", 1, 1, 1));
        firstSealingLine.add(new LimitationDoor("тиугольный", 2, 2, 0));
        firstSealingLine.add(new LimitationDoor("магнитный", 3, 3, 0));

        secondSealingLine = new ArrayList<>();
        secondSealingLine.add(new LimitationDoor("тонкий", 1, 1, 1));
        secondSealingLine.add(new LimitationDoor("тиугольный", 2, 2, 0));
        secondSealingLine.add(new LimitationDoor("магнитный", 3, 3, 0));

        thirdSealingLine = new ArrayList<>();
        thirdSealingLine.add(new LimitationDoor("тонкий", 1, 1, 1));
        thirdSealingLine.add(new LimitationDoor("тиугольный", 2, 2, 0));
        thirdSealingLine.add(new LimitationDoor("магнитный", 3, 3, 0));

        topDoorTrim = new ArrayList<>();
        topDoorTrim.add(new LimitationDoor("да", 1, 1, 0));
        topDoorTrim.add(new LimitationDoor("нет", 0, 0, 0));

        leftDoorTrim = new ArrayList<>();
        leftDoorTrim.add(new LimitationDoor("да", 1, 1, 0));
        leftDoorTrim.add(new LimitationDoor("нет", 0, 0, 0));

        rightDoorTrim = new ArrayList<>();
        rightDoorTrim.add(new LimitationDoor("да", 1, 1, 0));
        rightDoorTrim.add(new LimitationDoor("нет", 0, 0, 0));
    }

    public void addMetal(@NonNull LimitationDoor limit) {
        this.metal.add(limit.setNuulLazyFild());
    }

    public void addMetal(@NonNull Metal metal) {
        this.metal.add(
                new LimitationDoor(String.valueOf(metal.getNameDisplayed()),
                        metal.getNameDisplayed(),
                        metal.getNameDisplayed(),
                        0));
    }

    public RestrictionOfSelectionFields widthDoor(@NonNull List<LimitationDoor> widthDoor) {
        this.setWidthDoor(widthDoor);
        return this;
    }

    public void addWidthDoor(@NonNull LimitationDoor limit) {
        this.widthDoor.add(limit.setNuulLazyFild());
    }

    public void addWidthDoorLeaf(@NonNull LimitationDoor limit) {
        this.widthDoorLeaf.add(limit.setNuulLazyFild());
    }

    public void addHeightDoorFanlight(@NonNull LimitationDoor limit) {
        this.heightDoorFanlight.add(limit.setNuulLazyFild());
    }

    public RestrictionOfSelectionFields heightDoor(@NonNull List<LimitationDoor> heightDoor) {
        this.setHeightDoor(heightDoor);
        return this;
    }

    public void addHeightDoor(@NonNull LimitationDoor limit) {
        this.heightDoor.add(limit.setNuulLazyFild());
    }

    public void addDeepnessDoor(@NonNull LimitationDoor deepnessDoor) {
        this.deepnessDoor.add(deepnessDoor.setNuulLazyFild());
    }

    public void addThicknessDoorLeaf(@NonNull LimitationDoor thicknessDoorLeaf) {
        this.thicknessDoorLeaf.add(thicknessDoorLeaf.setNuulLazyFild());
    }

    public void addDoorstep(@NonNull LimitationDoor doorstep) {
        this.doorstep.add(doorstep.setNuulLazyFild());
    }

    public void addStainlessSteelDoorstep(@NonNull LimitationDoor stainlessSteelDoorstep) {
        this.stainlessSteelDoorstep.add(stainlessSteelDoorstep.setNuulLazyFild());
    }

    public void addFirstSealingLine(@NonNull LimitationDoor lim) {
        this.firstSealingLine.add(lim.setNuulLazyFild());
    }

    public void addSecondSealingLine(@NonNull LimitationDoor lim) {
        this.secondSealingLine.add(lim.setNuulLazyFild());
    }

    public void addThirdSealingLine(@NonNull LimitationDoor lim) {

        this.thirdSealingLine.add(lim.setNuulLazyFild());
    }

    public RestrictionOfSelectionFields stuffColors(@NonNull List<ImageEntity> colors) {

        colors.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .forEach((color) -> addColors(color));
        return this;
    }

    public void addColors(@NonNull ImageEntity color) {
        if (color != null) {
            this.colors.add(LimitationDoor.builder()
                    .typeSettings(TypeOfLimitionDoor.COLOR_DOOR)
                    .itemId(color.getId())
                    .firstItem(color.getName())
                    .picturePath(color.getPicturePath())
                    .build());
        }
    }

    public void addColors(@NonNull LimitationDoor color) {

        this.colors.add(color.setNuulLazyFild());

    }

    public RestrictionOfSelectionFields stuffDesign(@NonNull List<ImageEntity> design) {

        design.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .forEach((desig) -> addDesign(desig));
        return this;
    }

    public void addDesign(@NonNull ImageEntity design) {
        if (design != null) {
            this.design.add(LimitationDoor.builder()
                    .typeSettings(TypeOfLimitionDoor.DESIGN_DOOR)
                    .itemId(design.getId())
                    .firstItem(design.getName())
                    .picturePath(design.getPicturePath())
                    .build());
        }
    }

    public void addDesign(@NonNull LimitationDoor design) {

        this.design.add(design.setNuulLazyFild());

    }

    public RestrictionOfSelectionFields stuffDoorColor(@NonNull List<ImageEntity> colors) {

        colors.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .forEach((color) -> addDoorColor(color));
        return this;
    }

    public void addDoorColor(@NonNull ImageEntity color) {
        if (color != null) {
            this.doorColor.add(LimitationDoor.builder()
                    .typeSettings(TypeOfLimitionDoor.COLOR_DOOR)
                    .itemId(color.getId())
                    .firstItem(color.getName())
                    .picturePath(color.getPicturePath())
                    .build());
        }
    }

    public RestrictionOfSelectionFields stuffDoorDesign(@NonNull List<ImageEntity> colors) {

        colors.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .forEach((color) -> addDoorDesign(color));
        return this;
    }

    public void addDoorDesign(@NonNull ImageEntity color) {
        if (color != null) {
            this.doorDesign.add(LimitationDoor.builder()
                    .typeSettings(TypeOfLimitionDoor.DESIGN_DOOR)
                    .itemId(color.getId())
                    .firstItem(color.getName())
                    .picturePath(color.getPicturePath())
                    .build());
        }
    }

    public RestrictionOfSelectionFields stuffShieldColor(@NonNull List<ImageEntity> colors) {

        colors.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .forEach((color) -> addShieldColor(color));
        return this;
    }

    public void addShieldColor(@NonNull ImageEntity color) {
        if (color != null) {
            this.shieldColor.add(LimitationDoor.builder()
                    .typeSettings(TypeOfLimitionDoor.SHIELD_COLOR)
                    .itemId(color.getId())
                    .firstItem(color.getName())
                    .secondItem(String.valueOf(color.getContainsDesign()))
                    .picturePath(color.getPicturePath())
                    .build());
        }
    }

    public void addShieldColor(@NonNull LimitationDoor color) {

        this.shieldColor.add(color.setNuulLazyFild());

    }

    public RestrictionOfSelectionFields stuffShieldDesign(@NonNull List<ImageEntity> colors) {

        colors.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .forEach((color) -> addShieldDesign(color));
        return this;
    }

    public void addShieldDesign(@NonNull ImageEntity color) {
        if (color != null) {
            this.shieldDesign.add(LimitationDoor.builder()
                    .typeSettings(TypeOfLimitionDoor.SHIELD_DESIGN)
                    .itemId(color.getId())
                    .firstItem(color.getName())
                    .picturePath(color.getPicturePath())
                    .build());
        }
    }

    public void addShieldDesign(@NonNull LimitationDoor color) {

        this.shieldDesign.add(color.setNuulLazyFild());

    }

    public void addShieldGlass(@NonNull List<ImageEntity> colorList) {
        for (ImageEntity color : colorList) {
            addShieldGlass(color);
            //LimitationDoor limColor = new LimitationDoor();
            //this.shieldGlass.add(color.setNuulLazyFild());
        }
    }

    public void addShieldGlass(@NonNull ImageEntity color) {
        if (color != null) {
            this.shieldGlass.add(LimitationDoor.builder()
                    .typeSettings(TypeOfLimitionDoor.SHIELD_GLASS)
                    .itemId(color.getId())
                    .firstItem(color.getName())
                    .picturePath(color.getPicturePath())
                    .build());
        }
    }

    public RestrictionOfSelectionFields addTopLock(@NonNull List<DoorFurniture> furnitures) {
        topLock = new ArrayList<>();

        for (DoorFurniture furniture : furnitures) {
            addTopLock(LimitationDoor.builder()
                    .typeSettings(TypeOfLimitionDoor.TOP_LOCK)
                    .itemId(furniture.getId())
                    .firstItem(furniture.getName())
                    .build());
        }
        topLock = topLock.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());
        return this;
    }

    public void addTopLock(@NonNull LimitationDoor furnitureLimit) {

        this.topLock.add(furnitureLimit.setNuulLazyFild());

    }

    public RestrictionOfSelectionFields addLowerLock(@NonNull List<DoorFurniture> furnitures) {
        lowerLock = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            addLowerLock(getLim(furniture, TypeOfLimitionDoor.LOWER_LOCK));
        }
        lowerLock = lowerLock.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());
        return this;
    }


    public void addLowerLock(@NonNull LimitationDoor furniture) {

        this.lowerLock.add(furniture.setNuulLazyFild());
    }

    public RestrictionOfSelectionFields addHendle(@NonNull List<DoorFurniture> furnitures) {
        handle = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            addHandle(getLim(furniture, TypeOfLimitionDoor.HANDLE));
        }
        handle = handle.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());
        return this;
    }

    public void addHandle(@NonNull LimitationDoor furniture) {

        this.handle.add(furniture.setNuulLazyFild());

    }

    public RestrictionOfSelectionFields addLockCylinder(@NonNull List<DoorFurniture> furnitures) {
        lockCylinder = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {

            addLockCylinder(getLim(furniture, TypeOfLimitionDoor.LOCK_CYLINDER));
        }
        lockCylinder = lockCylinder.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());
        return this;
    }

    public void addLockCylinder(@NonNull LimitationDoor furniture) {

        this.lockCylinder.add(furniture.setNuulLazyFild());

    }

    public RestrictionOfSelectionFields addCloser(@NonNull List<DoorFurniture> furnitures) {
        closer = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {

            addCloser(getLim(furniture, TypeOfLimitionDoor.CLOSER));
        }
        closer = closer.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());
        return this;
    }

    public void addTopInLockDecor(@NonNull LimitationDoor furniture) {


        this.topInLockDecor.add(furniture.setNuulLazyFild());

    }

    public void addTopOutLockDecor(@NonNull LimitationDoor furniture) {

        this.topOutLockDecor.add(furniture.setNuulLazyFild());
    }

    public void addLowerInLockDecor(@NonNull LimitationDoor furniture) {

        this.lowerInLockDecor.add(furniture.setNuulLazyFild());

    }

    public void addLowerOutLockDecor(@NonNull LimitationDoor furniture) {

        this.lowerOutLockDecor.add(furniture.setNuulLazyFild());

    }

    public void addCloser(@NonNull LimitationDoor furniture) {


        this.closer.add(furniture.setNuulLazyFild());

    }

    public void addSizeCostWidth(@NonNull LimitationDoor lim) {


        this.sizeCostWidth.add(lim.setNuulLazyFild());

    }

    public void addSizeCostHeight(@NonNull LimitationDoor lim) {


        this.sizeCostHeight.add(lim.setNuulLazyFild());

    }

    public void addEndDoorLock(@NonNull LimitationDoor furniture) {

        this.endDoorLock.add(furniture.setNuulLazyFild());

    }

    public void addTypeDoorGlass(@NonNull LimitationDoor furniture) {

        this.typeDoorGlass.add(furniture.setNuulLazyFild());

    }

    public void addToning(@NonNull LimitationDoor furniture) {

        this.toning.add(furniture.setNuulLazyFild());

    }

    public void addArmor(@NonNull LimitationDoor furniture) {

        this.armor.add(furniture.setNuulLazyFild());

    }

    public RestrictionOfSelectionFields addEndDoorLock(@NonNull List<DoorFurniture> furnitures) {
        endDoorLock = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {

            addEndDoorLock(getLim(furniture, TypeOfLimitionDoor.END_DOOR_LOCK));
        }
        endDoorLock = endDoorLock.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());
        return this;
    }

    public RestrictionOfSelectionFields addGlass(@NonNull List<DoorFurniture> furnitures) {
        typeDoorGlass = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {

            addTypeDoorGlass(getLim(furniture, TypeOfLimitionDoor.TYPE_GLASS));
        }
        typeDoorGlass =  typeDoorGlass.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());
        return this;
    }

    public RestrictionOfSelectionFields addToning(@NonNull List<DoorFurniture> furnitures) {
        toning = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {

            addToning(getLim(furniture, TypeOfLimitionDoor.TONING));
        }
        toning =  toning.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());
        return this;
    }

    public RestrictionOfSelectionFields addArmor(@NonNull List<DoorFurniture> furnitures) {
        armor = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {

            addArmor(getLim(furniture, TypeOfLimitionDoor.ARMOR));
        }
        armor =  armor.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());
        return this;
    }

    public RestrictionOfSelectionFields addTopInLockDecor(@NonNull List<DoorFurniture> furnitures) {
        topInLockDecor = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {

            addTopInLockDecor(getLim(furniture, TypeOfLimitionDoor.TOP_IN_LOCK_DECOR));
        }
        topInLockDecor =  topInLockDecor.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());
        return this;
    }

    public RestrictionOfSelectionFields addTopOutLockDecor(@NonNull List<DoorFurniture> furnitures) {
        topOutLockDecor = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            addTopOutLockDecor(getLim(furniture, TypeOfLimitionDoor.TOP_OUT_LOCK_DECOR));
        }
        topOutLockDecor =  topOutLockDecor.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());
        return this;
    }

    public RestrictionOfSelectionFields addLowerInLockDecor(@NonNull List<DoorFurniture> furnitures) {
        lowerInLockDecor = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {

            addLowerInLockDecor(getLim(furniture, TypeOfLimitionDoor.LOWER_IN_LOCK_DECOR));
        }
        lowerInLockDecor =  lowerInLockDecor.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());
        return this;
    }

    public RestrictionOfSelectionFields addLowerOutLockDecor(@NonNull List<DoorFurniture> furnitures) {
        lowerOutLockDecor = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {

            addLowerOutLockDecor(getLim(furniture, TypeOfLimitionDoor.LOWER_OUT_LOCK_DECOR));
        }
        lowerOutLockDecor =  lowerOutLockDecor.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());
        return this;
    }

    public RestrictionOfSelectionFields stuffMetal(@NonNull List<Metal> metals) {

        metals.stream().forEach((metal) -> this.addMetal(metal));

        return this;

    }

    public void addTopDoorTrim(@NonNull LimitationDoor lim) {
        topDoorTrim.add(lim.setNuulLazyFild());
    }

    public void addLeftDoorTrim(@NonNull LimitationDoor lim) {
        leftDoorTrim.add(lim.setNuulLazyFild());
    }

    public void addRightDoorTrim(@NonNull LimitationDoor lim) {
        rightDoorTrim.add(lim.setNuulLazyFild());
    }

    public void addTopDoorTrimSize(@NonNull LimitationDoor lim) {
        topDoorTrimSize.add(lim.setNuulLazyFild());
    }

    public void addLeftDoorTrimSize(@NonNull LimitationDoor lim) {
        leftDoorTrimSize.add(lim.setNuulLazyFild());
    }

    public void addRightDoorTrimSize(@NonNull LimitationDoor lim) {
        rightDoorTrimSize.add(lim.setNuulLazyFild());
    }


    public LimitationDoor getLim(@NonNull DoorFurniture furniture, @NonNull TypeOfLimitionDoor typeOfLimitionDoor) {
        return LimitationDoor.builder()
                .typeSettings(typeOfLimitionDoor)
                .itemId(furniture.getId())
                .firstItem(furniture.getName())
                .build();
    }

}
