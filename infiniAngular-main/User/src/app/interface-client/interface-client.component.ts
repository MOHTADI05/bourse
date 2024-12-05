import { DemandeCreditService } from './../Services/demande-credit.service';
import { Component, OnInit } from '@angular/core';
import { Chart, ChartConfiguration } from 'chart.js';
import { AuthService } from '../Services/auth-service.service';
import {Observable} from "rxjs";
import {DemandeCredit} from "../../model/DemandeCredit";
import {InvHistory} from "../../model/InvHistory";

@Component({
  selector: 'app-interface-client',
  templateUrl: './interface-client.component.html',
  styleUrls: ['./interface-client.component.css']
})
export class InterfaceClientComponent implements OnInit {

  lineChart: Chart<"line", number[], string> | null = null; // Spécifiez explicitement le type de lineChart
  credits: DemandeCredit[] = [];
  tabvisibal2 = false;

  constructor(private demandeCreditService: DemandeCreditService, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.loadLineChart();
  }

  loadLineChart(): void {
    const token = localStorage.getItem('jwt');
    if (token) {
      this.authService.getUserId(token).subscribe(
        id => {
          console.log('ID utilisateur récupéré:', id);
          this.demandeCreditService.chart(id).subscribe(
            data => {
              const years = Object.keys(data);
              const amounts = Object.values(data);

              this.lineChart = new Chart('lineChart', {
                type: 'line',
                data: {
                  labels: years,
                  datasets: [{
                    label: 'Montant restant par année',
                    data: amounts,
                    fill: false,
                    borderColor: 'rgb(75, 192, 192)',
                    tension: 0.1
                  }]
                },
                options: {
                  scales: {
                    y: {
                      beginAtZero: true
                    }
                  }
                }
              });
            },
            error => {
              console.error('Erreur lors de la récupération des données pour le line chart:', error);
              // Gérez l'erreur ici selon vos besoins
            }
          );
        },
        error => {
          console.error('Erreur lors de la récupération de l\'ID utilisateur:', error);
          // Gérez l'erreur ici selon vos besoins
        }
      );
    } else {
      console.error('Aucun token JWT trouvé dans le localStorage');
    }
  }
  Tabvisibal(): void{
    this.tabvisibal2=true;
  } 
  Tabinvisibal(): void{
    this.tabvisibal2=false;
  } 
  fetchDataByClientId2(): void {
    const token = localStorage.getItem('jwt');
    console.log('aasba ala raseek', token);
    if (token) {
      this.authService.getUserId(token).subscribe(
        id => {
          console.log('ID utilisateur récupéré:', id);
          // Utilisez l'ID utilisateur récupéré pour interroger le service invHistoryService
          this.demandeCreditService.findCreditByUser(id).subscribe(
            credits => {
              this.credits = credits;
              console.log('Données reçues du serveur:', credits);
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

  payer(idC: number) {
    const token = localStorage.getItem('jwt');

    if (token) {
      this.authService.getUserId(token).subscribe(
        id => {
          console.log('ID utilisateur récupéré:', id);
          this.demandeCreditService.Pay(id, 1, idC).subscribe()
        }, error => {
          console.error('Erreur lors de la récupération de l\'ID utilisateur:', error);
          // Gérez l'erreur ici selon vos besoins
        }
      );
    } else {
      console.error('Aucun token JWT trouvé dans le localStorage');
    }

  }
}