package com.secj3303.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.secj3303.dao.ProgramDaoHibernate;
import com.secj3303.model.Program;

@Controller
@RequestMapping("/program")
public class ProgramController {
    @Autowired
    private ProgramDaoHibernate pDao;

    @RequestMapping("/getall")
    public String getall(Model model) {
        List<Program> plist = pDao.findAll();
        model.addAttribute("programs", plist);
        return "program-list";
    }

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("program", new Program());
        return "program-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("program") Program program){
        pDao.save(program);
        return "redirect:/program/getall";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam int id){
        pDao.delete(id);
        return "redirect:/program/getall";
    }

    @RequestMapping("/find")
    public String find(@RequestParam int id, Model model){
        Program program = pDao.findById(id);

        model.addAttribute("program", program);
        return "program-detail";
    }
}
