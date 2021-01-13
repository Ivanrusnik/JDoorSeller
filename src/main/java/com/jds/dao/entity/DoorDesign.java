package com.jds.dao.entity;

import com.jds.dao.repository.ColorRepository;
import com.jds.model.AvailableFieldsForSelection;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "design")
public class DoorDesign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "door_color")
    private ImageEntity doorColor;

    @ManyToOne()
    @JoinColumn(name = "door_design")
    private ImageEntity doorDesign;

    @OneToOne(mappedBy = "doorDesign",fetch = FetchType.LAZY)
    private DoorEntity door;

    public static DoorDesign instanceDesign(@NonNull List<LimitationDoor> listLim, @NonNull List<LimitationDoor> templateDesign, ColorRepository colorDao) {
        DoorDesign design = new DoorDesign();

        List<LimitationDoor> defList = listLim.stream()
                .filter(lim -> lim.isDefault())
                .collect(Collectors.toList());


        if (defList.size() == 1) {
            design.setDoorColor(colorDao.getColorById(defList.get(0).getItemId()));
        }
        if (templateDesign.size() == 1) {
            design.setDoorDesign(colorDao.getColorById(templateDesign.get(0).getItemId()));
        }
        design.clearNonSerializingFields();
        return design;
    }

    private static ImageEntity getFirst(List<ImageEntity> list) {
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public boolean exists() {
        if (doorColor == null && doorDesign == null) {
            return false;
        }
        return true;
    }

    public DoorDesign clearNonSerializingFields() {
        door = null;
        if (doorColor!=null){
            doorColor.clearNonSerializingFields();
        }
        if (doorDesign !=null){
            doorDesign.clearNonSerializingFields();
        }

        return this;
    }

}
