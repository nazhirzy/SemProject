package com.secj3303.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.secj3303.dao.PersonDaoHibernate;
import com.secj3303.model.Person;

@Controller
public class LoginController {
    @Autowired
    private PersonDaoHibernate pDao;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String name, @RequestParam String password, HttpSession session, Model model){
        String role = null;
        Person p = pDao.findByUsernameAndPassword(name, password);

        if (p == null) {
            model.addAttribute("error", "Invalid login");
            return "login";
        }

        if (p.getRole().equals("member")) {
            role = "member";
        } else if (p.getRole().equals("trainer")) {
            role = "trainer";
        } else if (p.getRole().equals("admin")) {
            role = "admin";
        }

        session.setAttribute("name", name);
        session.setAttribute("role", role);
        session.setAttribute("id", p.getId());

        if (role.equals("member")) {
            return "redirect:/member/dashboard";
        } else if (role.equals("trainer")) {
            return "redirect:/trainer/dashboard";
        } else if (role.equals("admin")) {
            return "redirect:/admin/dashboard";
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
