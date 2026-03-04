package com.eatclub.challenge.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.eatclub.challenge.model.Restaurant;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DealService {

    private final ObjectMapper objectMapper;

    public DealService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Restaurant> fetchAllData() {
        try {
            ClassPathResource resource = new ClassPathResource("challengedata.json");
            JsonNode root = objectMapper.readTree(resource.getInputStream());

            List<Restaurant> results = new ArrayList<>();
            if (root.isArray()) {
                results = Arrays.asList(objectMapper.treeToValue(root, Restaurant[].class));
            } else if (root.isObject()) {
                 // handle object wrapper
                 Iterator<JsonNode> elements = root.elements();
                 while(elements.hasNext()) {
                     JsonNode n = elements.next();
                     if (n.isArray()) {
                         results = Arrays.asList(objectMapper.treeToValue(n, Restaurant[].class));
                         break;
                     }
                 }
            }
            return results;

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}