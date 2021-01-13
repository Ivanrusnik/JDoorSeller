package com.jds.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "line_specification")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "doorType_id")
    DoorType doorType;

    @Column(name = "material_id")
    String materialId;

    @Column(name = "name")
    String name;

    @Column(name = "value")
    double value;

    @Column(name = "formula")
    String formula;

    @Column(name = "independent_name")
    String independentName;

    @Column(name = "release_operation")
    String releaseOperation;

    @Column(name = "write_off_operation")
    String writeOffOperation;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "specification")
    private SpecificationEntity specification;

    public LineSpecification clearNonSerializingFields(){
        specification = null;
        return this;
    }
}
