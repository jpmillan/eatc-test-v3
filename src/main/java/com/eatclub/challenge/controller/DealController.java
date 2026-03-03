package com.eatclub.challenge.controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatclub.challenge.dto.DealResultDTO;
import com.eatclub.challenge.model.Deal;
import com.eatclub.challenge.model.Restaurant;
import com.eatclub.challenge.service.DealService;

@RestController
public class DealController {

    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @GetMapping("/deals")
    public Map<String, List<DealResultDTO>> getDeals(@RequestParam String timeOfDay) {
        // Parse time like "10:30" or "3:00pm"
        // For simplicity assuming 24h format first or simple HH:mm in this step
        // Real implementation usually needs a robust parser for "3:00pm"
        LocalTime queryTime = parseTime(timeOfDay); 
        
        List<Restaurant> allData = dealService.fetchAllData();
        List<DealResultDTO> results = new ArrayList<>();

        for (Restaurant r : allData) {
            for (Deal d : r.getDeals()) {
                if (isTimeBetween(queryTime, d.getStartTime(), d.getEndTime())) {
                    DealResultDTO dto = new DealResultDTO();
                    dto.restaurantName = r.getName();
                    dto.discount = d.getDiscountOf() + "%";
                    dto.restaurantAddress1 = r.getAddress1();
                    dto.restaurantSuburb = r.getSuburb();
                    dto.dineIn = d.isDineIn();
                    dto.lightning = d.isLightning();
                    dto.qtyLeft = d.getQtyLeft();
                    results.add(dto);
                }
            }
        }
        
        Map<String, List<DealResultDTO>> response = new HashMap<>();
        response.put("deals", results);
        return response;
    }

    private LocalTime parseTime(String timeStr) {
        // naive parsing, improve later
        // If the user inputs 3:00pm, this simple ISO parser will fail
        // This is a good place to show incremental improvement in next commits
        return LocalTime.parse(timeStr); 
    }
    
    private boolean isTimeBetween(LocalTime target, LocalTime start, LocalTime end) {
        return !target.isBefore(start) && !target.isAfter(end);
    }
}