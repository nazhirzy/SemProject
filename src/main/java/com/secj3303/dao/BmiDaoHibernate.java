package com.secj3303.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.secj3303.model.Bmi;

@Repository
public class BmiDaoHibernate implements BmiDao {
    @Autowired
    private org.hibernate.SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.openSession();
    }

    @Override
    public void save(Bmi bmi) {
        Session session = openSession();
        session.save(bmi);
    }

    @Override
    public List<Bmi> findById(int member_id) {
        Session session = openSession();

        List<Bmi> list = session
            .createQuery("FROM Bmi b WHERE b.member.id = :member_id", Bmi.class)
            .setParameter("member_id", member_id)
            .list();

        return list;
    }
}
