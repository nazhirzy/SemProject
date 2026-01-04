package com.secj3303.controller;

import com.secj3303.dao.PersonDaoHibernate;
import com.secj3303.dao.ProgramDao;
import com.secj3303.model.Person;
import com.secj3303.model.Program;
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
    private ProgramDao programDAO;

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
    @GetMapping("/users/delete")
    public String deleteUser(@RequestParam("userId") int id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";

        personDao.delete(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/programs")
    public String listPrograms(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";

        List<Program> programs = programDAO.findAll();
        model.addAttribute("programs", programs);
        return "admin-program-list";
    }

    @GetMapping("/programs/delete")
    public String deleteProgram(@RequestParam("programId") int id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";

        programDAO.delete(id);
        return "redirect:/admin/programs";
    }


}