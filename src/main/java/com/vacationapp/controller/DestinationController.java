package com.vacationapp.controller;

import com.vacationapp.entity.DestinationInfo;
import com.vacationapp.service.DestinationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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

}
