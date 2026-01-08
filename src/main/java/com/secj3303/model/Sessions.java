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


@Entity
@Table(name = "sessions")
public class Sessions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Enumerated(EnumType.STRING)
    private SessionStatus status = SessionStatus.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person bookedBy;

    public Sessions(){}

    public String getName() { return name; }

    public int getId() { return id; }
    
    public SessionStatus getStatus() { return status; }

    public LocalTime getTime() { return time; }

    public LocalDate getDate() { return date; }

    public Person getBookedBy() { return bookedBy; }

    public void setBookedBy(Person bookedBy) { this.bookedBy = bookedBy; }

    public void setId(int id) { this.id = id; }

    public void setDate(LocalDate date) { this.date = date; }

    public void setName(String name) { this.name = name; }

    public void setStatus(SessionStatus status) { this.status = status; }

    public void setTime(LocalTime time) {this.time = time; }
}

enum SessionStatus {
    AVAILABLE, PENDING, BOOKED, COMPLETED, CANCELLED
}
