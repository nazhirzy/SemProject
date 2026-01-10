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

import com.secj3303.dao.PersonDaoHibernate;
import com.secj3303.model.Person;

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/home")
    public String dashboard(HttpSession session, Model model){
        return "member-home";
    }


}