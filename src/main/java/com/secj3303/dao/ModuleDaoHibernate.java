package com.secj3303.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.secj3303.model.Module;

@Repository
public class ModuleDaoHibernate implements ModuleDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.openSession();
    }

    @Override
    public List<Module> findAll() {
        Session session = openSession();
        return session.createQuery("FROM Module", Module.class).getResultList();
    }

    @Override
    public Module findByCategory(String category) {
        Session session = openSession();
        return session.createQuery("FROM Module WHERE category = :category", Module.class)
                .setParameter("category", category)
                .uniqueResult();
    }

    @Override
    public Module findById(int id) {
        Session session = openSession();
        return session.get(Module.class, id);
    }

    @Override
    @Transactional
    public void save(Module module) {
        Session session = openSession();
        session.save(module);
    }
}