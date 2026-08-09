package com.johnfreeman.transittracker.controller;

import com.johnfreeman.transittracker.repository.PredictionSnapshotRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trends")
@CrossOrigin(origins = "*")
public class TrendController {

    private final PredictionSnapshotRepository repository;

    public TrendController(PredictionSnapshotRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{stationCode}")
    public List<Map<String, Object>> getTrends(@PathVariable String stationCode, @RequestParam(defaultValue = "7") int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        List<Object[]> rows = repository.findAverageWaitByStationGroupedByHour(stationCode, since);

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("line", row[0]);
            entry.put("hourOfDay", row[1]);
            entry.put("avgMinutes", row[2]);
            result.add(entry);
        }
        return result;
    }
}
