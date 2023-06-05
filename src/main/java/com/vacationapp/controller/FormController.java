package com.vacationapp.controller;

import com.vacationapp.weatherApi.Weather;
import com.vacationapp.weatherApi.WeatherAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormController {

    @Value("${api.key}")
    private String apiKey;

    @GetMapping("/proposal")
    public String showProposalPage(){

        return "proposal";
    }

    @PostMapping("/proposal")
    public String submitFormAndGetProposal(@RequestParam("city") String city,
                                           @RequestParam("budget") String budget,
                                           @RequestParam("startOfVacation") String startOfVacation,
                                           @RequestParam("endOfVacation") String endOfVacation,
                                           @RequestParam("departureCity") String departureCity,
                                           @RequestParam("departureCountry") String departureCountry,
                                           Model model) {

        WeatherAPI weatherAPI = new WeatherAPI(apiKey);
        Weather currentWeather = weatherAPI.getWeather(city);

        model.addAttribute("startingBudget", budget);
        model.addAttribute("temperature", currentWeather.getTemperature());
        model.addAttribute("feelsLike", currentWeather.getFeelsLike());
        model.addAttribute("humidity", currentWeather.getHumidity());
        model.addAttribute("description", currentWeather.getDescription());
        model.addAttribute("windSpeed", currentWeather.getWindSpeed());
        model.addAttribute("country", currentWeather.getCountry());

        //Chosen city name, but lowercase (plane ticket link uses lowercase)
        String cityLowerCase = city.toLowerCase();
        model.addAttribute("city", cityLowerCase);

        model.addAttribute("startOfVacation", startOfVacation);
        model.addAttribute("endOfVacation", endOfVacation);

        //budget needs additional text after value, otherwise search doesn't filter by price
        String budgetWithText = "price=EUR-min-" + budget + "-1";
        model.addAttribute("budget", budgetWithText);

        departureCity = departureCity.toLowerCase();
        String departureCountryLowerCase = departureCountry.toLowerCase();

        model.addAttribute("departureCity", departureCity);
        model.addAttribute("departureCountry", departureCountryLowerCase);

        return "proposal";
    }

}
