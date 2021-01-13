package com.jds.service;

import com.jds.dao.entity.*;
import com.jds.dao.repository.*;
import com.jds.model.*;
import com.jds.model.cutting.DoorPart;
import com.jds.model.cutting.Sheet;
import com.jds.model.cutting.SheetCutting;
import com.jds.model.modelEnum.OrderStatus;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoorService implements DoorServ {

    @Autowired
    private MainDAO dAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private MaterialsRepository materialsDAO;

    @Autowired
    private OrderService orderService;
    @Autowired
    private FurnitureService furnitureService;
    @Autowired
    private UserService userService;
    @Autowired
    private MetalRepository metalDao;
    @Autowired
    private ColorRepository colorDao;
    @Autowired
    private TemplateService templateService;
    @Autowired
    OrderDiscountService orderDiscountService;

    @Override
    public DoorEntity calculateTheDoor(@NonNull DoorEntity door) {

        if (door.getDoorType().getPriceList() == 1) {

            return recalculateTheDoorByPriceList(door,
                    userService.getCurrentUser().getDiscount(),
                    userService.getUserSetting().getRetailMargin());

        } else {
            return recalculateTheDoorCompletely(door);
        }

    }

    @Override
    public DoorEntity getDoor(@NonNull int id, @NonNull int orderId, @NonNull int typid) {

        DoorEntity door;

        if (id > 0) {
            door = dAO.getDoor(id);
            door = allowEditing(door, orderId);
        } else if (typid > 0) {
            door = createNewDoorByTemplate(typid, id);
        } else {
            door = createNewDoorWithAvailableDoorClass();
        }

        if (orderId != 0 && door.getId() == 0) {
            door.setOrderHolder(orderId);
        }

        return door.clearNonSerializingFields();
    }

    private DoorEntity allowEditing(DoorEntity door, int orderId) {
        DoorsОrder doorsОrder = orderService.getOrder(orderId);
        if (doorsОrder.getStatus() == OrderStatus.CALC) {
            door.setTemplate(templateService.getTemplateFromLimits(String.valueOf(door.getDoorType().getId())));
        }
        return door;
    }

    public DoorEntity recalculateTheDoorByPriceList(@NonNull DoorEntity doorEntity,
                                                    @NonNull int discount,
                                                    @NonNull int retailMargin) {

        doorEntity.setCostList(new CostList());

        doorEntity = addPriceToCostList(doorEntity, discount);

        doorEntity = costOfChangesAtTemplate(doorEntity);

        doorEntity
                .calculateGlass()
                .calculateFurniture();

        doorEntity = addRetailMarginToCostList(doorEntity, retailMargin);

        doorEntity
                .setPriceOfDoorType(userService.getCurrentUser())
                .createName();

        return addToOrderIfNotExist(doorEntity);
    }

    private DoorEntity addRetailMarginToCostList(DoorEntity doorEntity, int retailMargin) {

        int discountPrice = doorEntity.getCostList().getTotalCost();
        int priceWithMarkup = (int) ((discountPrice * retailMargin) / 100);

        doorEntity.getCostList().addLine("PriceWithMarkup",
                500,
                false,
                (int) priceWithMarkup);

        return doorEntity;
    }

    private DoorEntity addPriceToCostList(DoorEntity doorEntity, int discount) {

        int retailPrice = (int) doorEntity.getDoorType().getRetailPrice();

        doorEntity.getCostList().addLine("RetailPrice",
                100,
                false,
                (int) retailPrice);

        int discountCost = (int) ((retailPrice * discount) / 100);

        doorEntity.getCostList().addLine("Discount",
                100,
                false,
                (int) -discountCost);

        return doorEntity;
    }

    private DoorEntity costOfChangesAtTemplate(DoorEntity doorEntity) {

        addCostResizing(doorEntity);
        addCostForColorChange(doorEntity);
        addCostForShieldKitChange(doorEntity);
        addCostForFurnitureKitChange(doorEntity);

        return doorEntity;
    }

    private DoorEntity addCostForFurnitureKitChange(@NonNull DoorEntity doorEntity) {
        FurnitureKit kit = doorEntity.getFurnitureKit();

        addCostForFurniture(kit.getTopLock(), doorEntity.getTemplate().getTopLock(), doorEntity);
        addCostForFurniture(kit.getLowerLock(), doorEntity.getTemplate().getLowerLock(), doorEntity);
        //...

        return doorEntity;

    }

    private DoorEntity addCostForFurniture(DoorFurniture furniture,
                                           List<LimitationDoor> listOfFurniturLimit,
                                           @NonNull DoorEntity doorEntity) {

        if (furniture != null) {

            LimitationDoor defaultShieldColor = TemplateService.getDefaultLine(
                    listOfFurniturLimit
            );

            if (defaultShieldColor.getItemId() != furniture.getId()) {

                LimitationDoor currentColorLim = TemplateService.getLineByItemId(
                        listOfFurniturLimit, furniture.getId()
                );

                doorEntity.getCostList().addLine(
                        LimitationDoor.getDescription(currentColorLim),
                        200,
                        false,
                        currentColorLim.getCost());
            }
        }

        return doorEntity;
    }

    private DoorEntity addCostForShieldKitChange(@NonNull DoorEntity doorEntity) {

        ShieldKit kit = doorEntity.getShieldKit();

        ImageEntity shieldColor = kit.getShieldColor();
        if (shieldColor != null) {

            LimitationDoor defaultShieldColor = TemplateService.getDefaultLine(
                    doorEntity.getTemplate().getShieldColor()
            );

            if (defaultShieldColor.getItemId() != shieldColor.getId()) {

                LimitationDoor currentColorLim = TemplateService.getLineByItemId(
                        doorEntity.getTemplate().getShieldColor(), shieldColor.getId()
                );

                doorEntity.getCostList().addLine(
                        LimitationDoor.getDescription(currentColorLim),
                        200,
                        false,
                        currentColorLim.getCost());
            }
        }

        ImageEntity shieldDesign = kit.getShieldDesign();
        if (shieldDesign != null) {

            LimitationDoor defaultShieldDesign = TemplateService.getDefaultLine(
                    doorEntity.getTemplate().getShieldDesign()
            );

            if (defaultShieldDesign.getItemId() != shieldDesign.getId()) {

                LimitationDoor currentDesignLim = TemplateService.getLineByItemId(
                        doorEntity.getTemplate().getShieldDesign(), shieldDesign.getId()
                );

                doorEntity.getCostList().addLine(
                        LimitationDoor.getDescription(currentDesignLim),
                        200,
                        false,
                        currentDesignLim.getCost());
            }
        }

        return doorEntity;
    }

    private DoorEntity addCostForColorChange(@NonNull DoorEntity doorEntity) {

        LimitationDoor defaultColor = TemplateService.getDefaultLine(doorEntity.getTemplate().getColors());

        if (!defaultColor.getFirstItem().equals(doorEntity.getDoorColor())) {
            LimitationDoor currentColorLim = doorEntity.getTemplate().getColors().stream()
                    .filter(line -> line.getFirstItem().equals(doorEntity.getDoorColor()))
                    .findFirst()
                    .orElse(new LimitationDoor());

            doorEntity.getCostList().addLine(
                    LimitationDoor.getDescription(currentColorLim),
                    200,
                    false,
                    currentColorLim.getCost());
        }

        return doorEntity;
    }

    private DoorEntity addCostResizing(@NonNull DoorEntity doorEntity) {

        int WidthSize = doorEntity.getWidthDoor();
        int defaultWidth = doorEntity.getTemplate().getWidthDoor().stream().findFirst().orElse(new LimitationDoor()).getDefaultValue();
        List<LineCostList> listWidth = new ArrayList<>();

        if (WidthSize != defaultWidth) {
            List<LimitationDoor> sizeCostWidth = doorEntity.getTemplate().getSizeCostWidth();
            listWidth = sizeCostWidth.stream()
                    .map((lim) -> toMarkup(lim, WidthSize, defaultWidth, false))
                    .filter(line -> line.getCost() > 0)
                    .collect(Collectors.toList());
        }

        List<LineCostList> listHeight = new ArrayList<>();
        int Heightsize = doorEntity.getHeightDoor();
        int defaultHeight = doorEntity.getTemplate().getHeightDoor().stream().findFirst().orElse(new LimitationDoor()).getDefaultValue();

        if (Heightsize != defaultHeight) {
            List<LimitationDoor> sizeCostHeight = doorEntity.getTemplate().getSizeCostHeight();
            final boolean ALREADY_COUNTED = !listWidth.isEmpty();
            listHeight = sizeCostHeight.stream()
                    .map((lim) -> toMarkup(lim, Heightsize, defaultHeight, ALREADY_COUNTED))
                    .filter(line -> line.getCost() > 0)
                    .collect(Collectors.toList());
        }

        doorEntity.getCostList().addAllLine(listWidth);
        doorEntity.getCostList().addAllLine(listHeight);

        return doorEntity;
    }

    private LineCostList toMarkup(LimitationDoor lim, int size, int defaultSize, boolean alreadyCounted) {


        int costStep = (int) lim.getStartRestriction();
        int step = (int) lim.getStep();
        int costForChange = alreadyCounted ? 0 : lim.getCost();

        if (costStep > 0 && step > 0 && (costForChange > 0 || alreadyCounted)) {

            int costDiff = ((size - defaultSize) / step) * costStep;

            return new LineCostList(lim.getTypeSettings().toString() + " " + size +
                    ", costForChange: " + costForChange +
                    ", costDiff: " + costDiff,
                    200,
                    false,
                    costForChange + costDiff);
        }

        return new LineCostList();
    }

    public DoorEntity recalculateTheDoorCompletely(DoorEntity door) {


        PayrollSettings paySettings = new PayrollSettings();
        paySettings.setBendSetting(dAO.getbendSettingId(door.getDoorType().getId(), door.getMetal(), door.getSealingLine()));
        paySettings.setConstMap(dAO.getSalaryConstantsMap());
        paySettings.setDoorColors(colorDao.getDoorColorByName(door.getDoorColor()));
        paySettings.setDoorType(dAO.getDoorType(door.getDoorType().getId()));
        paySettings.setSalarySetting(dAO.getSalarySetting(door.getMetal()));

        List<SpecificationSetting> speciSettingList = materialsDAO.getSpecificationSetting(door.getMetal(), door.getDoorType().getId());

        //new instance cost
        door.setCostList(new CostList());

        door = calculateMetalDoor(door)
                .calculateColorDoor(paySettings.getDoorColors())
                .calculateSalary(paySettings)
                .calculateFurniture()
                .calculateGlass()
                .calculateMaterials(speciSettingList)
                .costToPrice()
                .createName();

        return door;
    }

    private DoorEntity calculateMetalDoor(DoorEntity door) {

        List<DoorPart> partList = getDoorPart(door);
        Sheet sheet = new Sheet(2500, 1250);

        SheetCutting sheetCutting = new SheetCutting(partList, sheet);
        sheetCutting.CompleteCutting();
        sheetCutting.clearHardCalculationData();

        Metal metal = metalDao.getMetal(door.getMetal());

        door.setSheets(sheetCutting.getSheets());
        door.calculateWeigh(metal);
        door.calculateCostMetal(metal);

        return door;
    }

    public List<DoorPart> getDoorPart(DoorEntity door) {

        return DoorPart.getDoopPartsList(dAO.getSizeOfDoorPartsList(door.getDoorType().getId()), door);

    }


    public DoorEntity createNewDoorWithAvailableDoorClass() {

        DoorEntity door = new DoorEntity();
        door.addAvailableDoorClass(dAO.getAvailableDoorClass());

        return door;
    }

    public DoorEntity createNewDoorByTemplate(@NonNull int typeId, @NonNull int id) {


        DoorType doorType = dAO.getDoorType(typeId);

        DoorEntity doorEntity = new DoorEntity();
        doorEntity.setId(id);
        doorEntity.addAvailableDoorClass(dAO.getAvailableDoorClass());

        doorEntity.setDoorType(doorType);

        RestrictionOfSelectionFields template = templateService.getTemplateFromLimits(String.valueOf(typeId));
        doorEntity.setTemplate(template);

        doorEntity.setMetal(findInTemplateRestriction(template.getMetal()));
        doorEntity.setWidthDoor(findInTemplateSize(template.getWidthDoor()));
        doorEntity.setHeightDoor(findInTemplateSize(template.getHeightDoor()));
        doorEntity.setDoorLeaf(doorType.getDoorLeaf());
        doorEntity.setActiveDoorLeafWidth(findInTemplateSize(template.getWidthDoorLeaf()));
        //doorEntity.setDoorFanlightHeight
        doorEntity.setDeepnessDoor((int) findInTemplateRestriction(template.getDeepnessDoor()));
        doorEntity.setThicknessDoorLeaf((int) findInTemplateRestriction(template.getThicknessDoorLeaf()));
        //sideDoorOpen
        //innerDoorOpen;

        doorEntity.setDoorstep((int) findInTemplateRestriction(template.getDoorstep()));
        doorEntity.setStainlessSteelDoorstep((int) findInTemplateRestriction(template.getStainlessSteelDoorstep()));

        doorEntity.setTopDoorTrim((int) findInTemplateRestriction(template.getTopDoorTrim()));
        doorEntity.setLeftDoorTrim((int) findInTemplateRestriction(template.getLeftDoorTrim()));
        doorEntity.setRightDoorTrim((int) findInTemplateRestriction(template.getRightDoorTrim()));

        doorEntity.setTopDoorTrimSize(findInTemplateSize(template.getTopDoorTrimSize()));
        doorEntity.setLeftDoorTrimSize(findInTemplateSize(template.getLeftDoorTrimSize()));
        doorEntity.setRightDoorTrimSize(findInTemplateSize(template.getRightDoorTrimSize()));

        //sealingLine
        doorEntity.setFirstSealingLine((int) findInTemplateRestriction(template.getFirstSealingLine()));
        doorEntity.setSecondSealingLine((int) findInTemplateRestriction(template.getSecondSealingLine()));
        doorEntity.setThirdSealingLine((int) findInTemplateRestriction(template.getThirdSealingLine()));
        //price
        //discountPrice
        //priceWithMarkup
        doorEntity.setDoorColor(findInTemplateColor(template.getColors()));
        doorEntity.setDoorDesign(DoorDesign.instanceDesign(template.getColors(), template.getDesign(), colorDao));

        AvailableFieldsForSelection availableFields = AvailableFieldsForSelection.builder()
                .topLock(defaultAndConvertToFurniture(template.getTopLock()))
                .lowerLock(defaultAndConvertToFurniture(template.getLowerLock()))
                .lockCylinder(defaultAndConvertToFurniture(template.getLowerLock()))
                .handle(defaultAndConvertToFurniture(template.getHandle()))
                .shieldColor(defaultAndConvertToImage(template.getShieldColor()))
                .shieldDesign(defaultAndConvertToImage(template.getShieldDesign()))
                .shieldGlass(defaultAndConvertToImage(template.getShieldGlass()))
                .build();
        doorEntity.setFurnitureKit(FurnitureKit.instanceKit(availableFields));
        doorEntity.setShieldKit(ShieldKit.instanceKit(availableFields));

        /*
        isDoorGlass
        doorGlass

        additionallyHingeMain
        additionallyHingeNotMain
        amplifierCloser
       */

        return doorEntity;

    }

    private List<DoorFurniture> defaultAndConvertToFurniture(List<LimitationDoor> listLim) {
        List<LimitationDoor> defList = listLim.stream()
                .filter(lim -> lim.isDefault())
                .collect(Collectors.toList());
        return furnitureService.convertToFurniture(defList);
    }

    private List<ImageEntity> defaultAndConvertToImage(List<LimitationDoor> listLim) {
        List<LimitationDoor> defList = listLim.stream()
                .filter(lim -> lim.isDefault())
                .collect(Collectors.toList());
        return furnitureService.convertToImage(defList);
    }

    public double findInTemplateRestriction(@NonNull List<LimitationDoor> listLim) {

        List<LimitationDoor> defList = listLim.stream()
                .filter(lim -> lim.isDefault())
                .collect(Collectors.toList());

        if (defList.size() == 1) {
            return defList.get(0).getStartRestriction();
        }

        return 0;
    }

    public String findInTemplateColor(@NonNull List<LimitationDoor> listLim) {

        List<LimitationDoor> defList = listLim.stream()
                .filter(lim -> lim.isDefault())
                .collect(Collectors.toList());

        if (defList.size() == 1) {
            return defList.get(0).getFirstItem();
        }

        return "";
    }

    public int findInTemplateSize(@NonNull List<LimitationDoor> listLim) {

        List<LimitationDoor> defList = listLim.stream()
                .filter(lim -> lim.isDefault())
                .collect(Collectors.toList());

        if (defList.size() == 1) {
            if (defList.get(0).getPairOfValues() == 1) {
                return defList.get(0).getDefaultValue();
            } else {
                return (int) defList.get(0).getStartRestriction();
            }
        }

        return 0;
    }

    @Override
    public DoorEntity saveDoor(@NonNull DoorEntity door) {

        door.createName();
        door.clearEmptyLinks();
        door = dAO.saveDoor(door);
        return addToOrderIfNotExist(door);

    }

    @Override
    public DoorsОrder deleteDoorFromOrder(@NonNull String id, @NonNull String orderId) {

        if (!orderId.isEmpty() && !orderId.equals("0") && !id.isEmpty() && !id.equals("0")) {
            DoorsОrder order = orderDAO.getOrder(Integer.parseInt(orderId));
            int mess = order.deleteDoor(Integer.parseInt(id));
            if (mess == 1) {
                orderDAO.saveOrder(order.calculateTotal(userService.getUserSetting(), orderDiscountService.getOrderDiscounts(orderId)));
                return orderService.clearNonSerializingFields(order);
            }

        }

        return null;
    }

    public DoorEntity addToOrderIfNotExist(@NonNull DoorEntity door) {

        if (door.getOrderHolder() == 0) {
            return door;
        }

        DoorsОrder order = orderDAO.getOrder(door.getOrderHolder());
        DoorEntity fineDoor = order.getDoors().stream()
                .filter(doorItem -> doorItem.getId() == door.getId())
                .findFirst()
                .orElse(null);

        if (fineDoor != null) {
            return door;
        }

        order.addDoor(door);
        order.calculateTotal(userService.getUserSetting(), orderDiscountService.getOrderDiscounts(String.valueOf(order.getOrder_id())));
        orderDAO.saveOrder(order);
        return door;

    }

    @Override
    public List<LineSpecification> getSpecificationByDoorId(@NonNull String doorId) {

        DoorEntity doorEntity = dAO.getDoor(Integer.parseInt(doorId));

        List<LineSpecification> lineSpec = materialsDAO.getSpecification(doorEntity.getDoorType().getId());

        lineSpec.stream()
                .peek((lin) -> addFurKitToLineSpec(lineSpec, doorEntity))
                .forEach((lin) -> lin.getDoorType().clearNonSerializingFields());

        return lineSpec;

    }

    public List<LineSpecification> addFurKitToLineSpec(List<LineSpecification> lineSpec, DoorEntity doorEntity) {

        FurnitureKit furnitureKit = doorEntity.getFurnitureKit();
        DoorType doorType = doorEntity.getDoorType();

        if (furnitureKit.getHandle() != null) {
            addfurniture(lineSpec, furnitureKit.getHandle(), doorType);
        }

        if (furnitureKit.getTopLock() != null) {
            addfurniture(lineSpec, furnitureKit.getTopLock(), doorType);
        }
        if (furnitureKit.getTopLockCylinder() != null) {
            addfurniture(lineSpec, furnitureKit.getTopLockCylinder(), doorType);
        }
        if (furnitureKit.getTopInLockDecor() != null) {
            addfurniture(lineSpec, furnitureKit.getTopInLockDecor(), doorType);
        }
        if (furnitureKit.getTopOutLockDecor() != null) {
            addfurniture(lineSpec, furnitureKit.getTopOutLockDecor(), doorType);
        }
        if (furnitureKit.getLowerLock() != null) {
            addfurniture(lineSpec, furnitureKit.getLowerLock(), doorType);
        }

        if (furnitureKit.getLowerLock() != null) {
            addfurniture(lineSpec, furnitureKit.getLowerLock(), doorType);
        }
        if (furnitureKit.getLowerLockCylinder() != null) {
            addfurniture(lineSpec, furnitureKit.getLowerLockCylinder(), doorType);
        }
        if (furnitureKit.getLowerInLockDecor() != null) {
            addfurniture(lineSpec, furnitureKit.getLowerInLockDecor(), doorType);
        }
        if (furnitureKit.getLowerOutLockDecor() != null) {
            addfurniture(lineSpec, furnitureKit.getLowerOutLockDecor(), doorType);
        }

        if (furnitureKit.getCloser() != null) {
            addfurniture(lineSpec, furnitureKit.getCloser(), doorType);
        }
        if (furnitureKit.getEndDoorLock() != null) {
            addfurniture(lineSpec, furnitureKit.getEndDoorLock(), doorType);
        }


        return lineSpec;
    }

    public void addfurniture(List<LineSpecification> lineSpec, DoorFurniture furniture, DoorType doorType) {

        lineSpec.add(LineSpecification.builder()
                .name(furniture.getName())
                .value(1)
                .materialId(furniture.getIdManufacturerProgram())
                .doorType(doorType)
                .build());


    }
}
