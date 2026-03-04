package com.eatclub.challenge.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.eatclub.challenge.model.Restaurant;

@SpringBootTest
public class DealServiceTest {

    @Autowired
    private DealService service;

    @Test
    void testFetchAllDataReturnsResults() {
        List<Restaurant> result = service.fetchAllData();

        Assertions.assertNotNull(result);
        if (!result.isEmpty()) {
            System.out.println("Fetched " + result.size() + " restos for testing.");
        }
    }

}
