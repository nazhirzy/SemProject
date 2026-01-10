package com.secj3303.dao;

import java.util.List;

import com.secj3303.model.Survey;

public interface SurveyDao {
    List<Survey> findAllSurveys();
    Survey findSurveyById();
}
