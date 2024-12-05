import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as apexcharts from "apexcharts";
import {AuthService} from "../Services/auth-service.service";
import {InvHistoryService} from "../Services/invhistoryservice.service";
import {immobilier} from "../../model/Immobilier";
import {InvHistory} from "../../model/InvHistory";

@Component({
  selector: 'app-area-chart',
  templateUrl: './area-chart.component.html',
  styleUrls: ['./area-chart.component.css']
})
export class AreaChartComponent implements OnInit {
  weeklycharvisible=false;
  dailychartvisible=false;
  yearlychartvisible=false ;
  invHistories: InvHistory[] = [];
  tabvisibal = false;

  constructor(private http: HttpClient, private authService:AuthService, private invHistoryService :InvHistoryService ) { }

  ngOnInit(): void {
  }

  fetchDataAndRenderChart(endpoint: string): void {
    const token = localStorage.getItem('jwt');

    if (token) {
      this.authService.getUserId(token).subscribe(
        id => {
          console.log('ID utilisateur récupéré:', id);

          this.http.get<any>(`http://localhost:8084/inv_history/${endpoint}/${id}`).subscribe(
            response => {
              console.log('Data received from the server:', response);
              this.renderChart(response);
            },
            error => {
              console.error('Error fetching data:', error);
              // Handle error here as needed
            }
          );
        },
        error => {
          console.error('Erreur lors de la récupération de l\'ID utilisateur:', error);
          // Handle error here as needed
        }
      );
    } else {
      console.error('Aucun token JWT trouvé dans le localStorage');
    }
  }fetchDataByClientId(): void {
    const token = localStorage.getItem('jwt');

    if (token) {
      this.authService.getUserId(token).subscribe(
        id => {
          console.log('ID utilisateur récupéré:', id);
          // Utilisez l'ID utilisateur récupéré pour interroger le service invHistoryService
          this.invHistoryService.getDemandesCreditParClient(id).subscribe(
            invHistories => {
              this.invHistories = invHistories;
              console.log('Données reçues du serveur:', invHistories);
              this.Tabvisibal();
            },
            error => {
              console.error('Erreur lors de la récupération des données:', error);
              // Gérer l'erreur ici selon vos besoins
            }
          );
        },
        error => {
          console.error('Erreur lors de la récupération de l\'ID utilisateur:', error);
          // Gérer l'erreur ici selon vos besoins
        }
      );
    } else {
      console.error('Aucun token JWT trouvé dans le localStorage');
    }
  }

  Tabvisibal(): void{
    this.tabvisibal=true;
  } 
  Tabinvisibal(): void{
    this.tabvisibal=false;
  } 

  Weeklycharvisible(): void {
    this.weeklycharvisible = true;
    this.dailychartvisible = false;
    this.yearlychartvisible = false;
    this.fetchDataAndRenderChart('weekly-profit-loss');
  }

  Dailychartvisible(): void {
    this.dailychartvisible = true;
    this.weeklycharvisible = false;
    this.yearlychartvisible = false;
    this.fetchDataAndRenderChart('daily-profit-loss');
  }

  Yearlychartvisible(): void {
    this.yearlychartvisible = true;
    this.weeklycharvisible = false;
    this.dailychartvisible = false;
    this.fetchDataAndRenderChart('yearly-profit-loss');
  }

  renderChart(data: any): void {
    if (!data || !data.prices || !data.dates) {
      console.error('Data is incomplete or not loaded');
      return; // Stop the function if data is not available
    }

    const series = {
      "monthDataSeries1": {
        "prices": data.prices,
        "dates": data.dates
      }
    };

    new apexcharts(document.querySelector("#areaChart"), {
      series: [{
        name: "STOCK ABC",
        data: series.monthDataSeries1.prices
      }],
      chart: {
        type: 'area',
        height: 350,
        zoom: {
          enabled: false
        }
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        curve: 'straight'
      },
      subtitle: {
        text: 'Price Movements',
        align: 'left'
      },
      labels: series.monthDataSeries1.dates,
      xaxis: {
        type: 'datetime',
      },
      yaxis: {
        opposite: true
      },
      legend: {
        horizontalAlign: 'left'
      }
    }).render();
  }



}