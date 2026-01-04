package com.secj3303.dao;

import java.util.List;

import com.secj3303.model.Bmi;

public interface BmiDao {
    void save(Bmi bmi);
    List<Bmi> findById(int id);
}
