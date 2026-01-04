package com.secj3303.model;

import java.time.Year;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int yob;

    @Column(nullable = false)
    private String role;

    public Person(){}

    public Person(int id, String name, String password, int yob, double weight, double height){
        this.id = id;
        this.name = name;
        this.password = password;
        this.yob = yob;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getYob() {
        return yob;
    }

    public int getAge() {
        int year = Year.now().getValue();
        return year - yob;
    }


 public void setId(int id) {
     this.id = id;
 }


 public void setName(String name) {
     this.name = name;
 }

 public void setPassword(String password) {
     this.password = password;
 }


 public void setYob(int yob) {
     this.yob = yob;
 }

 public String getRole() {
     return role;
 }

 public void setRole(String role) {
     this.role = role;
 }
}

