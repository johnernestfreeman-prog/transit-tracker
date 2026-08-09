package com.johnfreeman.transittracker.service;

import com.johnfreeman.transittracker.dto.TrainPrediction;
import com.johnfreeman.transittracker.entity.PredictionSnapshot;
import com.johnfreeman.transittracker.repository.PredictionSnapshotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PredictionSnapshotService {

    private static final Logger log = LoggerFactory.getLogger(PredictionSnapshotService.class);
    private static final List<String> TRACKED_STATIONS = List.of("A01", "B01", "A11");

    private final WmataService wmataService;
    private final PredictionSnapshotRepository repository;

    public PredictionSnapshotService(WmataService wmataService, PredictionSnapshotRepository repository) {
        this.wmataService = wmataService;
        this.repository = repository;
    }

    @Scheduled(fixedRate = 300000)
    public void captureSnapshots() {
        LocalDateTime now = LocalDateTime.now();
        for (String stationCode : TRACKED_STATIONS) {
            try {
                List<TrainPrediction> predictions = wmataService.getPredictionsForStation(stationCode);
                for (TrainPrediction p : predictions) {
                    Integer numeric = parseMinutes(p.getMin());
                    PredictionSnapshot snapshot = new PredictionSnapshot(
                        stationCode, p.getLine(), p.getDestinationName(), p.getMin(), numeric, now
                    );
                    repository.save(snapshot);
                }
                log.info("Captured {} predictions for station {}", predictions.size(), stationCode);
            } catch (Exception e) {
                log.error("Failed to capture snapshot for station {}: {}", stationCode, e.getMessage());
            }
            try {
                Thread.sleep(3500);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private Integer parseMinutes(String min) {
        try {
            return Integer.parseInt(min);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
