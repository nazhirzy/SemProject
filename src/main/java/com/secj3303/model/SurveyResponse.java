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
    private int answerValue;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private SurveyQuestion question;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    
    public SurveyResponse(){}

    public SurveyResponse(int value, SurveyQuestion question, Person person) {
    this.answerValue = value;
    this.question = question;
    this.person = person;
    }

    public int getId() {return id;}
    public int getAnswerValue() {return answerValue;}
    public SurveyQuestion getQuestion() {return question;}
    public Person getPerson() {return person;}

    public void setId(int id) {this.id = id;}
    public void setAnswerValue(int answerValue) {this.answerValue = answerValue;}
    public void setPerson(Person person) {this.person = person;}
    public void setQuestion(SurveyQuestion question) {this.question = question;}

}
