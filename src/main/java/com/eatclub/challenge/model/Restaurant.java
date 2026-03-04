package com.eatclub.challenge.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Restaurant {
    
    @JsonAlias({"objectId", "restaurantId", "id"}) 
    private String id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("address1")
    private String address1;
    
    @JsonProperty("suburb")
    private String suburb;
    
    @JsonProperty("state")
    private String state;
    
    @JsonProperty("postcode")
    private String postcode;
    
    @JsonProperty("deals")
    private List<Deal> deals;
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getAddress1() { return address1; }
    public void setAddress1(String address1) { this.address1 = address1; }
    
    public String getSuburb() { return suburb; }
    public void setSuburb(String suburb) { this.suburb = suburb; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public String getPostcode() { return postcode; }
    public void setPostcode(String postcode) { this.postcode = postcode; }
    
    public List<Deal> getDeals() { return deals; }
    public void setDeals(List<Deal> deals) { this.deals = deals; }
}