package com.secj3303.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.secj3303.model.Sessions;
import com.secj3303.model.Survey;
import com.secj3303.model.SurveyQuestion;

@Repository
public class SurveyDaoHibernate implements SurveyDao {
    @Autowired
    private org.hibernate.SessionFactory sessionFactory;


    private Session openSession() {
        return sessionFactory.openSession();
    }

    @Override
    public void saveSurvey(Survey s) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(s);
            tx.commit();
        }
    }
    @Override
    public void saveQuestion(SurveyQuestion q) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(q);
            tx.commit();
        }
    }

    @Override
    public Survey findSurveyById(int id) {
        try (Session session = sessionFactory.openSession()) {
        return session.createQuery(
            "SELECT s FROM Survey s " +
            "LEFT JOIN FETCH s.questions " + 
            "WHERE s.id = :id", Survey.class)
            .setParameter("id", id)
            .uniqueResult();
    }
    }

    @Override
    public List<Survey> findAllSurveys() {
        try (Session session = openSession()) {
            return session.createQuery(
                "SELECT s FROM Survey s",
                Survey.class)
                .getResultList();
        }
    }
}
