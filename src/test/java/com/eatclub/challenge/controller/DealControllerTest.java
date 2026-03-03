package com.eatclub.challenge.controller;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        Map<String, List<DealResultDTO>> result = dealController.getActiveDeals("11:00");
        assertFalse(result.get("deals").isEmpty());
        assertEquals("Test Maccas", result.get("deals").get(0).restaurantName);
    }

    @Test
    void testGetDeals_TimeOutsideWindow() {
        Map<String, List<DealResultDTO>> result = dealController.getActiveDeals("09:00");
        assertTrue(result.get("deals").isEmpty());
    }

    @Test
    void testGetDeals_AmPmFormat() {
        // Reuse logic but mocking afternoon deals
        List<Restaurant> pmData = new ArrayList<>();
        Restaurant r = new Restaurant();
        List<Deal> deals = new ArrayList<>();
        Deal d = new Deal();
        d.setStartTime(LocalTime.of(14, 0)); // 2pm
        d.setEndTime(LocalTime.of(16, 0));   // 4pm
        deals.add(d);
        r.setDeals(deals);
        pmData.add(r);
        
        when(dealService.fetchAllData()).thenReturn(pmData);

        // Test parsing "3:00pm"
        Map<String, List<DealResultDTO>> result = dealController.getActiveDeals("03:00pm");
        assertFalse(result.get("deals").isEmpty());
    }

}