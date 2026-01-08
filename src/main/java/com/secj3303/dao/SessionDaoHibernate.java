package com.secj3303.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.support.SessionStatus;

import com.secj3303.model.Person;
import com.secj3303.model.Sessions;

@Repository
public class SessionDaoHibernate implements SessionDao {
    @Autowired
    private org.hibernate.SessionFactory sessionFactory;



    private Session openSession() {
        return sessionFactory.openSession();
    }


    @Override
    public List<Sessions> findAllSessions(){
        Query<Sessions> query = openSession().createQuery(
            "from Sessions p", Sessions.class);
        return query.getResultList();
    }

    @Override
    public void book(int sessionId, Person user){
        
    }
}
