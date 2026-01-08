package com.secj3303.dao;

import java.util.List;


import com.secj3303.model.Person;
import com.secj3303.model.Sessions;

public interface SessionDao {
    List<Sessions> findAllSessions();
    void book(int sessionId, Person user);
}
