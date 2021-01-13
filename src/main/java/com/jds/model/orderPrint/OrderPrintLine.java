package com.jds.model.orderPrint;

import com.jds.dao.entity.DoorEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderPrintLine implements Comparable<OrderPrintLine> {

    private int id;
    private String name;
    private int quantity;
    private int priceWithMarkup;
    private int discount;
    private int totalPrice;

    public OrderPrintLine(DoorEntity door, OrderDiscounts orderDiscounts) {
        this.id = door.getId();
        this.name = door.getName();
        this.quantity = door.getQuantity();
        this.priceWithMarkup = door.getPriceWithMarkup();

        this.discount = orderDiscounts.getByDoorId(door.getId());
        this.totalPrice = (priceWithMarkup - Math.round(priceWithMarkup * discount / 100)) * quantity;
    }

    @Override
    public int compareTo(OrderPrintLine line) {
        return this.id - line.id;
    }
}
