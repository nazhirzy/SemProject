package com.secj3303.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "sessions")
public class Sessions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus status = SessionStatus.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "booked_id")
    private Person bookedBy;

    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private Person conductor;

    public Sessions(){}

    public Sessions(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.status = SessionStatus.AVAILABLE;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public SessionStatus getStatus() { return status; }
    public Person getBookedBy() { return bookedBy; }
    public Person getConductor() { return conductor; }


    public void setName(String name) { this.name = name; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setTime(LocalTime time) { this.time = time; }
    public void setStatus(SessionStatus status) { this.status = status; }
    public void setBookedBy(Person bookedBy) { this.bookedBy = bookedBy; }
    public void setConductor(Person conductor) { this.conductor = conductor; }
}

