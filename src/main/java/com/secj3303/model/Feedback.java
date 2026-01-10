package com.secj3303.model;

import javax.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 250)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    // Constructors
    public Feedback() {}

    public Feedback(String comment, Person person) {
        this.comment = comment;
        this.person = person;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }
}