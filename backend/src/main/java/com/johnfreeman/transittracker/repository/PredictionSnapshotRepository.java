package com.johnfreeman.transittracker.repository;

import com.johnfreeman.transittracker.entity.PredictionSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PredictionSnapshotRepository extends JpaRepository<PredictionSnapshot, Long> {

    @Query("SELECT p.line as line, HOUR(p.capturedAt) as hourOfDay, AVG(p.minutesNumeric) as avgMinutes " +
           "FROM PredictionSnapshot p " +
           "WHERE p.stationCode = :stationCode AND p.minutesNumeric IS NOT NULL AND p.capturedAt >= :since " +
           "GROUP BY p.line, HOUR(p.capturedAt) " +
           "ORDER BY p.line, hourOfDay")
    List<Object[]> findAverageWaitByStationGroupedByHour(@Param("stationCode") String stationCode, @Param("since") LocalDateTime since);
}
