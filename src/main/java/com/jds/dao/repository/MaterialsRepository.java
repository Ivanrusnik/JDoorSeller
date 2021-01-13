package com.jds.dao.repository;

import com.jds.dao.entity.*;
import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MaterialsRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private MainDAO dAO;

    public List<MaterialFormula> getMaterialFormula() {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from material_formula ";
        Query query = session.createSQLQuery(sql)
                .addEntity(MaterialFormula.class);
        List<MaterialFormula> materialFormulas = query.list();

        session.close();

        return materialFormulas;

    }

    public List<RawMaterials> getRawMaterials() {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from raw_materials ";
        Query query = session.createSQLQuery(sql)
                .addEntity(RawMaterials.class);
        List<RawMaterials> rawMaterialsList = query.list();

        session.close();

        return rawMaterialsList;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SpecificationSetting saveSpecificationSetting(SpecificationSetting setting) {

        dAO.saveOrUpdateDoorType(setting.getDoorType());
        saveOrUpdateMaterialFormula(setting.getFormula());
        saveOrUpdateRawMaterials(setting.getRawMaterials());

        int id = getSpecificationSettingId(setting.getMetal(), setting.getDoorType().getId(), setting.getRawMaterials().getId());//check exists

        if (id > 0) {
            setting.setId(id);
        }

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(setting);

        return setting;
    }

    public List<LineSpecification> getSpecification(int id){

        Session session = sessionFactory.openSession();

        String sql = "select * from line_specification where doorType_id = :id";
        Query query = session.createSQLQuery(sql)
                .addEntity(LineSpecification.class)
                .setParameter("id", id);
        List<LineSpecification> list = query.list();

        session.close();

        return list;
    }

    public List<SpecificationEntity> getSpecification(){

        Session session = sessionFactory.openSession();

        String sql = "select * from specification";
        Query query = session.createSQLQuery(sql)
                .addEntity(SpecificationEntity.class);
        List<SpecificationEntity> list = query.list();

        session.close();

        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LineSpecification  saveLineSpecification(LineSpecification lineSpec){

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(lineSpec);

        return lineSpec;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteLineSpecification(LineSpecification lineSpecification){
        Session session = sessionFactory.getCurrentSession();
        session.delete(lineSpecification);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public MaterialFormula saveOrUpdateMaterialFormula(MaterialFormula setting) {

        setting.decodeAfterJson();

        int id = getMaterialFormulaById(setting.getIdManufacturerProgram());//check exists
        if (id > 0) {
            setting.setId(id);
        }

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(setting);

        return setting;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RawMaterials saveOrUpdateRawMaterials(RawMaterials setting) {

        int id = getRawMaterialsById(setting.getIdManufacturerProgram());//check exists
        if (id > 0) {
            setting.setId(id);
        }

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(setting);

        return setting;
    }

    public int getMaterialFormulaById(String id) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from material_formula where idmanufacturerprogram like :log ";
        Query query = session.createSQLQuery(sql)
                .addEntity(MaterialFormula.class)
                .setParameter("log", id);
        List<MaterialFormula> materialFormulas = query.list();

        session.close();

        if (materialFormulas.size() > 0) {
            return materialFormulas.get(0).getId();
        }
        return 0;

    }

    public int getRawMaterialsById(String id) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from raw_materials where idmanufacturerprogram like :log ";
        Query query = session.createSQLQuery(sql)
                .addEntity(RawMaterials.class)
                .setParameter("log", id);
        List<RawMaterials> rawMaterialsList = query.list();

        session.close();

        if (rawMaterialsList.size() > 0) {
            return rawMaterialsList.get(0).getId();
        }
        return 0;

    }

    public int getSpecificationSettingId(double metal, int typyDoorId, int rawMaterialsId) {
        Session session = sessionFactory.openSession();

        String sql = "select * from specification_setting where metal = :metalVal and doortype_id = :idDoorT and rawMaterials_id = :rawId";
        Query query = session.createSQLQuery(sql)
                .addEntity(SpecificationSetting.class)
                .setParameter("metalVal", metal)
                .setParameter("rawId", rawMaterialsId)
                .setParameter("idDoorT", typyDoorId);
        List<SpecificationSetting> list = query.list();

        session.close();

        SpecificationSetting specificationSetting = new SpecificationSetting();
        if (list.size() > 0) {
            specificationSetting = list.get(0);
        }
        return specificationSetting.getId();
    }

    public List<SpecificationSetting> getSpecificationSetting(double metal, int typyDoorId) {
        Session session = sessionFactory.openSession();

        String sql = "select * from specification_setting where metal = :metalVal and doortype_id = :idDoorT";
        Query query = session.createSQLQuery(sql)
                .addEntity(SpecificationSetting.class)
                .setParameter("metalVal", metal)
                .setParameter("idDoorT", typyDoorId);
        List<SpecificationSetting> list = query.list();

        session.close();

        List<SpecificationSetting> specificationSettingList = new ArrayList<>();
        if (list.size() > 0) {
            specificationSettingList = list;
        }
        return specificationSettingList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SpecificationEntity saveSpecificationEntity(SpecificationEntity specification) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(specification);

        return specification;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LineSpecification saveLineSpecificationEntity(LineSpecification lineSpecification) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(lineSpecification);

        return lineSpecification;
    }


    public SpecificationEntity getSpecificationEntityById(@NonNull int id) {
        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from specification where id = :id";
        Query query = session.createSQLQuery(sql)
                .addEntity(SpecificationEntity.class)
                .setParameter("id", id);
        List<SpecificationEntity> list = query.list();

        session.close();

        if (list.size() > 0) {
            return list.get(0);
        }
        return new SpecificationEntity();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String deleteSpecificationEntity(@NonNull SpecificationEntity specification) {

        Session session = sessionFactory.getCurrentSession();
        session.delete(specification);

        return "ok";

    }

    public LineSpecification getSpecificationLineById(@NonNull int id) {
        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from line_specification where id = :id";
        Query query = session.createSQLQuery(sql)
                .addEntity(LineSpecification.class)
                .setParameter("id", id);
        List<LineSpecification> list = query.list();

        session.close();

            return list.get(0);

    }

}
