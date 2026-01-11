package com.secj3303.dao;

import com.secj3303.model.Feedback;
import com.secj3303.model.Person;

import java.util.List;

public interface FeedbackDao {
    void save(Feedback feedback);
    List<Feedback> findAll();
    Feedback findById(int id);
    public void deleteFeedbackByMember(Person member);
}