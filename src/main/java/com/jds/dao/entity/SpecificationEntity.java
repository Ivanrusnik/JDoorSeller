package com.jds.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "specification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "doorType_id")
    private DoorType doorType;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "specification")
    private List<LineSpecification> lineSpecifications;

    public void clearNonSerializingFields(){
        for (LineSpecification line : lineSpecifications){
            line.setDoorType(null);
            line.setSpecification(null);
        }
    }

    public void addLine(LineSpecification line){
        lineSpecifications.add(line);
    }

    public void putLine(@NonNull LineSpecification line){
        if (line.getId() > 0){
            for (int i = 0; i < lineSpecifications.size(); i++){
                if (lineSpecifications.get(i).getId() == line.getId()){
                    lineSpecifications.set(i, line);
                }
            }
        }else {
            lineSpecifications.add(line);
        }

    }

    public SpecificationEntity(int id) {
        this.id = id;
    }

    public void setSpecificationToAllLine(SpecificationEntity spec){
        lineSpecifications.stream()
                .map(line -> setSpecification(line, spec))
                .collect(Collectors.toList());
    }

    private LineSpecification setSpecification(LineSpecification lineSpecification, SpecificationEntity specificationEntity){
        lineSpecification.setSpecification(specificationEntity);
        lineSpecification.setDoorType(doorType);
        return lineSpecification;
    }
}
