package com.jds.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users_setting")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSetting {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "retailmargin")
    private int retailMargin;

    @Column(name = "salestax")
    private int salesTax;

    @Column(name = "includestax")
    private int includesTax;

}
