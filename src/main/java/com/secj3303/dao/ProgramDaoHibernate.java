package com.secj3303.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.secj3303.model.Program;
import java.util.List;

@Repository
public class ProgramDaoHibernate implements ProgramDao {

    @Autowired
    private org.hibernate.SessionFactory sessionFactory;

    @Autowired
    private EnrollmentDaoHibernate eDao;

    // For the time being/beginning: we explicitly open/close session
    private Session openSession() {
        return sessionFactory.openSession();
    }

    @Override
    public List<Program> findAll() {
        Session session = sessionFactory.openSession();
        List<Program> list = session
                .createQuery("FROM Program", Program.class) //this is HQL - Hibernate Query Language
                .list();
        session.close();
        return list;
    }

   @Override
    public Program findById(Integer id) {
 	//complete this
    Session session = openSession();

    return session.get(Program.class, id);
}
   @Override
    public void save(Program program) {
	//complete this
    Session session = openSession();
    session.save(program);
}

@Override
public void delete(Integer id) {
    if (eDao == null) {
        System.err.println("CRITICAL ERROR: EnrollmentDao is NULL. Check Spring configuration.");
        return; // Prevents further execution if the dependency failed to inject
    }
    
    // Log the action to see if it is reached
    System.out.println("Attempting to clean up enrollments for program: " + id);
    eDao.deleteEnrollmentsByProgramId(id);
    eDao.deleteEnrollmentsByProgramId(id); 

    Session session = openSession();
    session.beginTransaction();
    
    try {
        // 2. Now, retrieve and delete the Program.
        Program programToDelete = session.get(Program.class, id);
        
        if (programToDelete != null) {
            session.delete(programToDelete);
        }
        
        session.getTransaction().commit();
        
    } catch (Exception e) {
        if (session.getTransaction() != null) {
            session.getTransaction().rollback();
        }
        System.err.println("Final program delete failed for ID " + id);
        e.printStackTrace();
        
    } finally {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}
} //end class

