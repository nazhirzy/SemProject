package com.secj3303.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.secj3303.dao.FeedbackDao;
import com.secj3303.dao.PersonDao;
import com.secj3303.model.Feedback;
import com.secj3303.model.Person;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private PersonDao personDao;

    // View Member Feedback Form
    @GetMapping("/feedback")
    public String showFeedbackForm(Model model, HttpSession session) {
        
        model.addAttribute("feedback", new Feedback());
        return "feedback_form";
    }

    // Process Submission
    @PostMapping("/feedback/submit")
    public String submitFeedback(@ModelAttribute("feedback") Feedback feedback, HttpSession session, Principal principal) {
        String username = principal.getName();
        Person user = personDao.findByUsername(username);

        feedback.setPerson(user);
        feedbackDao.save(feedback);
        return "redirect:/feedback?success";
    }

    // Admin View
    @GetMapping("/admin/feedback")
    public String listFeedback(Model model, HttpSession session) {
        Person loggedInUser = (Person) session.getAttribute("user");
        // Simple role check
        if (loggedInUser == null || !"admin".equals(loggedInUser.getRole())) {
            return "redirect:/login";
        }
        
        List<Feedback> feedbackList = feedbackDao.findAll();
        model.addAttribute("feedbacks", feedbackList);
        return "admin_feedback";
    }
}