package com.secj3303.dao;

import com.secj3303.model.Feedback;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class FeedbackDaoHibernate implements FeedbackDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Feedback feedback) {
        entityManager.persist(feedback);
    }

    @Override
    public List<Feedback> findAll() {
        return entityManager.createQuery("from Feedback", Feedback.class).getResultList();
    }

    @Override
    public Feedback findById(int id) {
        return entityManager.find(Feedback.class, id);
    }

}