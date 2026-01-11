package com.secj3303.dao;

import java.util.List;


import com.secj3303.model.Person;
import com.secj3303.model.Sessions;
import com.secj3303.model.SessionStatus;

public interface SessionDao {
    List<Sessions> findAllSessions();
    void book(int sessionId, Person user);
    void unbook(int sessionId);
    void save(Sessions session);
    List<Sessions> findSessionsByStatus(SessionStatus status);
    List<Sessions> findByBookedById(int userId);
    List<Sessions> findByConductorId(int userId);
    void clearBooking(Person member);
}
