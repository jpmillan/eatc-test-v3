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
import com.eatclub.challenge.dto.PeakTimeDTO;
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
        
        System.out.println("DEBUG: Querying for time: " + queryTime);

        List<Restaurant> allData = dealService.fetchAllData();
        List<DealResultDTO> matches = new ArrayList<>();

        for (Restaurant r : allData) {
            if (r.getDeals() == null) continue;

            for (Deal d : r.getDeals()) {
                System.out.println("Checking Deal: " + d.getId() + " Start: " + d.getStartTime() + " End: " + d.getEndTime());

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
        
        if (target == null || start == null || end == null) {
            return false; 
        }
        // Handle deals that cross midnight
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
        dto.restaurantOpen = "10:00am"; // Placeholder
        dto.restaurantClose = "10:00pm"; // Placeholder
        dto.dealObjectId = d.getId();
        dto.discount = d.getDiscountOf() + "%";
        dto.dineIn = true;
        dto.lightning = false;
        dto.qtyLeft = 10; // Placeholder
        return dto;
    }

    @GetMapping("/peak-time")
    public PeakTimeDTO getPeakTime() {
        List<Restaurant> allData = dealService.fetchAllData();
        
        // Strategy: Iterate quarter hour of a day (or 15 mins) and count active deals
        int maxDeals = 0;
        LocalTime bestTime = LocalTime.MIN;

        // Checking every 15 minutes
        LocalTime cursor = LocalTime.of(0, 0);
        while(true) {
            int currentCount = 0;
            for (Restaurant r : allData) {
                if (r.getDeals() == null) continue;
                for (Deal d : r.getDeals()) {
                    if (isTimeActive(cursor, d.getStartTime(), d.getEndTime())) {
                        currentCount++;
                    }
                }
            }
            
            if (currentCount > maxDeals) {
                maxDeals = currentCount;
                bestTime = cursor;
            }
            
            cursor = cursor.plusMinutes(15);
            if (cursor.equals(LocalTime.of(0, 0))) break; // Back to start
        }

        // Return a window (e.g., the best 15 min block or just start/end of that block)
        return new PeakTimeDTO(
            bestTime.format(standardFormat), 
            bestTime.plusMinutes(15).format(standardFormat)
        );
    }
}