package com.secj3303.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.secj3303.dao.PersonDaoHibernate;
import com.secj3303.dao.SessionDaoHibernate;
import com.secj3303.model.Person;
import com.secj3303.model.Sessions;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private SessionDaoHibernate sessionDao;

    @Autowired
    private PersonDaoHibernate pDao;

    @GetMapping("/home")
    public String dashboard(HttpSession session, Model model){
        return "member-home";
    }

    // SESSIONS PAGE
    @GetMapping("/session")
    public String listSessions(Model model) {
        List<Sessions> sessions = sessionDao.findAllSessions();
        model.addAttribute("sessions", sessions);
        return "session/sessions";
    }

    @PostMapping("/sessions/book/{id}")
    public String bookSession(@PathVariable("id") int sessionId, Principal principal) {

        String name = principal.getName();


        Person user = pDao.findByUsername(name);
        sessionDao.book(sessionId, user);

        return "redirect:/member/session";
    }
    
    @GetMapping("/my-sessions")
    public String showMySessions(Model model, Principal principal) {
        String username = principal.getName();
        Person currentUser = pDao.findByUsername(username);

        List<Sessions> mySessions = sessionDao.findByBookedById(currentUser.getId());

        model.addAttribute("mySessions", mySessions);
        
        return "session/my-sessions";
    }

    @PostMapping("/my-sessions/unbook/{id}")
    public String unbookSession(@PathVariable("id") int id) {
        sessionDao.unbook(id);
        return "redirect:/member/my-sessions"; // Redirect back to their list
    }
}