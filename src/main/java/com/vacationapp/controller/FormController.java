package com.vacationapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormController {


    @GetMapping("/proposal")
    public String showProposalPage(){

        return "proposal";
    }

    @PostMapping("/proposal")
    public String submitForm(@RequestParam("city") String city,
                             @RequestParam("budget") String budget,
                             @RequestParam("startOfVacation") String startOfVacation,
                             @RequestParam("endOfVacation") String endOfVacation,
                             Model model) {
        // Process the form data (e.g., save it to a database)

        // Pass the form data to the view
        model.addAttribute("city", city);
        model.addAttribute("budget", budget);
        model.addAttribute("startVaca", startOfVacation);
        model.addAttribute("endVaca", endOfVacation);

        return "proposal";
    }

}
