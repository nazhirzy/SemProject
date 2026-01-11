package com.secj3303.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Module", Module.class).getResultList();
        }
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
        session.saveOrUpdate(module);
    }

    @Override
    public void delete(Module module) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(module);
            tx.commit();
        }
    }
}