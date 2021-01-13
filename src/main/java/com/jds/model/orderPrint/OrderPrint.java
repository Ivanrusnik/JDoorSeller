package com.jds.model.orderPrint;

import com.jds.dao.entity.DoorsОrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class OrderPrint {

    private int order_id;
    private Date data;
    private String company;
    private String partner;
    private int totalAmount;
    private int totalTax;
    private int totalQuantity;
    private List<OrderPrintLine> doorsLine;

    public OrderPrint(DoorsОrder order, OrderDiscounts ordersDiscounts) {
        this.order_id = order.getOrder_id();
        this.data = order.getData();
        this.company = order.getCompany();
        this.partner = order.getPartner();
        this.totalAmount = order.getTotalAmount();
        this.totalTax = order.getTotalTax();
        this.totalQuantity = order.getTotalQuantity();

        doorsLine = order.getDoors().stream()
                .map((door) -> new OrderPrintLine(door, ordersDiscounts))
                .sorted((line1, line2) -> -line1.compareTo(line2))
                .collect(Collectors.toList());
    }

}
