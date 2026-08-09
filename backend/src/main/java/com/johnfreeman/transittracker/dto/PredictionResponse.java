package com.johnfreeman.transittracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PredictionResponse {

    @JsonProperty("Trains")
    private List<TrainPrediction> trains;

    public List<TrainPrediction> getTrains() { return trains; }
    public void setTrains(List<TrainPrediction> trains) { this.trains = trains; }
}
