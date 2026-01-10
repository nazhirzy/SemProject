package com.secj3303.dao;
import java.util.List;

import java.util.List;

import com.secj3303.model.Person;

public interface PersonDao {
    List<Person> findAllMembers();
    public List<Person> findAllProfessionals();
    List<Person> findAllUsers();
    Person findById(int id);
    Person findByUsernameAndPassword(String username, String password);		
    void save(Person person);
    Person findByUsername(String username);
}
