package com.vacationapp.controller;

import com.vacationapp.service.DestinationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping("/all")
    public String getAll(Model model){
        model.addAttribute("destinations", destinationService.getAllDestinations());
        return "allDestinations";

    }

    @GetMapping("/countries")
    public String getCountries(Model model){
        model.addAttribute("countries", destinationService.getAllDestinationCountry());
        return "countries";
    }

    @GetMapping("/cities/{country}")
    public String getCities(Model model, @PathVariable String country) {
        model.addAttribute("cities", destinationService.getCitiesFromDatabase(country));

        return "cities";
    }

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

