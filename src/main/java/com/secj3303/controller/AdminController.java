package com.secj3303.controller;

import com.secj3303.dao.PersonDaoHibernate;
import com.secj3303.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PersonDaoHibernate personDao;

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin-dashboard"; 
    }

    // View Members
    @GetMapping("/members")
    public String listMembers(Model model) {
        List<Person> members = personDao.findAllMembers();
        model.addAttribute("users", members);
        return "admin-user-list";
    }

    // Deleting a member
    @GetMapping("/members/delete/{id}")
    public String deleteMember(@PathVariable("id") int id) {
        personDao.delete(id);
        return "redirect:/admin/members?deleted";
    }

    // View Professionals
    @GetMapping("/professionals")
    public String listProfessionals(Model model) {
        List<Person> professionals = personDao.findAllProfessionals();
        model.addAttribute("profs", professionals);
        return "admin-prof-list";
    }
}