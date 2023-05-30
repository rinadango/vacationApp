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

        Weather currentWeather = new Weather(); //i sita objekta bus sudetos visos gautos vertes, paskui jas gausim ir naudosim su model.addAttribute
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            //Geolocation API, skirtas miesta paversti i latitude ir longitude kintamuosius, kuriuos veliau naudojam weather API requeste
            //Suformuojamas linkas su city ir apiKey vertem
            //su The Hague yra problemu, nes du zodziai, todel reikia naudot if

            String geolocationApiUrl;
            if (city.equals("The Hague")){geolocationApiUrl = "https://api.openweathermap.org/geo/1.0/direct?q=The-Hague&limit=1&appid=6d48803efc881326bec94c30df0addac";}
            else {geolocationApiUrl = "https://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=1&appid=" + apiKey;}

            //gaunam atsaka is geolocationApiUrl
            JsonNode geolocationResponse = objectMapper.readTree(new URL(geolocationApiUrl));

            String latitude = "";
            String longitude = "";

            //susirandam atsake latitude ir longitude
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

            //Patikrinu ar vertes tikrai gautos
            System.out.println("Latitude: " + latitude);
            System.out.println("Longitude: " + longitude);


            try {
                //Weather API
                //Toliau, kai matysit json atsaka, galit ji isimesti i https://jsonviewer.stack.hu/, tada bus aiskesne struktura


                // Naudojam is Geolocation API gautas latitude ir longitude vertes uzklausoje bei apiKey
                String weatherApiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey;
                //Gaunu atsaka is weatherApiUrl
                JsonNode weatherDataResponse = objectMapper.readTree(new URL(weatherApiUrl));

                //ziuriu kaip atrodo atsakas
                System.out.println("weather data response: " + weatherDataResponse);

                //Toliau, atsizvelgiant i weatherDataResponse struktura, is atsago gaunami reikalingi parametrai:
                //"list" yra listas jsone, i kuri sudeta viskas, ka mums reikia gauti, todel pirma pasiimam "list"
                JsonNode mainList = weatherDataResponse.get("list");
                //"0" yra objektas liste "list". Sitame "0" yra dabartines oro salygos (ko mums ir reikia). Yra ir kiti objektai "1", "2" ir t.t., kuriuose oro salygos rodomos kas 3 valandas i prieki
                JsonNode firstItemMain = mainList.get(0);
                System.out.println("first item main: " + firstItemMain);
                /*first item main: {"dt":1685404800,"main":{"temp":288.63,"feels_like":287.83,"temp_min":284.54,"temp_max":288.63,"pressure":1018,"sea_level":1018,"grnd_level":1006,"humidity":61,"temp_kf":4.09},
                "weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03n"}],
                "clouds":{"all":25},"wind":{"speed":0.97,"deg":335,"gust":0.98},"visibility":10000,"pop":0.05,
                "sys":{"pod":"n"},"dt_txt":"2023-05-30 00:00:00"}
                 */

                //gaunam tik "main" objekta is firstItemMain
                JsonNode mainObject = firstItemMain.get("main");
                System.out.println("main object: " + mainObject);
                /*main object: {"temp":288.63,"feels_like":287.83,"temp_min":284.54,"temp_max":288.63,"pressure":1018,"sea_level":1018,"grnd_level":1006,"humidity":61,"temp_kf":4.09}*/

                //Gaunam temperature, feels like ir humidity is mainObject
                double temperature = mainObject.get("temp").asDouble() - 273.15; //temperatura duoda farenheitais, todel paverciu i celsiju
                temperature = Math.round(temperature * 10) / 10.0;
                System.out.println("temperature: " + temperature);
                currentWeather.setTemperature(temperature);

                double feelsLike = mainObject.get("feels_like").asDouble() - 273.15;
                feelsLike = Math.round(feelsLike * 10) / 10.0;
                System.out.println("feels like: " + feelsLike);
                currentWeather.setFeelsLike(feelsLike);


                int humidity = mainObject.get("humidity").asInt();
                System.out.println("humidity: " + humidity);
                currentWeather.setHumidity(humidity);

                //[list]>{0}>[weather]>{0}>"description"
                //liste [list] yra objektas {0}, kuriame yra listas {weather}, kuriame yra objektas {0}, kuris savo viduj turi "description"
                JsonNode weatherList = firstItemMain.get("weather");
                JsonNode firstItemWeather = weatherList.get(0);
                System.out.println("first item weather: " + firstItemWeather);
                /*first item weather: {"id":802,"main":"Clouds","description":"scattered clouds","icon":"03n"}*/
                String description = firstItemWeather.get("description").asText();
                System.out.println("weather condition: " + description);
                currentWeather.setDescription(description);


                //liste [list] yra objektas {0}, kuriame yra objektas {wind}, kuris viduj turi "speed"
                JsonNode windObject = firstItemMain.get("wind");
                System.out.println("wind object: " + windObject);
                double windSpeed = windObject.get("speed").asDouble();
                System.out.println("wind speed: " + windSpeed);
                currentWeather.setWindSpeed(windSpeed);


                //Gaunam sali, kurioje yra musu miestas (reikia liektuvo bilietui)
                //city nera liste [list], jis atskirai, todel nenaudoju firstItemMain, imu tiesiai is viso atsako
                JsonNode cityObject = weatherDataResponse.get("city");
                System.out.println("city object: " + cityObject);
                //Salis pateikiama ISO kodu (pvz LT)
                String countryISO = cityObject.get("country").asText();
                System.out.println("country: " + countryISO);

                //ISO koda vercia i pilna salies pavadinima, nes ISO netinka lektuvo bilietu linkui
                Locale country = new Locale("", countryISO);
                String countryFullName = country.getDisplayCountry(Locale.ENGLISH).toLowerCase();
                System.out.println("country full name: " + countryFullName);
                /*String formattedCountryName = countryFullName.toLowerCase();
                System.out.println("country full name: " + formattedCountryName);*/
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