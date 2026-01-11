package com.secj3303.dao;

import java.util.List;



import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.secj3303.model.Person;
import com.secj3303.model.Survey;

@Repository
public class PersonDaoHibernate implements PersonDao {
    @Autowired
    private org.hibernate.SessionFactory sessionFactory;



    private Session openSession() {
        return sessionFactory.openSession();
    }

    @Override
    public Person findByUsernameAndPassword(String name, String password) {
        Session session = openSession();
        return session.createQuery(
                        "FROM Person WHERE name = :name AND password = :password",
                        Person.class
                )
                .setParameter("name", name)
                .setParameter("password", password)
                .uniqueResult();
    }


    @Override
    @Transactional
    public List<Person> findAllMembers() {
        Query<Person> query = openSession().createQuery(
            "from Person p where p.role = 'ROLE_MEMBER'", Person.class);
        return query.getResultList();
    }


    @Override
    public List<Person> findAllUsers(){
        Query<Person> query = openSession().createQuery(
            "from Person p", Person.class);
        return query.getResultList();
    }


    @Override
    public void save(Person person){
        Session session = openSession();
        session.save(person);
    }

    @Override
    public Person findById(int id) {
 	
        Session session = openSession();

        return session.get(Person.class, id);
    }


    @Override
    public Person findByUsername(String name){
        Session session = openSession();

        return session.createQuery(
                        "FROM Person WHERE name = :name",
                        Person.class
                )
                .setParameter("name", name)
                .uniqueResult();
    }

    @Override
    public void delete(Person person) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(person);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    @Transactional
    public List<Person> findAllProfessionals() {
        Query<Person> query = openSession().createQuery(
        "from Person p where p.role = 'ROLE_PROFESSIONAL'", Person.class);
        return query.getResultList();
}
}
