# Transit Tracker

A full-stack live transit dashboard for the DC Metro (WMATA), built to demonstrate a Java/Spring Boot + Angular + PostgreSQL stack  -  a combination frequently required in NoVA govcon junior full-stack postings.

## Features

- **Live train arrivals**  -  real-time predictions pulled from the WMATA API, selectable by station
- **Historical trend analysis**  -  a background scheduler polls tracked stations every 5 minutes and stores snapshots in PostgreSQL, powering an average-wait-time-by-hour chart
- Clean, styled UI with Metro line color badges

## Tech Stack

**Backend:** Java 21, Spring Boot 4, Spring Data JPA, Spring Scheduling, PostgreSQL, Maven
**Frontend:** Angular (standalone components, signals), Chart.js
**External API:** WMATA (Washington Metropolitan Area Transit Authority) Real-Time Rail Predictions API

## Architecture
```


frontend/   Angular app  -  station selector, live arrivals table, trend chart
backend/    Spring Boot API
  controller/   REST endpoints (predictions, trends)
  service/      WMATA API client, scheduled snapshot polling
  repository/   Spring Data JPA + custom aggregation queries
  entity/       JPA entities (PredictionSnapshot)
  dto/          WMATA response mapping
```


## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/predictions/{stationCode} | Live train predictions for a station |
| GET | /api/trends/{stationCode}?days=7 | Average wait time by line and hour of day |

## Running Locally

**Backend**
```bash
cd backend
mvn spring-boot:run
```


Requires a local PostgreSQL database named transit_tracker and a WMATA API key set in application.properties:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/transit_tracker
spring.datasource.username=postgres
spring.datasource.password=
wmata.api.key=YOUR_KEY_HERE
```


**Frontend**
```bash
cd frontend
npm install
ng serve
```


App runs at http://localhost:4200, backend at http://localhost:8080.

## Notable Engineering Details

- The scheduled snapshot job includes per-station error handling and rate-limit-aware delays, so a single failed WMATA API call never blocks the rest of the polling cycle.
- Historical trends are computed via a custom JPQL aggregation query (AVG, GROUP BY, HOUR()), not pulled and aggregated in application code.
- Frontend station selection drives both the live arrivals table and the historical trend chart from shared state.

## Roadmap

- [ ] Deploy to AWS (Elastic Beanstalk + RDS + Amplify)
- [ ] Expand tracked/selectable station list
- [ ] Reliability tracking (predicted vs. actual arrival)



