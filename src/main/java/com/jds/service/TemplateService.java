package com.jds.service;

import com.jds.dao.entity.DoorType;
import com.jds.dao.entity.ImageEntity;
import com.jds.dao.entity.LimitationDoor;
import com.jds.dao.repository.*;
import com.jds.model.DoorTemplate;
import com.jds.model.RestrictionOfSelectionFields;
import com.jds.model.ShortTemplate;
import com.jds.model.modelEnum.TypeOfFurniture;
import com.jds.model.modelEnum.TypeOfLimitionDoor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplateService {
    @Autowired
    private TemplateRepository dAO;
    @Autowired
    private MainDAO mainDAO;
    @Autowired
    private MetalRepository metalDao;
    @Autowired
    private ColorRepository colorDao;
    @Autowired
    private FurnitureRepository furnitureDao;

    public List<ShortTemplate> getTemplateList() {

        List<DoorType> list = mainDAO.getDoorTypeListFromLimitTable();
        return list.stream()
                .map(type -> new ShortTemplate(type))
                .collect(Collectors.toList());
    }

    public DoorTemplate getDoorTemplate(@NonNull String doorTypeId) {

        return DoorTemplate.builder()
                .template(getTemplateFromLimits(doorTypeId))
                .restriction(getRestrictionOfSelectionFields(doorTypeId))
                .build();
    }

    public void saveDoorTemplate(@NonNull RestrictionOfSelectionFields restriction) {

        DoorType doorType = mainDAO.getDoorType(restriction.getDoorTypeid());

        List<LimitationDoor> limitList = mainDAO.getLimitationDoor(doorType.getId());

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.METAL_THICKNESS, restriction.getMetal(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.WIDTH, restriction.getWidthDoor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.HEIGHT, restriction.getHeightDoor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.DEPTH, restriction.getDeepnessDoor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LEAF_THICKNESS, restriction.getThicknessDoorLeaf(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.WIDTH_ACTIVE_LEAF, restriction.getWidthDoorLeaf(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.HEIGHT_FANLIGHT, restriction.getHeightDoorFanlight(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.COLOR_DOOR, restriction.getColors(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.DESIGN_DOOR, restriction.getDesign(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.SHIELD_COLOR, restriction.getShieldColor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.SHIELD_DESIGN, restriction.getShieldDesign(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.DOORSTEP, restriction.getDoorstep(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.STAINLESS_STEEL_DOORSTEP, restriction.getStainlessSteelDoorstep(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.FIRST_SEALING_LINE, restriction.getFirstSealingLine(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.SECOND_SEALING_LINE, restriction.getSecondSealingLine(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.THIRD_SEALING_LINE, restriction.getThirdSealingLine(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TOP_DOOR_TRIM, restriction.getTopDoorTrim(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LEFT_DOOR_TRIM, restriction.getLeftDoorTrim(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.RIGHT_DOOR_TRIM, restriction.getRightDoorTrim(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TOP_DOOR_TRIM_SIZE, restriction.getTopDoorTrimSize(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LEFT_DOOR_TRIM_SIZE, restriction.getLeftDoorTrimSize(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.RIGHT_DOOR_TRIM_SIZE, restriction.getRightDoorTrimSize(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TOP_LOCK, restriction.getTopLock(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LOWER_LOCK, restriction.getLowerLock(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.HANDLE, restriction.getHandle(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LOCK_CYLINDER, restriction.getLockCylinder(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TOP_IN_LOCK_DECOR, restriction.getTopInLockDecor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TOP_OUT_LOCK_DECOR, restriction.getTopOutLockDecor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LOWER_IN_LOCK_DECOR, restriction.getLowerInLockDecor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LOWER_OUT_LOCK_DECOR, restriction.getLowerOutLockDecor(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.CLOSER, restriction.getCloser(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.END_DOOR_LOCK, restriction.getEndDoorLock(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TYPE_GLASS, restriction.getTypeDoorGlass(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TONING, restriction.getToning(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.ARMOR, restriction.getArmor(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.SIZE_COST_HEIGHT, restriction.getSizeCostHeight(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.SIZE_COST_WIDTH, restriction.getSizeCostWidth(), limitList);

        deleteAllDoorTemplate(limitList);

    }

    public RestrictionOfSelectionFields getTemplateFromLimits(@NonNull String doorTypeId) {

        int intDoorTypeId = Integer.parseInt(doorTypeId);

        List<LimitationDoor> limitList = mainDAO.getLimitationDoor(intDoorTypeId);
        List<ImageEntity> colorList = colorDao.getShieldGlass();
        RestrictionOfSelectionFields restriction = new RestrictionOfSelectionFields();
        restriction.setDoorTypeid(intDoorTypeId);

        limitList.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .forEach((lim) -> RestrictionBuild(restriction, lim));
        restriction.addShieldGlass(colorList);
        return restriction;
    }

    public RestrictionOfSelectionFields getRestrictionOfSelectionFields(@NonNull String idDoorType) {

        int idType = Integer.parseInt(idDoorType);

        return new RestrictionOfSelectionFields(idType)

                .widthDoor(generateSizeDoor(TypeOfLimitionDoor.HEIGHT))
                .heightDoor(generateSizeDoor(TypeOfLimitionDoor.WIDTH))

                .stuffMetal(metalDao.getMetals())

                .stuffColors(colorDao.getDoorColors())
                .stuffDesign(colorDao.getDoorDesign())
                .stuffDoorColor(colorDao.getDoorColors())
                .stuffDoorDesign(colorDao.getDoorDesign())
                .stuffShieldColor(colorDao.getShieldColor())
                .stuffShieldDesign(colorDao.getShieldDesign())

                .addTopLock(furnitureDao.getFurniture(TypeOfFurniture.TOP_LOCK))
                .addLowerLock(furnitureDao.getFurniture(TypeOfFurniture.LOWER_LOCK))
                .addHendle(furnitureDao.getFurniture(TypeOfFurniture.HANDLE))
                .addLockCylinder(furnitureDao.getFurniture(TypeOfFurniture.LOCK_CYLINDER))
                .addCloser(furnitureDao.getFurniture(TypeOfFurniture.CLOSER))
                .addEndDoorLock(furnitureDao.getFurniture(TypeOfFurniture.END_DOOR_LOCK))

                .addGlass(furnitureDao.getFurniture(TypeOfFurniture.TYPE_GLASS))
                .addToning(furnitureDao.getFurniture(TypeOfFurniture.GLASS_PELLICLE))
                .addArmor(furnitureDao.getFurniture(TypeOfFurniture.ARMOR_GLASS_PELLICLE))

                .addTopInLockDecor(furnitureDao.getFurniture(TypeOfFurniture.TOP_IN_LOCK_DECOR))
                .addTopOutLockDecor(furnitureDao.getFurniture(TypeOfFurniture.TOP_OUT_LOCK_DECOR))
                .addLowerInLockDecor(furnitureDao.getFurniture(TypeOfFurniture.LOWER_IN_LOCK_DECOR))
                .addLowerOutLockDecor(furnitureDao.getFurniture(TypeOfFurniture.LOWER_OUT_LOCK_DECOR));
    }

    public void saveAsLimitationDoor(@NonNull DoorType doorType, @NonNull TypeOfLimitionDoor type,
                                     @NonNull List<LimitationDoor> newlimList, List<LimitationDoor> oldLimitList) {


        newlimList.stream().peek((lim) -> lim.setFildForSaveToDataBase(doorType, type, oldLimitList))
                .forEach((lim) -> mainDAO.saveOrUpdateLimitationDoor(lim));

    }

    public void deleteAllDoorTemplate(@NonNull List<LimitationDoor> limitList) {


        limitList.stream().forEach((limit) -> mainDAO.deleteLimit(limit));

    }

    public void RestrictionBuild(@NonNull RestrictionOfSelectionFields restriction, @NonNull LimitationDoor lim) {

        if (TypeOfLimitionDoor.METAL_THICKNESS == lim.getTypeSettings()) {
            restriction.addMetal(lim);

        } else if (TypeOfLimitionDoor.WIDTH == lim.getTypeSettings()) {
            restriction.addWidthDoor(lim);
        } else if (TypeOfLimitionDoor.HEIGHT == lim.getTypeSettings()) {
            restriction.addHeightDoor(lim);

        } else if (TypeOfLimitionDoor.WIDTH_ACTIVE_LEAF == lim.getTypeSettings()) {
            restriction.addWidthDoorLeaf(lim);
        } else if (TypeOfLimitionDoor.HEIGHT_FANLIGHT == lim.getTypeSettings()) {
            restriction.addHeightDoorFanlight(lim);

        } else if (TypeOfLimitionDoor.DEPTH == lim.getTypeSettings()) {
            restriction.addDeepnessDoor(lim);
        } else if (TypeOfLimitionDoor.LEAF_THICKNESS == lim.getTypeSettings()) {
            restriction.addThicknessDoorLeaf(lim);

        } else if (TypeOfLimitionDoor.DOORSTEP == lim.getTypeSettings()) {
            restriction.addDoorstep(lim);
        } else if (TypeOfLimitionDoor.STAINLESS_STEEL_DOORSTEP == lim.getTypeSettings()) {
            restriction.addStainlessSteelDoorstep(lim);

        } else if (TypeOfLimitionDoor.FIRST_SEALING_LINE == lim.getTypeSettings()) {
            restriction.addFirstSealingLine(lim);
        } else if (TypeOfLimitionDoor.SECOND_SEALING_LINE == lim.getTypeSettings()) {
            restriction.addSecondSealingLine(lim);
        } else if (TypeOfLimitionDoor.THIRD_SEALING_LINE == lim.getTypeSettings()) {
            restriction.addThirdSealingLine(lim);

        } else if (TypeOfLimitionDoor.TOP_DOOR_TRIM == lim.getTypeSettings()) {
            restriction.addTopDoorTrim(lim);
        } else if (TypeOfLimitionDoor.LEFT_DOOR_TRIM == lim.getTypeSettings()) {
            restriction.addLeftDoorTrim(lim);
        } else if (TypeOfLimitionDoor.RIGHT_DOOR_TRIM == lim.getTypeSettings()) {
            restriction.addRightDoorTrim(lim);

        } else if (TypeOfLimitionDoor.TOP_DOOR_TRIM_SIZE == lim.getTypeSettings()) {
            restriction.addTopDoorTrimSize(lim);
        } else if (TypeOfLimitionDoor.LEFT_DOOR_TRIM_SIZE == lim.getTypeSettings()) {
            restriction.addLeftDoorTrimSize(lim);
        } else if (TypeOfLimitionDoor.RIGHT_DOOR_TRIM_SIZE == lim.getTypeSettings()) {
            restriction.addRightDoorTrimSize(lim);

        } else if (TypeOfLimitionDoor.COLOR_DOOR == lim.getTypeSettings()) {
            restriction.addColors(lim);
        } else if (TypeOfLimitionDoor.DESIGN_DOOR == lim.getTypeSettings()) {
            restriction.addDesign(lim);
        } else if (TypeOfLimitionDoor.SHIELD_COLOR == lim.getTypeSettings()) {
            restriction.addShieldColor(lim);
        } else if (TypeOfLimitionDoor.SHIELD_DESIGN == lim.getTypeSettings()) {
            restriction.addShieldDesign(lim);

        } else if (TypeOfLimitionDoor.TOP_LOCK == lim.getTypeSettings()) {
            restriction.addTopLock(lim);
        } else if (TypeOfLimitionDoor.LOWER_LOCK == lim.getTypeSettings()) {
            restriction.addLowerLock(lim);
        } else if (TypeOfLimitionDoor.HANDLE == lim.getTypeSettings()) {
            restriction.addHandle(lim);
        } else if (TypeOfLimitionDoor.LOCK_CYLINDER == lim.getTypeSettings()) {
            restriction.addLockCylinder(lim);

        } else if (TypeOfLimitionDoor.TOP_IN_LOCK_DECOR == lim.getTypeSettings()) {
            restriction.addTopInLockDecor(lim);
        } else if (TypeOfLimitionDoor.TOP_OUT_LOCK_DECOR == lim.getTypeSettings()) {
            restriction.addTopOutLockDecor(lim);
        } else if (TypeOfLimitionDoor.LOWER_IN_LOCK_DECOR == lim.getTypeSettings()) {
            restriction.addLowerInLockDecor(lim);
        } else if (TypeOfLimitionDoor.LOWER_OUT_LOCK_DECOR == lim.getTypeSettings()) {
            restriction.addLowerOutLockDecor(lim);

        } else if (TypeOfLimitionDoor.CLOSER == lim.getTypeSettings()) {
            restriction.addCloser(lim);
        } else if (TypeOfLimitionDoor.END_DOOR_LOCK == lim.getTypeSettings()) {
            restriction.addEndDoorLock(lim);
        } else if (TypeOfLimitionDoor.TYPE_GLASS == lim.getTypeSettings()) {
            restriction.addTypeDoorGlass(lim);
        } else if (TypeOfLimitionDoor.TONING == lim.getTypeSettings()) {
            restriction.addToning(lim);
        } else if (TypeOfLimitionDoor.ARMOR == lim.getTypeSettings()) {
            restriction.addArmor(lim);

        } else if (TypeOfLimitionDoor.SIZE_COST_HEIGHT == lim.getTypeSettings()) {
            restriction.addSizeCostHeight(lim);
        } else if (TypeOfLimitionDoor.SIZE_COST_WIDTH == lim.getTypeSettings()) {
            restriction.addSizeCostWidth(lim);
        }
    }

    public List<LimitationDoor> generateSizeDoor(@NonNull TypeOfLimitionDoor typeSettings) {
        List<LimitationDoor> size = new ArrayList<>();

        LimitationDoor limit = new LimitationDoor();
        limit.setTypeSettings(typeSettings);
        limit.setStartRestriction(980);
        limit.setPairOfValues(0);
        size.add(limit);

        limit = new LimitationDoor();
        limit.setTypeSettings(typeSettings);
        limit.setStartRestriction(860);
        limit.setPairOfValues(0);
        size.add(limit);

        limit = new LimitationDoor();
        limit.setTypeSettings(typeSettings);
        limit.setStartRestriction(500);
        limit.setStopRestriction(1050);
        limit.setPairOfValues(1);
        size.add(limit);

        return size;
    }

    public static LimitationDoor getDefaultLine(List<LimitationDoor> list){
        return list.stream()
                .filter(line -> line.getDefaultValue() == 1)
                .findFirst().orElse(new LimitationDoor());
    }

    public static LimitationDoor getLineByItemId(List<LimitationDoor> list,int id){
        return list.stream()
                .filter(line -> line.getItemId() == id)
                .findFirst().orElse(new LimitationDoor());
    }

}
