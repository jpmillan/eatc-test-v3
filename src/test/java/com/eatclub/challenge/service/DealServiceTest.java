package com.eatclub.challenge.service;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.eatclub.challenge.model.Restaurant;

@SpringBootTest
public class DealServiceTest {
    @Test
    void testFetchAllDataReturnsResults() {
        DealService service = new DealService();
        List<Restaurant> result = service.fetchAllData();
        
        Assertions.assertNotNull(result);
        // We assume the external URL is up for this integration test
        // In a real scenario, we'd mock RestTemplate to avoid network calls
        if (!result.isEmpty()) {
            System.out.println("Fetched " + result.size() + " restos for testing.");
        }
    }
    
}
