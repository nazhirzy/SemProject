package com.secj3303.controller;

import com.secj3303.dao.FeedbackDao;
import com.secj3303.model.Feedback;
import com.secj3303.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackDao feedbackDao;

    // View Member Feedback Form
    @GetMapping("/feedback")
    public String showFeedbackForm(Model model, HttpSession session) {
        Person loggedInUser = (Person) session.getAttribute("user");
        if (loggedInUser == null) return "redirect:/login";
        
        model.addAttribute("feedback", new Feedback());
        return "feedback_form";
    }

    // Process Submission
    @PostMapping("/feedback/submit")
    public String submitFeedback(@ModelAttribute("feedback") Feedback feedback, HttpSession session) {
        Person loggedInUser = (Person) session.getAttribute("user");
        if (loggedInUser != null) {
            feedback.setPerson(loggedInUser);
            feedbackDao.save(feedback);
        }
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