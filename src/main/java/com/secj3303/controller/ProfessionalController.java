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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.secj3303.dao.ModuleDaoHibernate;
import com.secj3303.dao.PersonDaoHibernate;
import com.secj3303.dao.SessionDaoHibernate;
import com.secj3303.dao.SurveyDao;
import com.secj3303.model.Person;
import com.secj3303.model.Module;
import com.secj3303.model.SessionStatus;
import com.secj3303.model.Sessions;
import com.secj3303.model.Survey;
import com.secj3303.model.SurveyQuestion;

@Controller
@RequestMapping("/professional")
public class ProfessionalController {
    @Autowired
    private SessionDaoHibernate sessionDao;

    @Autowired
    private PersonDaoHibernate pDao;

    @Autowired
    private SurveyDao sDao;

    @Autowired
    private ModuleDaoHibernate moduleDao;

    @GetMapping("/home")
    public String dashboard(HttpSession session, Model model){
        return "homepage/professional-home";
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

    // SURVEY

    @GetMapping("/survey/create")
    public String getCreateForm(Model model) {
        model.addAttribute("survey", new Survey());
        return "survey/create-survey";
    }

    @PostMapping("/survey/save")
    public String saveSurvey(@ModelAttribute Survey survey) {
        sDao.saveSurvey(survey);
        return "redirect:/professional/survey/edit/" + survey.getId() + "/questions";
    }

    @GetMapping("/survey/edit/{id}/questions")
    public String showAddQuestions(@PathVariable int id, Model model) {
        Survey survey = sDao.findSurveyById(id);
        model.addAttribute("survey", survey);
        model.addAttribute("newQuestion", new SurveyQuestion());
        return "survey/manage-questions";
    }

    @PostMapping("/survey/edit/{id}/questions/add")
    public String addQuestion(@PathVariable int id, @ModelAttribute SurveyQuestion question) {
        Survey survey = sDao.findSurveyById(id);
        question.setSurvey(survey);
        survey.getQuestions().add(question);
        sDao.saveQuestion(question);
        return "redirect:/professional/survey/edit/" + id + "/questions";
    }

    @GetMapping("/survey/manage")
    public String manageSurveys(Model model) {
        List<Survey> surveys = sDao.findAllSurveys();
        model.addAttribute("surveys", surveys);
        return "survey/manage-list";
    }

    @PostMapping("/survey/delete/{id}")
    public String deleteSurvey(@PathVariable int id) {
        sDao.deleteSurvey(id);
        return "redirect:/professional/survey/manage";
    }

    // Resource Module 

    @GetMapping("/module")
public String listModules(Model model) {
    try {
        List<Module> modules = moduleDao.findAll();
        System.out.println("Fetched modules: " + modules.size());
        for (Module m : modules) {
            System.out.println("Module: " + m.getTitle());
        }
        model.addAttribute("modules", modules);
        return "module/professional-modules";
    } catch (Exception e) {
        // Print full stack trace so we can see the root cause
        e.printStackTrace();
        // Optionally add an error message to the model
        model.addAttribute("errorMessage", "Error loading modules: " + e.getMessage());
        return "module/professional-modules"; // still returns the page so you see the error
    }
}



    @GetMapping("/module/create")
    public String showCreateForm(Model model) {
        model.addAttribute("module", new Module());
        return "module/create-module";
    }

    @PostMapping("/module/create")
    public String createModule(@ModelAttribute Module module) {
        moduleDao.save(module);
        return "redirect:/professional/module";
    }

    @GetMapping("/module/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Module module = moduleDao.findById(id);
        if (module == null) {
            return "redirect:/professional/module";
        }
        model.addAttribute("module", module);
        return "module/edit-module";
    }

    @PostMapping("/module/edit/{id}")
    public String editModule(@PathVariable int id, @ModelAttribute Module module) {
        module.setId(id);
        moduleDao.save(module);
        return "redirect:/professional/module";
    }

    @GetMapping("/module/delete/{id}")
    public String deleteModule(@PathVariable int id) {
        Module module = moduleDao.findById(id);
        if (module != null) {
            moduleDao.delete(module);
        }
        return "redirect:/professional/module";
    }
}
