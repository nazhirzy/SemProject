package com.secj3303.controller;

import com.secj3303.dao.PersonDaoHibernate;
import com.secj3303.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PersonDaoHibernate personDao;

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "admin".equals(role);
    }
    
    @GetMapping("/home")
    public String adminDashboard() {
        return "homepage/admin-home"; 
    }

    @GetMapping("/members")
    public String listMembers(Model model) {
        List<Person> members = personDao.findAllMembers();
        model.addAttribute("users", members);
        return "admin/admin-user-list";
    }

    @GetMapping("/professionals")
    public String listProfessionals(Model model) {
        List<Person> professionals = personDao.findAllProfessionals();
        model.addAttribute("profs", professionals);
        return "admin/admin-prof-list";
    }

}