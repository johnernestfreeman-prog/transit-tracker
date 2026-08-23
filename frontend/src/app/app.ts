import { Component, OnInit, signal, effect } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

interface TrainPrediction {
  Car: string;
  Destination: string;
  DestinationName: string;
  Line: string;
  LocationCode: string;
  LocationName: string;
  Min: string;
}

interface Station {
  code: string;
  name: string;
}

interface TrendEntry {
  line: string;
  hourOfDay: number;
  avgMinutes: number;
}

const API_BASE_URL = "https://d2edvyjl8llo0z.cloudfront.net";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  protected readonly title = signal('frontend');
  protected readonly predictions = signal<TrainPrediction[]>([]);
  protected readonly selectedStation = signal('A01');
  protected readonly trendData = signal<TrendEntry[]>([]);
  private chart: Chart | null = null;

  protected readonly stations: Station[] = [
    { code: 'A01', name: 'Metro Center' },
    { code: 'B01', name: 'Gallery Pl-Chinatown' },
    { code: 'C01', name: 'Metro Center (Blue/Orange/Silver)' },
    { code: 'A11', name: 'Rosslyn' },
    { code: 'A15', name: 'Vienna' },
    { code: 'K05', name: 'Franconia-Springfield' },
    { code: 'D01', name: "Federal Triangle" },
    { code: 'E01', name: 'Mt Vernon Sq' }
  ];

  constructor(private http: HttpClient) {
    effect(() => {
      const data = this.trendData();
      this.renderChart(data);
    });
  }

  ngOnInit() {
    this.loadPredictions();
    this.loadTrends();
  }

  onStationChange(event: Event) {
    const code = (event.target as HTMLSelectElement).value;
    this.selectedStation.set(code);
    this.loadPredictions();
    this.loadTrends();
  }

  loadPredictions() {
    this.http.get<TrainPrediction[]>(API_BASE_URL + "/api/predictions/" + this.selectedStation())
      .subscribe(data => this.predictions.set(data));
  }

  loadTrends() {
    this.http.get<TrendEntry[]>(API_BASE_URL + "/api/trends/" + this.selectedStation())
      .subscribe(data => this.trendData.set(data));
  }

  private renderChart(data: TrendEntry[]) {
    const canvas = document.getElementById('trendChart') as HTMLCanvasElement;
    if (!canvas) return;

    if (this.chart) {
      this.chart.destroy();
    }

    const labels = data.map(d => d.hourOfDay + ":00");
    const values = data.map(d => d.avgMinutes);

    this.chart = new Chart(canvas, {
      type: 'bar',
      data: {
        labels: labels,
        datasets: [{
          label: 'Avg Wait (minutes)',
          data: values,
          backgroundColor: '#bf0d3e'
        }]
      },
      options: {
        responsive: true,
        scales: {
          y: { beginAtZero: true, title: { display: true, text: 'Minutes' } },
          x: { title: { display: true, text: 'Hour of Day' } }
        }
      }
    });
  }
}
