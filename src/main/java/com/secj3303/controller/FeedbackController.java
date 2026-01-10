package com.secj3303.controller;

import com.secj3303.dao.FeedbackDao;
import com.secj3303.dao.PersonDao;
import com.secj3303.model.Feedback;
import com.secj3303.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private PersonDao personDao;

    // Member View
    @GetMapping("/member/feedback")
    public String showFeedbackForm(Model model) {
        model.addAttribute("feedback", new Feedback());
        return "feedback_form";
    }

    // Process Submission
    @PostMapping("/member/feedback/submit")
    public String submitFeedback(@ModelAttribute("feedback") Feedback feedback, Principal principal) {
        // Get user from the DB
        Person loggedInUser = personDao.findByUsername(principal.getName());
        
        if (loggedInUser != null) {
            feedback.setPerson(loggedInUser);
            feedbackDao.save(feedback);
        }
        return "redirect:/profile?feedbackSuccess";
    }

    // Admin View
    @GetMapping("/admin/feedback")
    public String listFeedback(Model model) {
        // Authorization
        List<Feedback> feedbackList = feedbackDao.findAll();
        model.addAttribute("feedbacks", feedbackList);
        return "admin_feedback";
    }
}