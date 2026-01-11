package com.secj3303.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.secj3303.dao.PersonDaoHibernate;
import com.secj3303.dao.SessionDaoHibernate;
import com.secj3303.dao.SurveyDaoHibernate;
import com.secj3303.model.Person;
import com.secj3303.model.SurveyQuestion;
import com.secj3303.model.Sessions;
import com.secj3303.model.Survey;
import com.secj3303.model.SurveyResponse;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private SessionDaoHibernate sessionDao;

    @Autowired
    private PersonDaoHibernate pDao;

    @Autowired
    private SurveyDaoHibernate sDao;


    @GetMapping("/home")
    public String dashboard(HttpSession session, Model model){
        return "homepage/member-home";
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
        return "redirect:/member/my-sessions";
    }

    // SURVEY
    @GetMapping("/surveys")
    public String listSurveys(Model model) {
        model.addAttribute("surveys", sDao.findAllSurveys());
        return "survey/survey-list";
    }

    @GetMapping("/survey/take/{id}")
    public String takeSurvey(@PathVariable int id, Model model) {
        Survey survey = sDao.findSurveyById(id);
        model.addAttribute("survey", survey);
        return "survey/take-survey";
    }

    @PostMapping("/survey/submit/{id}")
    public String submitSurvey(@PathVariable int id, @RequestParam Map<String, String> params, Authentication auth, Model model) {
        Person user = pDao.findByUsername(auth.getName());
        Survey survey = sDao.findSurveyById(id);
        
        int totalScore = 0;
        int maxScore = 0;
        

        List<SurveyQuestion> questions = survey.getQuestions(); 
        for (SurveyQuestion q : questions) {
            maxScore += 5; 
        }
        
        for (String key : params.keySet()) {
            if (key.startsWith("question_")) {
                totalScore += Integer.parseInt(params.get(key));
            }
        }

        SurveyResponse response = new SurveyResponse(survey, user, totalScore);
        sDao.saveResponse(response);

        // Calculate percentage
        double percentage = (totalScore * 100.0) / maxScore;

        model.addAttribute("survey", survey);
        model.addAttribute("totalScore", totalScore);
        model.addAttribute("maxScore", maxScore);
        model.addAttribute("percentage", percentage);
        
        return "survey/survey-result";
    }


    //Profile

    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        String username = principal.getName();
        Person user = pDao.findByUsername(username);
        model.addAttribute("user", user);
        return "profile/member-profile";
    }

}