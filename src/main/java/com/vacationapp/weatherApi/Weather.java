package com.vacationapp.weatherApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Weather {
    private double temperature;
    private double feelsLike;
    private int humidity;
    private String description;
    private double windSpeed;
    private String country;
}