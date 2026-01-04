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
    
    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login"; 
        }
        return "admin-dashboard"; 
    }

    @GetMapping("/users")
    public String listUsers(HttpSession session, Model model){
        if (!isAdmin(session)) return "redirect:/login";

        List<Person> users = personDao.findAllUsers();
        model.addAttribute("users", users);
        return "admin-user-list";

    }



}