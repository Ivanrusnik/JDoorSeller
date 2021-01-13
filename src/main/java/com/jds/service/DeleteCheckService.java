package com.jds.service;

import com.jds.dao.entity.*;
import com.jds.dao.repository.MainDAO;
import com.jds.model.image.TypeOfImage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeleteCheckService {

    @Autowired
    private MainDAO mainDAO;

    public DeleteCheckService() {
    }

    public boolean checkColor(ImageEntity color){

        List<DoorEntity> doors = mainDAO.getDoors();

        for (int i = 0; i<doors.size(); i++){

            if (color.getTypeOfImage().equals(TypeOfImage.DOOR_COLOR)){
                if (checkTypeDoorColor(doors.get(i), color)) return true;
            }
            if (color.getTypeOfImage().equals(TypeOfImage.SHIELD_COLOR)){
                if (checkTypeShieldColor(doors.get(i),color)) return true;
            }
            if (color.getTypeOfImage().equals(TypeOfImage.SHIELD_DESIGN)){
                if (checkTypeShieldDesign(doors.get(i),color)) return true;
            }
            if (color.getTypeOfImage().equals(TypeOfImage.DOOR_DESIGN)){
                if (checkTypeDoorDesign(doors.get(i), color)) return true;
            }
        }
        return false;
    }

    public boolean checkTypeDoorColor(DoorEntity door, ImageEntity color){
        if (door.getDoorDesign().getDoorColor().getId() == color.getId()) return true;
        else return false;
    }

    public boolean checkTypeDoorDesign(DoorEntity door, ImageEntity color){
        if (door.getDoorDesign().getDoorDesign().getId() == color.getId()) return true;
        else return false;
    }

    public boolean checkTypeShieldColor(DoorEntity door, ImageEntity color){
        if (door.getShieldKit().getShieldColor().getId() == color.getId()) return true;
         else return false;
    }

    public boolean checkTypeShieldDesign(DoorEntity door, ImageEntity color){
        if (door.getShieldKit().getShieldDesign().getId() == color.getId()) return true;
        else return false;
    }

    public boolean checkFurniture(DoorFurniture furniture){
        List<DoorEntity> doors = mainDAO.getDoors();

        for (int i = 0; i<doors.size(); i++){

            FurnitureKit listFurniture = doors.get(i).getFurnitureKit();
            if (listFurniture.isContentFurniture(furniture.getId())) return true;

        }
        return false;
    }


}
