package com.secj3303.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "survey")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<SurveyQuestion> questions = new ArrayList<>();

    public Survey(){}

    public Survey(String title){
        this.title = title;
    }

    public int getId() {return id;}
    public String getTitle() {return title;}
    public List<SurveyQuestion> getQuestions() {return questions;}

    public void setTitle(String title) {this.title = title;}
}