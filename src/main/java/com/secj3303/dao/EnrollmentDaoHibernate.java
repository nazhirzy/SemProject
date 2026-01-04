package com.secj3303.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.secj3303.model.Enrollment;
@Repository
public class EnrollmentDaoHibernate implements EnrollmentDao{
    @Autowired
    private org.hibernate.SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.openSession();
    }
    
    @Override
    public void save(Enrollment enrollment) {
        Session session = openSession();
        session.save(enrollment);
    }

    // Inside EnrollmentDaoHibernate.java
public void deleteEnrollmentsByMemberId(int memberId) {
    Session session = openSession();
    session.beginTransaction();
    try {
        session.createQuery("DELETE FROM Enrollment e WHERE e.member.id = :memberId")
               .setParameter("memberId", memberId)
               .executeUpdate();
        session.getTransaction().commit();
    } catch (Exception e) {
        // ... rollback and logging ...
    } finally {
        // ... close session ...
    }
}
    public void deleteEnrollmentsByProgramId(int programId) {
    Session session = openSession();
    session.beginTransaction();
    try {
        // This HQL query deletes all Enrollment records associated with the given programId.
        session.createQuery("DELETE FROM Enrollment e WHERE e.program.id = :programId")
               .setParameter("programId", programId)
               .executeUpdate();
        session.getTransaction().commit();
    } catch (Exception e) {
        if (session.getTransaction() != null) {
            session.getTransaction().rollback();
        }
        System.err.println("Error deleting enrollments for program ID " + programId);
        e.printStackTrace();
    } finally {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    }   

    @Override
    public List<Enrollment> findByMemberId(int memberId) {
        Session session = openSession();

        List<Enrollment> list = session
            .createQuery("FROM Enrollment e WHERE e.member.id = :memberId", Enrollment.class)
            .setParameter("memberId", memberId)
            .list();

        return list;
    }
}
