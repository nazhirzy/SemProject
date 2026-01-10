package com.secj3303.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.secj3303.dao.PersonDaoHibernate;
import com.secj3303.model.Person;

@Controller
public class RegisterController{
    @Autowired
    private PersonDaoHibernate personDao;

    @Autowired
    private PasswordEncoder pEncoder;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new Person());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute Person user, Model model) {
        if (personDao.findByUsername(user.getName()) != null) {
            model.addAttribute("error", "Username is taken");
            return "auth/register";
        }
        user.setPassword(pEncoder.encode(user.getPassword()));
        user.setRole("ROLE_MEMBER");
        personDao.save(user);
        return "redirect:/login";
    }
    }

