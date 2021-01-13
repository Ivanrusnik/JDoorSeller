package com.jds.dao.repository;

import com.jds.dao.entity.Metal;
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
public class MetalRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Metal> getMetals() {

        Session session = sessionFactory.openSession();

        String sql = "select * from metal";
        Query query = session.createSQLQuery(sql).addEntity(Metal.class);

        List<Metal> list = query.list();

        session.close();

        return list;

    }

    public int getMetalId(@NonNull String id) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from metal where id_manufacturer_program like :log";
        Query query = session.createSQLQuery(sql)
                .addEntity(Metal.class)
                .setParameter("log", id);
        List<Metal> metalList = query.list();

        session.close();

        if (metalList.size() > 0) {
            return metalList.get(0).getId();
        }
        return 0;

    }

    public Metal getMetal(@NonNull double val) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from metal where name_displayed = :log";
        Query query = session.createSQLQuery(sql)
                .addEntity(Metal.class)
                .setParameter("log", val);
        List<Metal> metalList = query.list();

        session.close();

        if (metalList.size() > 0) {
            return metalList.get(0);
        }
        return null;

    }

    public Metal getMetalById(@NonNull int id) {
        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from metal where id = :id";
        Query query = session.createSQLQuery(sql)
                .addEntity(Metal.class)
                .setParameter("id", id);
        List<Metal> metalList = query.list();

        session.close();

        if (metalList.size() > 0) {
            return metalList.get(0);
        }
        return new Metal();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveMetal(@NonNull Metal metal) {

        int id = getMetalId(metal.getIdManufacturerProgram());//check exists
        if (id > 0) {
            metal.setId(id);
        }

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(metal);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String deleteMetal(@NonNull Metal metal) {

        Session session = sessionFactory.getCurrentSession();
        session.delete(metal);

        return "ok";

    }
}
