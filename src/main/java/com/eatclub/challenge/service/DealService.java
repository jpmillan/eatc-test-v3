package com.eatclub.challenge.service;

import com.eatclub.challenge.model.Restaurant;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

@Service
public class DealService {

    private static final String DATA_URL = "https://eccdn.com.au/misc/challengedata.json";
    
    public List<Restaurant> fetchAllData() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            Restaurant[] response = restTemplate.getForObject(DATA_URL, Restaurant[].class);
            return response != null ? Arrays.asList(response) : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}