package com.secj3303.controller;


import com.secj3303.dao.EnrollmentDaoHibernate;
import com.secj3303.dao.PersonDao;
import com.secj3303.dao.PersonDaoHibernate;
import com.secj3303.dao.ProgramDaoHibernate;
import com.secj3303.model.Enrollment;
import com.secj3303.model.Person;
import com.secj3303.model.Program;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/trainer")
public class TrainerController {

    @Autowired
    private ProgramDaoHibernate programDao;

    @Autowired
    private PersonDaoHibernate personDao;

    @Autowired
    private EnrollmentDaoHibernate eDao;


    private boolean isTrainer(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "trainer".equals(role);
    }
    
    @GetMapping("/dashboard")
    public String trainerDashboard(HttpSession session) {
        if (!isTrainer(session)) {
            return "redirect:/login";
        }
        return "trainer-dashboard";
    }


    @GetMapping("/plans")
    public String listPlans(HttpSession session, Model model) {
        if (!isTrainer(session)) return "redirect:/login";

        List<Program> plist = programDao.findAll();
        model.addAttribute("programs", plist);
        return "program-list";
    }

    @GetMapping("/add")
    public String add(HttpSession session, Model model){
        if (!isTrainer(session)) return "redirect:/login";
        model.addAttribute("program", new Program());
        return "program-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("program") Program program){
        programDao.save(program);
        return "redirect:/program/getall";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id){
        programDao.delete(id);
        return "redirect:/program/getall";
    }


    @GetMapping("/assign")
    public String showAssignmentForm(Model model) {
        List<Person> allMembers = personDao.findAllMembers(); 
        List<Program> allPrograms = programDao.findAll(); 
        model.addAttribute("allMembers", allMembers);
        model.addAttribute("allPrograms", allPrograms);

        return "assign-program";
    }

    @PostMapping("/assignProgram")
    public String assignProgram(HttpSession session, @RequestParam int memberId, @RequestParam int programId) {
        if (!isTrainer(session)) return "redirect:/login";

        Program p = programDao.findById(programId);
        Person m = personDao.findById(memberId);
        
        if (p == null || m == null) {
            System.err.println("Error: Program or Member not found.");
            return "redirect:/trainer/programs-list?error=notfound";
        }

        Enrollment e = new Enrollment(m, p);
        eDao.save(e);

        return "redirect:/trainer/dashboard";
    }



}
