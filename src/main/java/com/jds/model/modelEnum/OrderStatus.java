package com.jds.model.modelEnum;

import java.util.ArrayList;
import java.util.List;

public enum OrderStatus {

    CALC("расчет"),
    TO_WORK("в работу"),
    IN_WORK("в работе"),
    READY("готов"),
    CLOSED("закрыт");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static OrderStatus startStatus() {
        return CALC;
    }

    public static List<OrderStatus> statusList(OrderStatus orderStatus) {

        List<OrderStatus> list = new ArrayList<>();
        switch (orderStatus) {
            case IN_WORK:
                list.add(IN_WORK);
                break;
            case READY:
                list.add(READY);
                list.add(CLOSED);
                break;
            default:
                list.add(CALC);
                list.add(TO_WORK);
                list.add(CLOSED);
                break;
        }
        return list;
    }
}
