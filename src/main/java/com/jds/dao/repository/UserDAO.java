package com.jds.dao.repository;


import com.jds.dao.entity.UserEntity;
import com.jds.dao.entity.UserSetting;
import com.jds.model.Role;
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
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public UserDAO() {
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateUser(UserEntity user) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);

    }

    public UserEntity getUser(int id){

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from users where id = :log";
        Query query = session.createSQLQuery(sql)
                .addEntity(UserEntity.class)
                .setParameter("log", id);
        List<UserEntity> list = query.list();

        session.close();

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<UserEntity> getUsers(){

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from users ";
        Query query = session.createSQLQuery(sql)
                .addEntity(UserEntity.class);
        List<UserEntity> list = query.list();

        session.close();

        return list;
    }

    public UserEntity getUserByName(String name){

            Session session = sessionFactory.openSession();

            String sql;
            sql = "select * from users where login like :log";
            Query query = session.createSQLQuery(sql)
                    .addEntity(UserEntity.class)
                    .setParameter("log", name);
            List<UserEntity> list = query.list();

            session.close();

            if (list.size() > 0) {
                return list.get(0);
            }
            return null;

    }

    public UserSetting getUserSetting (int id){
        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from users_setting where id = :userId";
        Query query = session.createSQLQuery(sql)
                .addEntity(UserSetting.class)
                .setParameter("userId", id);
        List<UserSetting> list = query.list();

        session.close();

        if (list.size() > 0) {
            return list.get(0);
        }
        return new UserSetting();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUserSetting(UserSetting setting) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(setting);

    }
}
