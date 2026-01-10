package com.secj3303.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SurveyQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Column(nullable = false)
    private String questionText;

    public SurveyQuestion() {}

    public SurveyQuestion(String questionText, Survey survey) {
        this.questionText = questionText;
        this.survey = survey;
    }

    public int getId() {return id;}
    public Survey getSurvey() {return survey;}
    public String getQuestionText() {return questionText;}

    public void setSurvey(Survey survey) {this.survey = survey;}
    public void setQuestionText(String questionText) {this.questionText = questionText;}
}
