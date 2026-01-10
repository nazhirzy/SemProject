package com.secj3303.dao;
import java.util.List;

import java.util.List;

import com.secj3303.model.Person;

public interface PersonDao {
    List<Person> findAllMembers();
    List<Person> findAllUsers();
    List<Person> findAllProfessionals();
    Person findById(int id);
    Person findByUsernameAndPassword(String username, String password);		
    void save(Person person);
    void delete(int id);
    Person findByUsername(String username);
}
