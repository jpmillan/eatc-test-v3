package com.eatclub.challenge.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Deal {

    @JsonProperty("objectId")
    private String id;
    
    @JsonProperty("discount")
    private int discountOf;
    
    @JsonIgnore // Internal use, populated via setters
    private LocalTime startTime;
    
    @JsonIgnore // Internal use
    private LocalTime endTime; 

    // Case-insensitive formatter for "3:00pm" or "3:00PM"
    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("h:mma")
            .toFormatter(Locale.ENGLISH);

    @JsonSetter("open")
    public void setOpenTimeStr(String timeStr) {
        try {
            this.startTime = LocalTime.parse(timeStr, TIME_FORMATTER);
        } catch (Exception e) {
            // Fallback or log if format changes
            System.err.println("Failed to parse time: " + timeStr);
        }
    }

    @JsonSetter("close")
    public void setCloseTimeStr(String timeStr) {
        try {
            this.endTime = LocalTime.parse(timeStr, TIME_FORMATTER);
        } catch (Exception e) {
            System.err.println("Failed to parse time: " + timeStr);
        }
    }

    // --- Standard Setters (Required for Unit Tests) ---

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    // --- Getters ---

    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }

    // --- Other Fields ---

    @JsonProperty("dineIn")
    private boolean dineIn; 
    
    @JsonProperty("lightning")
    private boolean lightning;
    
    @JsonProperty("qtyLeft")
    private Integer quantity; 

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public int getDiscountOf() { return discountOf; }
    public void setDiscountOf(int discountOf) { this.discountOf = discountOf; }
    public boolean isDineIn() { return dineIn; }
    public void setDineIn(boolean dineIn) { this.dineIn = dineIn; }
    public boolean isLightning() { return lightning; }
    public void setLightning(boolean lightning) { this.lightning = lightning; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    @Override
    public String toString() {
        return "Deal{id='" + id + "', start=" + startTime + ", end=" + endTime + "}";
    }
}