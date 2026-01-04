package com.secj3303.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bmi")
public class Bmi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Person member;

    private double weight;
    private double height;
    private double bmiValue;

    @Column(name = "bmi_category")
    private String bmiCat;

    private LocalDateTime date = LocalDateTime.now();

    public Bmi(){}

    public Bmi(Person member, double weight, double height){
        this.member = member;
        this.weight = weight;
        this.height = height;
        this.bmiValue = weight / (height * height);
        this.bmiCat = this.getBmiCategory(bmiValue);
    }

    public String getBmiCategory(double bmiValue) {
        if (bmiValue < 18.5) {
            return "Underweight";
        } else if (bmiValue >= 18.5 && bmiValue < 25) {
            return "Normal Weight";
        } else if (bmiValue >= 25 && bmiValue < 30) {
            return "Overweight";
        } else {
            return "Obesity";
        }
    }

    public Integer getId() {
        return id;
    }

    public Person getMember() {
        return member;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getBmiValue() {
        return bmiValue;
    }

    public String getBmiCat() {
        return bmiCat;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setBmiCat(String bmiCat) {
        this.bmiCat = bmiCat;
    }

    public void setBmiValue(double bmiValue) {
        this.bmiValue = bmiValue;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMember(Person member) {
        this.member = member;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}
