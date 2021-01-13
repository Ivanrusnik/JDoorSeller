package com.jds.dao.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TemplateRepository {
    @Autowired
    private SessionFactory sessionFactory;
}
