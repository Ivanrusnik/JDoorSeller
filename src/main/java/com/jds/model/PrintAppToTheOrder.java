package com.jds.model;

import com.jds.dao.entity.DoorEntity;
import com.jds.dao.entity.DoorsОrder;

import java.util.ArrayList;
import java.util.List;

public class PrintAppToTheOrder {

    private String orderNumber;
    private String customer;
    private String orderDate;

    private DoorEntity door;

    private List<ParamApp> sizes;
    private List<ParamApp> appearance;
    private List<ParamApp> internalView;
    private List<ParamApp> furniture;

    public PrintAppToTheOrder() {
    }

    public PrintAppToTheOrder(DoorEntity door, DoorsОrder order) {

        orderNumber = "Приложение к заказу №: " + order.getId() + " /1";
        customer = "Клиент: " + order.getPartner();
        orderDate = "Дата заказа: " + order.getData();

        this.door = door;

        sizes = new ArrayList<>();
        sizes.add(new ParamApp("Толщина металла:", String.valueOf(door.getMetal())));
        sizes.add(new ParamApp("Ширина:", String.valueOf(door.getWidthDoor())));
        sizes.add(new ParamApp("Высота:", String.valueOf(door.getHeightDoor())));
        sizes.add(new ParamApp("Глубина:", String.valueOf(door.getDeepnessDoor())));
        sizes.add(new ParamApp("Толщина полотна:", String.valueOf(door.getThicknessDoorLeaf())));
        sizes.add(new ParamApp("Открывание:", String.valueOf(door.getSideDoorOpen())));
        sizes.add(new ParamApp("Внутреннее открывание:", String.valueOf(door.getInnerOpen())));
        //sizes.add(new ParamApp("Ширина активной створки:", String.valueOf(door.getActiveDoorLeafWidth())));
        //sizes.add(new ParamApp("Высота фрамуги:", String.valueOf(door.getDoorFanlightHeight())));

        appearance = new ArrayList<>();
        appearance.add(new ParamApp("Цвет двери:", String.valueOf(door.getDoorColor())));
        if (door.getShieldKit() != null){
            appearance.add(new ParamApp("Дек.накладка:", door.getShieldKit().getShieldName()));
        }
        //appearance.add(new ParamApp("Доп.петля:", (door.getAdditionallyHingeMain() == 1 ? "да" : "нет")));
        appearance.add(new ParamApp("Стекло:", (door.getDoorGlass() != null) ? "да" : "нет"));

        internalView = new ArrayList<>();

        internalView.add(new ParamApp("Порог:", String.valueOf(door.getDoorstep())));
        internalView.add(new ParamApp("Наличник:", (door.getTopDoorTrim() == 1) ? door.getTrimName() : "нет"));
        internalView.add(new ParamApp("Цвет накладки:", "нет"));


        furniture = new ArrayList<>();
        if (door.getFurnitureKit().getTopLock() != null) {
            furniture.add(new ParamApp("Верхний замок:", String.valueOf(door.getFurnitureKit().getTopLock().getName())));
        }
        if (door.getFurnitureKit().getTopInLockDecor() != null) {
            furniture.add(new ParamApp("Внутренняя накладка", String.valueOf(door.getFurnitureKit().getTopInLockDecor().getName())));
        }
        if (door.getFurnitureKit().getTopOutLockDecor() != null) {
            furniture.add(new ParamApp("Внешняя накладка:", String.valueOf(door.getFurnitureKit().getTopOutLockDecor().getName())));
        }
        if (door.getFurnitureKit().getTopLockCylinder() != null) {
            furniture.add(new ParamApp("Цилиндр:", String.valueOf(door.getFurnitureKit().getTopLockCylinder().getName())));
        }
        if (door.getFurnitureKit().getLowerLock() != null) {
            furniture.add(new ParamApp("Нижний замок:", String.valueOf(door.getFurnitureKit().getLowerLock().getName())));
        }
        if (door.getFurnitureKit().getLowerInLockDecor() != null) {
            furniture.add(new ParamApp("Внутренняя накладка:", String.valueOf(door.getFurnitureKit().getLowerInLockDecor().getName())));
        }
        if (door.getFurnitureKit().getLowerOutLockDecor() != null) {
            furniture.add(new ParamApp("Внешняя накладка:", String.valueOf(door.getFurnitureKit().getLowerOutLockDecor().getName())));
        }
        if (door.getFurnitureKit().getLowerLockCylinder() != null) {
            furniture.add(new ParamApp("Цилиндр:", String.valueOf(door.getFurnitureKit().getLowerLockCylinder().getName())));
        }

        if (door.getFurnitureKit().getHandle() != null) {
            furniture.add(new ParamApp("Ручка:", String.valueOf(door.getFurnitureKit().getHandle().getName())));
        }
        if (door.getFurnitureKit().getCloser() != null) {
            furniture.add(new ParamApp("Задвижка:", String.valueOf(door.getFurnitureKit().getCloser().getName())));
        }
        if (door.getFurnitureKit().getEndDoorLock() != null) {
            furniture.add(new ParamApp("Торцевой дверной замок:", String.valueOf(door.getFurnitureKit().getEndDoorLock().getName())));
        }
        if (door.getFurnitureKit().getNightLock() != 0) {
            furniture.add(new ParamApp("Ночной замок:", String.valueOf(door.getFurnitureKit().getNightLock())));
        }
        if (door.getFurnitureKit().getPeephole() != 0) {
            furniture.add(new ParamApp("Дверной глазок:", String.valueOf(door.getFurnitureKit().getPeephole())));
        }
        if (door.getFurnitureKit().getAmplifierCloser() != 0) {
            furniture.add(new ParamApp("Усилитель:", String.valueOf(door.getFurnitureKit().getAmplifierCloser())));
        }
    }


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public DoorEntity getDoor() {
        return door;
    }

    public void setDoor(DoorEntity door) {
        this.door = door;
    }

    public List<ParamApp> getSizes() {
        return sizes;
    }

    public void setSizes(List<ParamApp> sizes) {
        this.sizes = sizes;
    }

    public List<ParamApp> getAppearance() {
        return appearance;
    }

    public void setAppearance(List<ParamApp> appearance) {
        this.appearance = appearance;
    }

    public List<ParamApp> getInternalView() {
        return internalView;
    }

    public void setInternalView(List<ParamApp> internalView) {
        this.internalView = internalView;
    }

    public List<ParamApp> getFurniture() {
        return furniture;
    }

    public void setFurniture(List<ParamApp> furniture) {
        this.furniture = furniture;
    }
}
