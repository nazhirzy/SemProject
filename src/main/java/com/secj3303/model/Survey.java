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

    @Column(length = 1000) 
    private String description;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<SurveyQuestion> questions = new ArrayList<>();

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyResponse> responses = new ArrayList<>();

    public Survey(){}

    public Survey(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getId() {return id;}
    public String getTitle() {return title;}
    public List<SurveyQuestion> getQuestions() {return questions;}
    public List<SurveyResponse> getResponses() {return responses;}
    public String getDescription() { return description; }
    public int getMaxPossibleScore() {
        if (questions == null) return 0;
        return questions.size() * 5;
    }

    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) { this.description = description; }
}