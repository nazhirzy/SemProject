package com.secj3303.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.secj3303.model.Module;

@Controller
public class ResourceController {

    // For demo purposes, creating sample modules
    private List<Module> getSampleModules() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module("Depression",
            "Depression is a common mental health disorder characterized by persistent sadness, loss of interest, and interference with daily life including sleep, appetite, and fatigue. It is a treatable condition resulting from biological, psychological, and social factors.\n\nKey characteristics include:\n• Persistent sadness: Intense feelings of emptiness lasting at least two weeks\n• Loss of interest: Diminished ability to find pleasure in once-enjoyable activities\n• Physical symptoms: Changes in sleep patterns, appetite, and energy levels",
            "/resources/images/depression.svg", "depression"));
        modules.add(new Module("Sleep Deprivation",
            "Sleep deprivation occurs when an individual gets less sleep than they need to function properly. It can lead to impaired cognitive function, mood changes, and various health issues.\n\nCommon effects include:\n• Reduced alertness and concentration\n• Memory problems\n• Mood disturbances and irritability\n• Weakened immune system",
            "/resources/images/sleep-deprivation.svg", "sleep-deprivation"));
        modules.add(new Module("PTSD",
            "Post-traumatic stress disorder (PTSD) is a mental health condition triggered by experiencing or witnessing a terrifying event. Symptoms may include flashbacks, nightmares, and severe anxiety.\n\nKey symptoms include:\n• Intrusive memories or flashbacks\n• Nightmares and sleep disturbances\n• Avoidance of trauma reminders\n• Hypervigilance and emotional numbness",
            "/resources/images/ptsd.svg", "ptsd"));
        modules.add(new Module("Anxiety",
            "Anxiety disorders involve excessive worry and fear that can interfere with daily activities. Common types include generalized anxiety disorder, panic disorder, and social anxiety.\n\nCommon symptoms include:\n• Excessive worry and fear\n• Restlessness and feeling on edge\n• Rapid heartbeat and sweating\n• Avoidance of anxiety-provoking situations",
            "/resources/images/anxiety.svg", "anxiety"));
        return modules;
    }

    @GetMapping("/resources")
    public String listResources(HttpSession session, Model model) {
        // Check if user is logged in
        if (session.getAttribute("role") == null) {
            return "redirect:/login";
        }

        List<Module> modules = getSampleModules();
        model.addAttribute("modules", modules);
        return "resources";
    }

    @GetMapping("/resources/{category}")
    public String resourceDetail(@PathVariable String category, HttpSession session, Model model) {
        // Check if user is logged in
        if (session.getAttribute("role") == null) {
            return "redirect:/login";
        }

        List<Module> modules = getSampleModules();
        Module module = modules.stream()
            .filter(m -> m.getCategory().equals(category))
            .findFirst()
            .orElse(null);

        if (module == null) {
            return "redirect:/resources";
        }

        model.addAttribute("module", module);
        return "resource-detail";
    }
}