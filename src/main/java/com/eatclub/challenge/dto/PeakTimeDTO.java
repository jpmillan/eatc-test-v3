package com.eatclub.challenge.dto;

public class PeakTimeDTO {
    public String peakTimeStart;
    public String peakTimeEnd;
    
    public PeakTimeDTO(String start, String end) {
        this.peakTimeStart = start;
        this.peakTimeEnd = end;
    }
}