package com.jds.dao.repository;

import com.jds.dao.entity.*;
import com.jds.model.FireproofDoor;
import com.jds.model.image.TypeOfImage;
import com.jds.model.modelEnum.TypeOfFurniture;
import com.jds.model.modelEnum.TypeOfSalaryConst;
import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class MainDAO {

    @Autowired
    private SessionFactory sessionFactory;


    public MainDAO() {
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateDoorClass(DoorClass doorClass) {

        int id = getDoorClassId(doorClass.getName());//check exists
        if (id > 0) {
            doorClass.setId(id);
        }
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(doorClass);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateDoorClassById(DoorClass doorClass) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(doorClass);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteDoorClass(DoorClass doorClass) {

        Session session = sessionFactory.getCurrentSession();
        session.delete(doorClass);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteLimit(LimitationDoor limitationDoor ) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(limitationDoor);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int saveOrUpdateDoorType(DoorType doorType) {

        doorType.makeRightNamePictureDoorType();

        int id = getDooTypeId(doorType.getName());//check exists
        if (id > 0) {
            doorType.setId(id);
        }

        DoorClass doorClass = doorType.getDoorClass();
        id = getDoorClassId(doorClass.getName());//check exists
        if (id > 0) {
            doorClass.setId(id);
            doorType.setDoorClass(doorClass);
        }

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(doorType);

        return doorType.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDoorFurniture(DoorFurniture doorFurniture) {

        saveOrUpdateDoorType(doorFurniture.getDoorType());
        doorFurniture.makeRightNamePictureDoorType();

        int id = getDoorFurnitureId(doorFurniture.getIdManufacturerProgram(), doorFurniture.getDoorType().getId());//check exists
        if (id > 0) {
            doorFurniture.setId(id);
        }

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(doorFurniture);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateLimitationDoor(@NonNull LimitationDoor limitationDoor) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(limitationDoor);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateSizeOfDoorParts(SizeOfDoorParts sizeOfDoorParts) {


        sizeOfDoorParts.decodeAfterJson();

        saveOrUpdateDoorType(sizeOfDoorParts.getDoorType());

        int id = getSizeOfDoorPartsId(sizeOfDoorParts.getName(), sizeOfDoorParts.getDoorType().getId());//check exists
        if (id > 0) {
            sizeOfDoorParts.setId(id);
        }


        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(sizeOfDoorParts);

    }



    @Transactional(propagation = Propagation.REQUIRED)
    public DoorEntity saveDoor(DoorEntity door) {


        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(door);

        return door;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BendSetting saveBendSetting(BendSetting bendSetting) {

        int idDoorType = saveOrUpdateDoorType(bendSetting.getDoorType());


        int id = getbendSettingId(idDoorType, bendSetting.getMetal(), bendSetting.getSealingLine()).getId();//check exists
        if (id > 0) {
            bendSetting.setId(id);
        }

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(bendSetting);

        return bendSetting;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SalaryConstants saveSalaryConstants(SalaryConstants constants) {
        int id = getSalaryConstantsId(constants.getName()).getId();//check exists
        if (id > 0) {
            constants.setId(id);
        }

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(constants);

        return constants;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SalarySetting saveSalarySetting(SalarySetting setting) {
        int id = getSalarySetting(setting.getMetal()).getId();//check exists
        if (id > 0) {
            setting.setId(id);
        }

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(setting);

        return setting;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LimitationDoor saveLimitationDoor(LimitationDoor limit){

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(limit);

        return limit;
    }

    public Map<TypeOfSalaryConst, Double> getSalaryConstantsMap() {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from salary_constants";
        Query query = session.createSQLQuery(sql)
                .addEntity(SalaryConstants.class);
        List<SalaryConstants> salaryConstantsList = query.getResultList();

        session.close();

        Map<TypeOfSalaryConst, Double> salaryConstantsMap = new HashMap<>();
        for (SalaryConstants salaryConstants : salaryConstantsList) {
            salaryConstantsMap.put(salaryConstants.getName(), salaryConstants.getValue());
        }

        return salaryConstantsMap;
    }

    public SalaryConstants getSalaryConstantsId(TypeOfSalaryConst name) {
        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from salary_constants where name like :nameSC ";
        Query query = session.createSQLQuery(sql)
                .addEntity(SalaryConstants.class)
                .setParameter("nameSC", name.toString());
        List<SalaryConstants> salaryConstantsList = query.list();

        session.close();

        if (salaryConstantsList.size() > 0) {
            return salaryConstantsList.get(0);
        }
        return new SalaryConstants();
    }

    public BendSetting getbendSettingId(int doorTypeId, double metal, int sealingLine) {
        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from bend_setting where doortype_id =:TypeId and metal =:metalvalue and sealingline =:sealing ";
        Query query = session.createSQLQuery(sql)
                .addEntity(BendSetting.class)
                .setParameter("TypeId", doorTypeId)
                .setParameter("metalvalue", metal)
                .setParameter("sealing", sealingLine);
        List<BendSetting> BendList = query.list();

        session.close();

        if (BendList.size() > 0) {
            return BendList.get(0);
        }
        return new BendSetting();
    }

    public int getDoorClassId(String name) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_class where name like :log";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorClass.class)
                .setParameter("log", name);
        List<DoorClass> doorClassList = query.list();

        session.close();

        if (doorClassList.size() > 0) {
            return doorClassList.get(0).getId();
        }
        return 0;

    }

    public int getDooTypeId(String name) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_type where name like :log";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorType.class)
                .setParameter("log", name);
        List<DoorType> doorTypeList = query.list();

        session.close();

        if (doorTypeList.size() > 0) {
            return doorTypeList.get(0).getId();
        }
        return 0;

    }


    public int getDoorFurnitureId(String id, int idDoorType) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_furniture where idmanufacturerprogram like :log and doortype_id = :idDoorT";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorFurniture.class)
                .setParameter("log", id)
                .setParameter("idDoorT", idDoorType);
        List<DoorFurniture> doorFurnitureList = query.list();

        session.close();

        if (doorFurnitureList.size() > 0) {
            return doorFurnitureList.get(0).getId();
        }
        return 0;

    }

    public DoorFurniture getDoorFurnitureId(int id) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_furniture where id = :fId";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorFurniture.class)
                .setParameter("fId", id);
        List<DoorFurniture> doorFurnitureList = query.list();

        session.close();

        if (doorFurnitureList.size() > 0) {
            return doorFurnitureList.get(0);
        }
        return null;

    }

    public List<DoorFurniture> getFurnitureByType(TypeOfFurniture type, int idType) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_furniture where doortype_id = :idtype and typeoffurniture like :typefurniture";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorFurniture.class)
                .setParameter("idtype", idType)
                .setParameter("typefurniture", type.toString());
        List<DoorFurniture> doorFurnitureList = query.list();

        session.close();

        return doorFurnitureList;
    }


    public int getSizeOfDoorPartsId(String name, int doortype) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from size_door_parts where name like :log and doortype_id = :doortype";
        Query query = session.createSQLQuery(sql)
                .addEntity(SizeOfDoorParts.class)
                .setParameter("log", name)
                .setParameter("doortype", doortype);
        List<SizeOfDoorParts> sizeOfDoorPartsList = query.list();

        session.close();

        if (sizeOfDoorPartsList.size() > 0) {
            return sizeOfDoorPartsList.get(0).getId();
        }
        return 0;

    }

    public DoorEntity getDoor(int id) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door where door_id = :log";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorEntity.class)
                .setParameter("log", id);
        List<DoorEntity> doorsList = query.list();

        session.close();

        DoorEntity door = null;
        if (doorsList.size() > 0) {
            door = doorsList.get(0).initKitFields();
        }
        return door;
    }

    public List<DoorEntity> getDoors() {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorEntity.class);
        List<DoorEntity> doorsList = query.list();

        session.close();

        return doorsList;
    }

    public List<SizeOfDoorParts> getSizeOfDoorPartsList(int doortypeId) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from size_door_parts where doortype_id = :log";
        Query query = session.createSQLQuery(sql)
                .addEntity(SizeOfDoorParts.class)
                .setParameter("log", doortypeId);

        List<SizeOfDoorParts> list = query.list();

        session.close();

        return list;

    }

    public DoorClass getDoorClass(int id) {

        Session session = sessionFactory.openSession();

        String sql = "select * from door_class where id = :log";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorClass.class)
                .setParameter("log", id);
        List<DoorClass> list = query.list();

        session.close();

        DoorClass doorClass = new DoorClass();
        if (list.size() > 0) {
            doorClass = list.get(0);
        }
        return doorClass;

    }


    public List<DoorClass> getAvailableDoorClass() {

        Session session = sessionFactory.openSession();

        String sql = "select * from door_class ";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorClass.class);
        List<DoorClass> list = query.list();

        session.close();

        return list;

    }

    public List<DoorClass> getAvailableDoorClass(int id) {

        Session session = sessionFactory.openSession();

        String sql = "select * from door_class where id = :classId";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorClass.class)
                .setParameter("classId", id);
        List<DoorClass> list = query.list();

        session.close();

        return list;

    }

    public List<DoorClass> getDoorClass() {

        Session session = sessionFactory.openSession();

        String sql = "select * from door_class";
        Query query = session.createSQLQuery(sql).addEntity(DoorClass.class);

        List<DoorClass> list = query.list();

        session.close();

        return list;

    }

    public DoorType getDoorType(int id) {

        Session session = sessionFactory.openSession();

        String sql = "select * from door_type where id = :val ";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorType.class)
                .setParameter("val", id);

        List<DoorType> list = query.list();

        session.close();

        DoorType doorType = new DoorType();
        if (list.size() > 0) {
            doorType = list.get(0);
        }
        return doorType;
    }

    public List<DoorType> getDoorType() {

        Session session = sessionFactory.openSession();

        String sql = "select * from door_type";
        Query query = session.createSQLQuery(sql).addEntity(DoorType.class);

        List<DoorType> list = query.list();

        session.close();

        return list;

    }

    public List<FireproofDoor> getlistDoor() {

        List<FireproofDoor> list = new ArrayList<>();
        list.add(new FireproofDoor());

        return list;
    }


    public SalarySetting getSalarySetting(double metal) {

        Session session = sessionFactory.openSession();

        String sql = "select * from salary_setting where metal = :metalVal";
        Query query = session.createSQLQuery(sql)
                .addEntity(SalarySetting.class)
                .setParameter("metalVal", metal);
        List<SalarySetting> list = query.list();

        session.close();

        SalarySetting salarySetting = new SalarySetting();
        if (list.size() > 0) {
            salarySetting = list.get(0);
        }
        return salarySetting;
    }

    public List<LimitationDoor> getLimitationDoor (int doorTypeId){

        Session session = sessionFactory.openSession();

        String sql = "select * from limitation_door where doortype_id = :id";
        Query query = session.createSQLQuery(sql)
                .addEntity(LimitationDoor.class)
                .setParameter("id", doorTypeId);
        List<LimitationDoor> list = query.list();

        session.close();

        List<LimitationDoor> limitList = new ArrayList<>();
        if (list.size() > 0) {
            limitList = list;
        }
        return limitList;
    }

    public List<DoorType> getDoorTypeListFromLimitTable (){

        Session session = sessionFactory.openSession();

        String sql = "select DISTINCT  door_type.* from door_type" +
                " inner join limitation_door on door_type.id = limitation_door.doortype_id";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorType.class);
        List<DoorType> list = query.list();

        session.close();

        List<DoorType> limitList = new ArrayList<>();
        if (list.size() > 0) {
            limitList = list;
        }
        return limitList;

    }

    public List<LimitationDoor> getLimitByTypeOfImages (TypeOfImage type){

        Session session = sessionFactory.openSession();

        String sql = "select * from limitation_door where typesettings like :typesettings";
        Query query = session.createSQLQuery(sql)
                .addEntity(LimitationDoor.class)
                .setParameter("typesettings", type.toString());
        List<LimitationDoor> list = query.list();

        session.close();

        List<LimitationDoor> limitList = new ArrayList<>();
        if (list.size() > 0) {
            limitList = list;
        }
        return limitList;
    }

}
