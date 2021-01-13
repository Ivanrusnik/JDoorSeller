package com.jds.dao.repository;

import com.jds.dao.entity.DoorsОrder;
import com.jds.dao.entity.UserEntity;
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
public class OrderDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<DoorsОrder> getOrders() {

        Session session = sessionFactory.openSession();

        String sql = "select * from orders";
        Query query = session.createSQLQuery(sql).addEntity(DoorsОrder.class);

        List<DoorsОrder> list = query.list();

        session.close();

        return list;
    }

    public List<DoorsОrder> getOrdersByStatus(String status){

        Session session = sessionFactory.openSession();

        String sql = "select * from orders where status like :status";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorsОrder.class)
                .setParameter("status", status);

        List<DoorsОrder> list = query.list();

        session.close();

        return list;

    }

    public List<DoorsОrder> getOrdersByUser(UserEntity user) {

        Session session = sessionFactory.openSession();

        String sql = "select * from orders where seller = :valId";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorsОrder.class)
                .setParameter("valId",  user.getId());

        List<DoorsОrder> list = query.list();

        session.close();

        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DoorsОrder saveOrder(DoorsОrder Оrder) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(Оrder);

        return Оrder;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteOrder(DoorsОrder order) {

        Session session = sessionFactory.getCurrentSession();
        session.delete(order);

    }

    public DoorsОrder getOrder(int id) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from orders where order_id = :log";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorsОrder.class)
                .setParameter("log", id);
        List<DoorsОrder> list = query.list();

        session.close();

        DoorsОrder оrder = null;
        if (list.size() > 0) {
            оrder = list.get(0);
        }

        return оrder;
    }



}
