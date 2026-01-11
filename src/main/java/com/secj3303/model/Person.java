package com.secj3303.model;

import javax.persistence.*;

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

    
    @Column(nullable = true)
    private String specialization;

    @Column(nullable = true)
    private String credentials;

    @Column(length = 500)
    private String description; // Bio idk


    

    public Person(){}

    public Person(String name, String password, String role, String specialization, String credentials){
        this.name = name;
        this.password = password;
        this.role = role;
        this.specialization = specialization;
        this.credentials = credentials;
    }

    // Existing Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // New Getters and Setters
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public String getCredentials() { return credentials; }
    public void setCredentials(String credentials) { this.credentials = credentials; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

}