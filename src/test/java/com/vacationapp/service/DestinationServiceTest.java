package com.vacationapp.service;

import com.vacationapp.entity.DestinationInfo;
import com.vacationapp.repository.DestinationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DestinationServiceTest {
    @InjectMocks
    private DestinationService destinationService;

    @Mock
    private DestinationRepository destinationRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllDestinations() {

        DestinationInfo destinationInfo1 = new DestinationInfo(1, "Lithuania", "Vilnius", 100, 2000);
        DestinationInfo destinationInfo2 = new DestinationInfo(2, "Belgium", "Brussels", 150, 1000);
        DestinationInfo destinationInfo3 = new DestinationInfo(3, "Poland", "Gdansk", 50, 1500);

        List<DestinationInfo> destinationInfoList = new ArrayList<>();
        destinationInfoList.add(destinationInfo1);
        destinationInfoList.add(destinationInfo2);
        destinationInfoList.add(destinationInfo3);

        when(destinationRepository.findAll()).thenReturn(destinationInfoList);

        List<DestinationInfo> resultList = destinationService.getAllDestinations();

        assertEquals(3, resultList.size());
        assertEquals("Belgium", resultList.get(1).getCountry());
        assertEquals(destinationInfoList, resultList);
    }
}