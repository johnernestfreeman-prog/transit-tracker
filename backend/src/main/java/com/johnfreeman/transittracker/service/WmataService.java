package com.johnfreeman.transittracker.service;

import com.johnfreeman.transittracker.dto.PredictionResponse;
import com.johnfreeman.transittracker.dto.TrainPrediction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class WmataService {

    private final RestClient restClient;
    private final String apiKey;

    public WmataService(@Value("${wmata.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.restClient = RestClient.builder()
                .baseUrl("https://api.wmata.com")
                .build();
    }

    public List<TrainPrediction> getPredictionsForStation(String stationCode) {
        PredictionResponse response = restClient.get()
                .uri("/StationPrediction.svc/json/GetPrediction/" + stationCode)
                .header("api_key", apiKey)
                .retrieve()
                .body(PredictionResponse.class);

        return response != null ? response.getTrains() : List.of();
    }
}
