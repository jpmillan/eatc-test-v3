package com.eatclub.challenge.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.eatclub.challenge.model.Restaurant;

@Service
public class DealService {

    private static final String DATA_URL = "https://eccdn.com.au/misc/challengedata.json";
    
    public List<Restaurant> fetchAllData() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            Restaurant[] response = restTemplate.getForObject(DATA_URL, Restaurant[].class);
            return response != null ? Arrays.asList(response) : new ArrayList<>();
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }
}