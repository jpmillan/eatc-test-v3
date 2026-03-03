package com.eatclub.challenge.controller;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import com.eatclub.challenge.dto.DealResultDTO;
import com.eatclub.challenge.model.Deal;
import com.eatclub.challenge.model.Restaurant;
import com.eatclub.challenge.service.DealService;


@SpringBootTest
public class DealControllerTest {
    private DealService dealService;
    private DealController dealController;

@BeforeEach
    void setUp() {
        // Mock the service so we control the data
        dealService = Mockito.mock(DealService.class);
        dealController = new DealController(dealService);
        
        // Setup dummy data
        List<Restaurant> mockData = new ArrayList<>();
        Restaurant r = new Restaurant();
        r.setName("Test Maccas");
        List<Deal> deals = new ArrayList<>();
        
        Deal d1 = new Deal();
        // Deal is active from 10:00 to 12:00
        d1.setStartTime(LocalTime.of(10, 0)); 
        d1.setEndTime(LocalTime.of(12, 0));
        d1.setDiscountOf(20);
        deals.add(d1);
        
        r.setDeals(deals);
        mockData.add(r);

        when(dealService.fetchAllData()).thenReturn(mockData);
    }

    @Test
    void testGetDeals_TimeInsideWindow() {
        // Test standard time format
        Map<String, List<DealResultDTO>> result = dealController.getDeals("11:00");
        assertFalse(result.get("deals").isEmpty());
        assertEquals("Test Maccas", result.get("deals").get(0).restaurantName);
    }

}