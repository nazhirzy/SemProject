package com.secj3303.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.secj3303.dao.FeedbackDao;
import com.secj3303.dao.PersonDao;
import com.secj3303.dao.SessionDao;
import com.secj3303.dao.SurveyDao;
import com.secj3303.model.Person;

@Service
public class AdminService {

    @Autowired private PersonDao personDao;
    @Autowired private FeedbackDao feedbackDao;
    @Autowired private SurveyDao responseDao;
    @Autowired private SessionDao sessionDao;

    @Transactional
    public void deleteMember(int memberId) {
        Person member = personDao.findById(memberId);

        feedbackDao.deleteFeedbackByMember(member);
        responseDao.deleteResponseByMember(member);
        sessionDao.clearBooking(member);
        personDao.delete(member);
    }
}

