package com.jds.dao.repository;

import com.jds.dao.entity.DoorFurniture;
import com.jds.dao.entity.DoorsОrder;
import com.jds.dao.entity.OrderDiscount;
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
public class OrderDiscountRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDiscount saveOrderDiscount(OrderDiscount orderDiscount) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(orderDiscount);
        return orderDiscount;
    }

    public List<OrderDiscount> getOrderDiscount() {
        Session session = sessionFactory.openSession();

        String sql = "select * from order_discount";
        Query query = session.createSQLQuery(sql).addEntity(OrderDiscount.class);
        List<OrderDiscount> list = query.list();
        session.close();

        return list;
    }

    public List<OrderDiscount> getOrderDiscountByOrderId(int orderId) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from order_discount where order_id = :order_id";
        Query query = session.createSQLQuery(sql)
                .addEntity(OrderDiscount.class)
                .setParameter("order_id", orderId);
        List<OrderDiscount> list = query.list();

        session.close();

        return list;
    }

    public OrderDiscount getOrderDiscountById(int orderDiscountId) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from order_discount where id = :id";
        Query query = session.createSQLQuery(sql)
                .addEntity(OrderDiscount.class)
                .setParameter("id", orderDiscountId);
        List<OrderDiscount> list = query.list();

        session.close();

        OrderDiscount orderDiscount = null;
        if (list.size() > 0) {
            orderDiscount = list.get(0);
        }

        return orderDiscount;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String deleteOrderDiscount(OrderDiscount orderDiscount) {

        Session session = sessionFactory.getCurrentSession();
        session.delete(orderDiscount);

        return "ok";

    }

}
