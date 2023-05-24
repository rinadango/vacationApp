package com.vacationapp.service;

import com.vacationapp.entity.DestinationInfo;
import com.vacationapp.repository.DestinationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public List<DestinationInfo> getAllDestinations(){
        return destinationRepository.findAll();
    }


    public Set<String> getAllDestinationCountry(){

        List<DestinationInfo> listOfDestination = getAllDestinations();

        Set<String> country = new HashSet<>();

        for (DestinationInfo destinationInfo : listOfDestination) {
            country.add(destinationInfo.getCountry());
        }

        return country;
    }

    public List<String> getCities(String name) {
        List<DestinationInfo> listOfDestination = getAllDestinations();
        List<String> cities = new ArrayList<>();

        for (DestinationInfo destinationInfo : listOfDestination) {
            if (destinationInfo.getCity().equals(name)) {
                cities.add(destinationInfo.getCity());
            }
        }
        return cities;
    }


}
