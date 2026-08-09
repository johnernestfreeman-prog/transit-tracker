package com.johnfreeman.transittracker.controller;

import com.johnfreeman.transittracker.dto.TrainPrediction;
import com.johnfreeman.transittracker.service.WmataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/predictions")
@CrossOrigin(origins = "*")
public class PredictionController {

    private final WmataService wmataService;

    public PredictionController(WmataService wmataService) {
        this.wmataService = wmataService;
    }

    @GetMapping("/{stationCode}")
    public List<TrainPrediction> getPredictions(@PathVariable String stationCode) {
        return wmataService.getPredictionsForStation(stationCode);
    }
}
