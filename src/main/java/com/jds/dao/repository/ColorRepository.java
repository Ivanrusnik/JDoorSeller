package com.jds.dao.repository;

import com.jds.dao.entity.ImageEntity;
import com.jds.model.image.TypeOfImage;
import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ColorRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public int getColorsIdByManufacturerId(String id) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_colors where idManufacturerProgram like :log";
        Query query = session.createSQLQuery(sql)
                .addEntity(ImageEntity.class)
                .setParameter("log", id);
        List<ImageEntity> doorColorsList = query.list();

        session.close();

        if (doorColorsList.size() > 0) {
            return doorColorsList.get(0).getId();
        }
        return 0;

    }

    public ImageEntity getDoorColorByName(String name) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_colors where name like :log";
        Query query = session.createSQLQuery(sql)
                .addEntity(ImageEntity.class)
                .setParameter("log", name);
        List<ImageEntity> doorColorsList = query.list();

        session.close();

        if (doorColorsList.size() > 0) {
            return doorColorsList.get(0);
        }
        return null;

    }

    public List<ImageEntity> getDoorColors() {
        return getImage(TypeOfImage.DOOR_COLOR);
    }
    public List<ImageEntity> getDoorDesign() {
        return getImage(TypeOfImage.DOOR_DESIGN);
    }
    public List<ImageEntity> getShieldColor() {
        return getImage(TypeOfImage.SHIELD_COLOR);
    }
    public List<ImageEntity> getShieldDesign() {
        return getImage(TypeOfImage.SHIELD_DESIGN);
    }
    public List<ImageEntity> getShieldGlass() {
        return getImage(TypeOfImage.SHIELD_GLASS);
    }

    private List<ImageEntity> getImage(TypeOfImage typeImage) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_colors where typeofimage like :typeofimage";
        Query query = session.createSQLQuery(sql)
                .addEntity(ImageEntity.class)
                .setParameter("typeofimage", typeImage.toString());
        List<ImageEntity> doorColorsList = query.list();

        session.close();

        return doorColorsList;
    }

    public List<ImageEntity> getImages() {
        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_colors ";
        Query query = session.createSQLQuery(sql)
                .addEntity(com.jds.dao.entity.ImageEntity.class);
        List<com.jds.dao.entity.ImageEntity> doorColorsList = query.list();

        session.close();

        return doorColorsList;
    }

    public List<ImageEntity> getImagesContainsGlass() {
        Session session = sessionFactory.openSession();
        int containsGlass = 1;
        String sql;
        sql = "select * from door_colors where containsGlass = :containsGlass";
        Query query = session.createSQLQuery(sql)
                .addEntity(ImageEntity.class)
                .setParameter("containsGlass", containsGlass);
        List<ImageEntity> doorColorsList = query.list();

        session.close();

        return doorColorsList;
    }

    public ImageEntity getColorById(@NonNull int id) {
        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_colors where id = :id";
        Query query = session.createSQLQuery(sql)
                .addEntity(ImageEntity.class)
                .setParameter("id", id);
        List<ImageEntity> list = query.list();

        session.close();

        if (list.size() > 0) {
            return list.get(0);
        }
        return new ImageEntity();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ImageEntity saveColors(ImageEntity doorColors) {

        int id = getColorsIdByManufacturerId(doorColors.getIdManufacturerProgram());//check exists
        if (id > 0) {
            doorColors.setId(id);
        }

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(doorColors);

        return doorColors;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String deleteColor(@NonNull ImageEntity colors) {

        Session session = sessionFactory.getCurrentSession();
        session.delete(colors);

        return "ok";

    }

}
