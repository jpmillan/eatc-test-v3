package com.eatclub.challenge.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
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
    private final DateTimeFormatter standardFormat = DateTimeFormatter.ofPattern("H:mm");
    private final DateTimeFormatter amPmFormat = DateTimeFormatter.ofPattern("h:mma", Locale.ENGLISH);

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @GetMapping("/deals")
    public Map<String, List<DealResultDTO>> getActiveDeals(@RequestParam("timeOfDay") String timeOfDay) {
        LocalTime queryTime = parseTime(timeOfDay.toUpperCase().replace(" ", ""));
        
        List<Restaurant> allData = dealService.fetchAllData();
        List<DealResultDTO> matches = new ArrayList<>();

        for (Restaurant r : allData) {
            // Need to handle null deals gracefully
            if (r.getDeals() == null) continue;

            for (Deal d : r.getDeals()) {
                if (isTimeActive(queryTime, d.getStartTime(), d.getEndTime())) {
                    matches.add(mapToDto(r, d));
                }
            }
        }
        
        return Collections.singletonMap("deals", matches);
    }

    private LocalTime parseTime(String timeStr) {
        try {
            // Try standard "10:30"
            return LocalTime.parse(timeStr, standardFormat);
        } catch (DateTimeParseException e) {
            // Try "3:00PM"
            return LocalTime.parse(timeStr, amPmFormat);
        }
    }

    private boolean isTimeActive(LocalTime target, LocalTime start, LocalTime end) {
        // Handle deals that cross midnight if necessary (e.g. 11pm to 2am), 
        // though restaurant deals usually don't.
        if (end.isBefore(start)) {
             return !target.isBefore(start) || !target.isAfter(end);
        }
        return !target.isBefore(start) && !target.isAfter(end);
    }

    //mapping models to dto
    private DealResultDTO mapToDto(Restaurant r, Deal d) {
        DealResultDTO dto = new DealResultDTO();
        dto.restaurantObjectId = r.getId();
        dto.restaurantName = r.getName();
        dto.restaurantAddress1 = r.getAddress1();
        dto.restaurantSuburb = r.getSuburb();
        dto.restaurantOpen = "10:00am"; // Placeholder or derive from data
        dto.restaurantClose = "10:00pm"; // Placeholder
        dto.dealObjectId = d.getId();
        dto.discount = d.getDiscountOf() + "%";
        dto.dineIn = true;
        dto.lightning = false;
        dto.qtyLeft = 10; // Placeholder
        return dto;
    }
}