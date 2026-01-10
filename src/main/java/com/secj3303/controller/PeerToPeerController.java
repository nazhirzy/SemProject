package com.secj3303.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/chat")  // Updated for general access
public class PeerToPeerController {
    

    @GetMapping("/peer-to-peer")
    public String peerToPeerPage(HttpSession session, Model model) {
        List<String> messages = (List<String>) session.getAttribute("chatMessages");
        if (messages == null) {
            messages = new ArrayList<>();
        }
        model.addAttribute("messages", messages);
        return "peer-to-peer";
    }

    @PostMapping("/peer-to-peer")
    public String sendMessage(@RequestParam String message, HttpSession session, Principal principal) {
        List<String> messages = (List<String>) session.getAttribute("chatMessages");
        if (messages == null) {
            messages = new ArrayList<>();
        }

        String name = principal.getName();



        String Timestamp = java.time.LocalTime.now().withNano(0).toString();
        message = "[" + Timestamp + "] " + name + ": " + message;
        messages.add(message);
        session.setAttribute("chatMessages", messages);
        return "redirect:/chat/peer-to-peer";
    }
}
