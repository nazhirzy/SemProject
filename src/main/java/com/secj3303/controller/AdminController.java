package com.secj3303.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.secj3303.dao.PersonDaoHibernate;
import com.secj3303.model.Person;
import com.secj3303.services.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private PersonDaoHibernate personDao;

    @Autowired
    private AdminService adminService;


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

    @GetMapping("/members/delete/{id}")
    public String deleteMember(@PathVariable int id) {
        adminService.deleteMember(id);
        return "redirect:/admin/members";
    }


    @GetMapping("/professionals")
    public String listProfessionals(Model model) {
        List<Person> professionals = personDao.findAllProfessionals();
        model.addAttribute("profs", professionals);
        return "admin/admin-prof-list";
    }

}