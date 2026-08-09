package com.johnfreeman.transittracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrainPrediction {

    @JsonProperty("Car")
    private String car;

    @JsonProperty("Destination")
    private String destination;

    @JsonProperty("DestinationName")
    private String destinationName;

    @JsonProperty("Line")
    private String line;

    @JsonProperty("LocationCode")
    private String locationCode;

    @JsonProperty("LocationName")
    private String locationName;

    @JsonProperty("Min")
    private String min;

    public String getCar() { return car; }
    public void setCar(String car) { this.car = car; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getDestinationName() { return destinationName; }
    public void setDestinationName(String destinationName) { this.destinationName = destinationName; }

    public String getLine() { return line; }
    public void setLine(String line) { this.line = line; }

    public String getLocationCode() { return locationCode; }
    public void setLocationCode(String locationCode) { this.locationCode = locationCode; }

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }

    public String getMin() { return min; }
    public void setMin(String min) { this.min = min; }
}
