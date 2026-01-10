package com.secj3303.controller;

import com.secj3303.dao.PersonDao;
import com.secj3303.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private PersonDao personDao;

    @GetMapping
    public String viewProfile(Principal principal, Model model) {
        Person user = personDao.findByUsername(principal.getName());
        model.addAttribute("user", user);

        // Redirect based on role
        return switch (user.getRole()) {
            case "ROLE_ADMIN" -> "profile-admin";
            case "ROLE_PROFESSIONAL" -> "profile-professional";
            default -> "profile-member";
        };
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("user") Person updatedUser, Principal principal) {
        Person currentUser = personDao.findByUsername(principal.getName());
        
        // Update common fields
        currentUser.setName(updatedUser.getName());
        
        // Update Professional specific fields
        if ("ROLE_PROFESSIONAL".equals(currentUser.getRole())) {
            currentUser.setSpecialization(updatedUser.getSpecialization());
            currentUser.setCredentials(updatedUser.getCredentials());
        }
        
        personDao.save(currentUser);
        return "redirect:/profile?success";
    }

    @GetMapping("/edit")
public String showEditForm(Principal principal, Model model) {
    Person user = personDao.findByUsername(principal.getName());
    model.addAttribute("user", user);
    return "profile-edit";
}

@PostMapping("/edit/save")
public String saveProfile(@ModelAttribute("user") Person formUser, Principal principal) {
    // 1. Get the existing user from DB to ensure we don't lose data not in form
    Person databaseUser = personDao.findByUsername(principal.getName());

    // 2. Update common fields
    databaseUser.setName(formUser.getName());
    // Handle profile picture URL/Path here

    // 3. Update Role Specific fields
    if ("ROLE_PROFESSIONAL".equals(databaseUser.getRole())) {
        databaseUser.setSpecialization(formUser.getSpecialization());
        databaseUser.setCredentials(formUser.getCredentials());
    } else if ("ROLE_MEMBER".equals(databaseUser.getRole())) {
        databaseUser.setDescription(formUser.getDescription());
    }

    personDao.save(databaseUser);
    return "redirect:/profile?success";
}
}