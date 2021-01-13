package com.jds.dao.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_discount")
public class OrderDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "order_id")
    private int order_id;

    @Column(name = "door_id")
    private int door_id;

    @Column(name = "discount")
    private int discount;

    public OrderDiscount clearNonSerializingFields() {
        if (order_id != 0){
            order_id = 0;
        }
        if (door_id != 0){
            door_id = 0;
        }
        return this;
    }
}
