package com.secj3303.model;


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
    private String role;


    public Person(){}

    public Person(String name, String password, String role){
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public String getPassword() {return password;}
    public void setId(int id) {this.id = id;}
    public String getRole() {return role;}

    public void setName(String name) {this.name = name;}
    public void setPassword(String password) {this.password = password;}
    public void setRole(String role) {this.role = role;}

}

