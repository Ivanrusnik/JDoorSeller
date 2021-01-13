package com.jds.dao.entity;

import com.jds.model.AvailableFieldsForSelection;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ShieldKit")
public class ShieldKit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "shieldColor")
    private ImageEntity shieldColor;

    @ManyToOne()
    @JoinColumn(name = "shieldDesign")
    private ImageEntity shieldDesign;

    @ManyToOne()
    @JoinColumn(name = "shieldGlass")
    private ImageEntity shieldGlass;

    @OneToOne(mappedBy = "shieldKit", fetch = FetchType.LAZY)
    private DoorEntity door;

    public static ShieldKit instanceKit(@NonNull AvailableFieldsForSelection AvailableFields) {
        ShieldKit kit = new ShieldKit();
        kit.setShieldColor(getFirst(AvailableFields.getShieldColor()));
        kit.setShieldDesign(getFirst(AvailableFields.getShieldDesign()));
        kit.setShieldGlass(getFirst(AvailableFields.getShieldGlass()));
        return kit;
    }

    private static ImageEntity getFirst(List<ImageEntity> list) {
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public boolean exists() {
        if (shieldColor == null && shieldDesign == null) {
            return false;
        }
        return true;
    }

    public ShieldKit clearNonSerializingFields() {
        door = null;
        if (shieldColor != null) {
            shieldColor.clearNonSerializingFields();
        }
        if (shieldDesign != null) {
            shieldDesign.clearNonSerializingFields();
        }
        if (shieldGlass != null) {
            shieldGlass.clearNonSerializingFields();
        }
        return this;
    }

    @Override
    public String toString() {

        String shieldColorName = shieldColor != null ? shieldColor.getName() + " " : "";
        String shieldDesignName = shieldDesign != null ? " ( " + shieldDesign.getName() + " )" : "";

        return " - " + shieldColorName + shieldDesignName;
    }

    public String getShieldName(){
        String shieldColorString = (getShieldColor() != null) ? getShieldColor().getName() : "";
        String shieldDesignString = (getShieldDesign() != null) ? " ( " + getShieldDesign().getName() + " ) " : "";
        String shieldGlassString = (getShieldGlass() != null) ? getShieldGlass().getName() : "";
        return shieldColorString + shieldDesignString + shieldGlassString;
    }

}
