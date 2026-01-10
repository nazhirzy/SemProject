package com.secj3303.dao;

import com.secj3303.model.Feedback;
import java.util.List;

public interface FeedbackDao {
    void save(Feedback feedback);
    List<Feedback> findAll();
    Feedback findById(int id);
}