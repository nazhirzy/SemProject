package com.secj3303.dao;

import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.secj3303.model.Person;
import com.secj3303.model.Sessions;
import com.secj3303.model.SessionStatus;

@Repository
public class SessionDaoHibernate implements SessionDao {
    @Autowired
    private org.hibernate.SessionFactory sessionFactory;


    private Session openSession() {
        return sessionFactory.openSession();
    }


    @Override
    public List<Sessions> findAllSessions() {
        try (Session session = openSession()) {
            return session.createQuery(
            "SELECT s FROM Sessions s LEFT JOIN FETCH s.conductor", Sessions.class)
            .getResultList();
        }
    }

    @Override
    public void book(int sessionId, Person user){
        Transaction tx = null;

        try (Session session = openSession()) {
            tx = session.beginTransaction();

            Sessions s = session.get(Sessions.class, sessionId);
            if (s == null) {
                throw new RuntimeException("Session not found with ID: " + sessionId);
            }

            if (s.getStatus() != SessionStatus.AVAILABLE) {
                throw new RuntimeException("Session is booked.");
            }


            s.setBookedBy(user);
            s.setStatus(SessionStatus.BOOKED);
            session.update(s);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void unbook(int sessionId) {
        Transaction tx = null;

        try (Session session = openSession()) {
            tx = session.beginTransaction();

            Sessions s = session.get(Sessions.class, sessionId);
            
            if (s == null) {
                throw new RuntimeException("Session not found with ID: " + sessionId);
            }
            s.setBookedBy(null); 
            s.setStatus(SessionStatus.AVAILABLE); 
            
            session.update(s);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public List<Sessions> findSessionsByStatus(SessionStatus status) {
        try (Session session = openSession()) {
            Query<Sessions> query = session.createQuery(
                "from Sessions s where s.status = :status", Sessions.class);
            query.setParameter("status", status);
            return query.getResultList();
        }
    }

    @Override
    public void save(Sessions sessions){
        Session session = openSession();
        session.save(sessions);
    }

    @Override
    public List<Sessions> findByBookedById(int userId) {
        try (Session session = openSession()) {
            return session.createQuery(
                "SELECT s FROM Sessions s " +
                "LEFT JOIN FETCH s.conductor " + 
                "WHERE s.bookedBy.id = :uid", Sessions.class)
                .setParameter("uid", userId)
                .getResultList();
        }
    }

    @Override
    public List<Sessions> findByConductorId(int userId) {
        try (Session session = openSession()) {
            return session.createQuery(
                "SELECT s FROM Sessions s " +
                "LEFT JOIN FETCH s.bookedBy " + 
                "WHERE s.conductor.id = :uid", Sessions.class)
                .setParameter("uid", userId)
                .getResultList();
        }
    }

    @Override
    public void clearBooking(Person member) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery(
                "UPDATE Sessions s SET s.bookedBy = null WHERE s.bookedBy = :member"
            ).setParameter("member", member)
            .executeUpdate();

            tx.commit();
        }
    }

}
