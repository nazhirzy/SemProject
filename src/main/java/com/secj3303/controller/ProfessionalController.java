package com.secj3303.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.secj3303.dao.PersonDaoHibernate;
import com.secj3303.dao.SessionDaoHibernate;
import com.secj3303.model.Person;
import com.secj3303.model.SessionStatus;
import com.secj3303.model.Sessions;

@Controller
@RequestMapping("/professional")
public class ProfessionalController {
    @Autowired
    private SessionDaoHibernate sessionDao;

    @Autowired
    private PersonDaoHibernate pDao;

    @GetMapping("/home")
    public String dashboard(HttpSession session, Model model){
        return "professional-home";
    }

    // SESSION STUFF
    @GetMapping("/session/create")
    public String getCreateSessionForm(Model model) {
        Sessions session = new Sessions();
        session.setDate(LocalDate.now());
        session.setTime(LocalTime.now());
        model.addAttribute("session", new Sessions());
        return "session/create-session";
    }

    @PostMapping("/session/create")
    public String createSession(@ModelAttribute("session") Sessions session, Principal principal) {
        String username = principal.getName();
        Person professional = pDao.findByUsername(username);

        session.setStatus(SessionStatus.AVAILABLE);
        session.setConductor(professional);

        sessionDao.save(session);

        return "redirect:/professional/home";
    }
    @GetMapping("/session/my-schedule")
    public String showConductorSessions(Model model, Principal principal) {
       
        String username = principal.getName();
        Person professional = pDao.findByUsername(username);

        List<Sessions> mySchedule = sessionDao.findByConductorId(professional.getId());

    
        model.addAttribute("schedule", mySchedule);
        
        return "session/my-schedule";
    }
}
