package com.secj3303.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.secj3303.model.Feedback;

@Repository
public class FeedbackDaoHibernate implements FeedbackDao {

    @Autowired
    private SessionFactory sessionFactory; // Injected from your existing XML

    @Override
    public void save(Feedback feedback) {
        // 1. Open a session
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        
        try {
            // 2. Manually start transaction
            tx = session.beginTransaction();
            
            // 3. Save the object
            session.save(feedback);
            
            // 4. Commit the transaction (Write to DB)
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback(); // Undo if error
            e.printStackTrace();
        } finally {
            session.close(); // 5. Always close the connection
        }
    }

    @Override
    public List<Feedback> findAll() {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Feedback f JOIN FETCH f.person";
            return session.createQuery(hql, Feedback.class).list();
        } finally {
            session.close();
        }
    }

    @Override
    public Feedback findById(int id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Feedback.class, id);
        } finally {
            session.close();
        }
    }
}