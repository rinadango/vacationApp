package com.vacationapp.weatherApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.net.URL;
import java.util.Locale;

@AllArgsConstructor
@Data
public class WeatherAPI {

    @Value("${api.key}")
    private String apiKey;

    public Weather getWeather(String city) {

        Weather currentWeather = new Weather(); //This object will store all values which we'll get from API, later we will need to use them with model.addAttribute()
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            //Geolocation API, needed to get latitude and longitude based on city name; we will use them in weather API request
            //We construct a link with city and apiKey values
            //The Hague has two words, so we use if for this

            String geolocationApiUrl;
            if (city.equals("The Hague")){geolocationApiUrl = "https://api.openweathermap.org/geo/1.0/direct?q=The-Hague&limit=1&appid=6d48803efc881326bec94c30df0addac";}
            else {geolocationApiUrl = "https://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=1&appid=" + apiKey;}

            //We get response from geolocationApiUrl
            JsonNode geolocationResponse = objectMapper.readTree(new URL(geolocationApiUrl));

            String latitude = "";
            String longitude = "";

            //We get longitude and latitude from response
            try {
                if (geolocationResponse.isArray() && geolocationResponse.size() > 0) {
                    JsonNode firstItem = geolocationResponse.get(0);
                    latitude = firstItem.get("lat").asText();
                    longitude = firstItem.get("lon").asText();
                    System.out.println("FirstItem: " + firstItem);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                //Weather API json response is complicated, so use https://jsonviewer.stack.hu/ to view its' structure better

                // Use latitude and longitude from Geolocation API and apiKey
                String weatherApiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey;
                //Gets response from weatherApiUrl
                JsonNode weatherDataResponse = objectMapper.readTree(new URL(weatherApiUrl));

                //See how response looks
                System.out.println("weather data response: " + weatherDataResponse);

                //Based on weatherDataResponse structure, we get needed values:
                //"list" is a list in json response, which has everything that we need to get, so we get "list" first
                JsonNode mainList = weatherDataResponse.get("list");
                //"0" is an object in "list". This "0" contains current weather conditions inside (that's what we need).
                // There are other objects like "1", "2" etc., which show weather conditions every 3 hours in future
                JsonNode firstItemMain = mainList.get(0);
                System.out.println("first item main: " + firstItemMain);

                //We get only "main" object from firstItemMain
                JsonNode mainObject = firstItemMain.get("main");
                System.out.println("main object: " + mainObject);

                //We get temperature, feels like and humidity from mainObject
                double temperature = mainObject.get("temp").asDouble() - 273.15; //Temperature is in Farenheit, so we convert to Celsius
                temperature = Math.round(temperature * 10) / 10.0;
                currentWeather.setTemperature(temperature);

                double feelsLike = mainObject.get("feels_like").asDouble() - 273.15;
                feelsLike = Math.round(feelsLike * 10) / 10.0;
                currentWeather.setFeelsLike(feelsLike);

                int humidity = mainObject.get("humidity").asInt();
                currentWeather.setHumidity(humidity);

                //[list] ->{0} ->[weather] ->{0} ->"description"
                //Explanation: list [list] contains an object {0}, which contains a list [weather], which has another {0} object inside, that contains "description"
                JsonNode weatherList = firstItemMain.get("weather");
                JsonNode firstItemWeather = weatherList.get(0);
                System.out.println("First item weather: " + firstItemWeather);
                String description = firstItemWeather.get("description").asText();
                currentWeather.setDescription(description);


                //List [list] contains an object {0}, which contains an object {wind}, which contains "speed" (of wind)
                JsonNode windObject = firstItemMain.get("wind");
                System.out.println("wind object: " + windObject);
                double windSpeed = windObject.get("speed").asDouble();
                currentWeather.setWindSpeed(windSpeed);

//We get country of inputted city (needed for plane tickets)
                //"city" is not inside [list], it's separate, so we don't use firstItemMain, instead we take from weatherDataResponse
                JsonNode cityObject = weatherDataResponse.get("city");
                System.out.println("city object: " + cityObject);
                String countryISO = cityObject.get("country").asText();

                //ISO code is converted to full country name, because ISO doesn't work for plane ticket link
                Locale country = new Locale("", countryISO);
                String countryFullName = country.getDisplayCountry(Locale.ENGLISH).toLowerCase();
                currentWeather.setCountry(countryFullName);


            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentWeather;
    }
}