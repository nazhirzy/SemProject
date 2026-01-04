package com.secj3303.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.secj3303.dao.BmiDaoHibernate;
import com.secj3303.dao.EnrollmentDaoHibernate;
import com.secj3303.dao.PersonDaoHibernate;
import com.secj3303.dao.ProgramDaoHibernate;
import com.secj3303.model.Bmi;
import com.secj3303.model.Enrollment;
import com.secj3303.model.Person;
import com.secj3303.model.Program;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private PersonDaoHibernate personDao;

    @Autowired
    private ProgramDaoHibernate programDao;

    @Autowired
    private EnrollmentDaoHibernate eDao;

    @Autowired
    private BmiDaoHibernate bDao;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model){
        if (!"member".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        String name = (String) session.getAttribute("name");
        model.addAttribute("name", name);

        return "member-dashboard";
    }

    @GetMapping("/programs")
    public String memberProgram(HttpSession session, Model model){
        if (!"member".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        List<Program> plist = programDao.findAll();
        model.addAttribute("programs", plist);
        return "member-program-list";
    }

    @GetMapping("/bmi-form")
    public String bmiForm(HttpSession session, Model model){
        if (!"member".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        model.addAttribute("bmi", new Bmi()); 
        return "bmi-form";
    }

    @PostMapping("/bmi-form")
    public String submitBmi(@ModelAttribute Bmi bmi, HttpSession session, Model model) {
        int id= (Integer) session.getAttribute("id");
        Person m = personDao.findById(id);

        bmi.setMember(m);

        double bmiValue = bmi.getWeight() / (bmi.getHeight() * bmi.getHeight());
        bmi.setBmiValue(bmiValue);
        String bmiCat = bmi.getBmiCategory(bmiValue);
        bmi.setBmiCat(bmiCat);

        bDao.save(bmi);

        model.addAttribute("bmiValue", bmiValue);
        model.addAttribute("bmiCat", bmiCat);
        return "bmi-result";
    }

    @GetMapping("/bmi-history")
    public String showBmiHistory(HttpSession session, Model model) {
        int id= (Integer) session.getAttribute("id");
        Person m = personDao.findById(id);

        List<Bmi> history = bDao.findById(m.getId());
        model.addAttribute("bmiList", history);
        return "bmi-history";
    }

    @PostMapping("/enroll")
    public String enrollProgram(HttpSession session, @RequestParam int programId){
        int memberId = (Integer) session.getAttribute("id");
        Program p = programDao.findById(programId);
        Person m = personDao.findById(memberId);

        Enrollment e = new Enrollment(m, p);
        eDao.save(e);
        return "redirect:/member/my-programs";
    }

    @GetMapping("/my-programs")
    public String viewPrograms(HttpSession session, Model model){
        int memberId = (Integer) session.getAttribute("id");
        List<Enrollment> enrollments = eDao.findByMemberId(memberId);
        model.addAttribute("enrollments", enrollments);

        return "member-programs";
    }

}