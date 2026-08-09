package com.johnfreeman.transittracker.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prediction_snapshots")
public class PredictionSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stationCode;
    private String line;
    private String destinationName;
    private String minutesRaw;
    private Integer minutesNumeric;
    private LocalDateTime capturedAt;

    public PredictionSnapshot() {}

    public PredictionSnapshot(String stationCode, String line, String destinationName, String minutesRaw, Integer minutesNumeric, LocalDateTime capturedAt) {
        this.stationCode = stationCode;
        this.line = line;
        this.destinationName = destinationName;
        this.minutesRaw = minutesRaw;
        this.minutesNumeric = minutesNumeric;
        this.capturedAt = capturedAt;
    }

    public Long getId() { return id; }
    public String getStationCode() { return stationCode; }
    public void setStationCode(String stationCode) { this.stationCode = stationCode; }
    public String getLine() { return line; }
    public void setLine(String line) { this.line = line; }
    public String getDestinationName() { return destinationName; }
    public void setDestinationName(String destinationName) { this.destinationName = destinationName; }
    public String getMinutesRaw() { return minutesRaw; }
    public void setMinutesRaw(String minutesRaw) { this.minutesRaw = minutesRaw; }
    public Integer getMinutesNumeric() { return minutesNumeric; }
    public void setMinutesNumeric(Integer minutesNumeric) { this.minutesNumeric = minutesNumeric; }
    public LocalDateTime getCapturedAt() { return capturedAt; }
    public void setCapturedAt(LocalDateTime capturedAt) { this.capturedAt = capturedAt; }
}
