package com.jds.service;

import com.jds.dao.entity.DoorEntity;
import com.jds.dao.entity.DoorsОrder;
import com.jds.dao.entity.LineSpecification;
import lombok.NonNull;

import java.util.List;

public interface DoorServ {
    DoorEntity getDoor(@NonNull int id, @NonNull int orderId, @NonNull int typid);
    DoorEntity calculateTheDoor(@NonNull DoorEntity door);
    DoorEntity saveDoor(@NonNull DoorEntity door);
    DoorsОrder deleteDoorFromOrder(@NonNull String id, @NonNull String orderId);
    List<LineSpecification> getSpecificationByDoorId(@NonNull String doorId);
}
