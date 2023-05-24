package com.vacationapp.repository;

import com.vacationapp.entity.DestinationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<DestinationInfo, Integer> {

    //List<DestinationInfo> findDistinctByCountryAndCountryIsNotEmpty();


    List<DestinationInfo> findAllByCountry(String country);
}
