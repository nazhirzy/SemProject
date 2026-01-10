package com.secj3303.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "responses")
public class SurveyResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int totalScore = 0;

    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    
    public SurveyResponse(){}

    public SurveyResponse(Survey survey, Person person, int initialScore) {
        this.survey = survey;
        this.person = person;
        this.totalScore = initialScore;
    }
    public int getId() { return id; }
    public int getTotalScore() { return totalScore; }
    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }
    public Survey getSurvey() { return survey; }
    public void setSurvey(Survey survey) { this.survey = survey; }
    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

}
