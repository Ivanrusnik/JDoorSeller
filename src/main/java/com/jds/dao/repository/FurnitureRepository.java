package com.jds.dao.repository;

import com.jds.dao.entity.DoorFurniture;
import com.jds.model.modelEnum.TypeOfFurniture;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class FurnitureRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public List<DoorFurniture> getFurniture(TypeOfFurniture type) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_furniture where typeoffurniture like :typefurniture";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorFurniture.class)
                .setParameter("typefurniture", type.toString());
        List<DoorFurniture> doorFurnitureList = query.list();

        session.close();

        return doorFurnitureList;
    }

    public List<DoorFurniture> getFurniture() {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_furniture ";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorFurniture.class);
        List<DoorFurniture> doorFurnitureList = query.list();

        session.close();

        doorFurnitureList = doorFurnitureList.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());

        return doorFurnitureList;
    }

    public DoorFurniture getFurnitureById(int id) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_furniture where id = :id";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorFurniture.class)
                .setParameter("id", id);
        List<DoorFurniture> furnitureList = query.list();

        session.close();

        if (furnitureList.size() > 0) {
            return furnitureList.get(0);
        }

        return new DoorFurniture();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String deleteFurniture(DoorFurniture furniture) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(furniture);
        return "ok";
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public String saveFurniture(DoorFurniture furniture) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(furniture);
        return "ok";
    }
}
