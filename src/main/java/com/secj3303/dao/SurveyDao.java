package com.secj3303.dao;

import java.util.List;

import com.secj3303.model.Survey;
import com.secj3303.model.SurveyQuestion;
import com.secj3303.model.SurveyResponse;

public interface SurveyDao {
    List<Survey> findAllSurveys();
    Survey findSurveyById(int id);
    void saveSurvey(Survey survey);
    void saveQuestion(SurveyQuestion sq);
    public void deleteSurvey(int id);
    void saveResponse(SurveyResponse response);
}
