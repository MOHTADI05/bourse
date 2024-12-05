import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js/auto';
import { ClusteringServiceService } from 'src/app/Services/clustering-service.service';


@Component({
  selector: 'app-body',
  templateUrl: './body.component.html',
  styleUrls: ['./body.component.css']
})
export class BodyComponent implements OnInit {

  constructor(private clusteringService: ClusteringServiceService) { }

  ngOnInit(): void {
    this.generateChart();
    this.generateChart2();
  }

  generateChart(): void {
    this.clusteringService.performClustering().subscribe(percentages => {
      const doughnutChart = new Chart('doughnutChart', {
        type: 'doughnut',
        data: {
          labels: ['solvable ', 'non solvable'],
          datasets: [{
            label: 'Clusters',
            data: percentages,
            backgroundColor: [
              'rgb(255, 99, 132)',
              'rgb(54, 162, 235)'
            ],
            hoverOffset: 4
          }]
        },
        options: {
          responsive: true,
          plugins: {
            legend: {
              position: 'top',
            },
            title: {
              display: true
            }
          }
        }
      });
    }, error => {
      console.error('Error fetching clustering data:', error);
    });
  }


  generateChart2(): void {
    this.clusteringService.getCluster2Data().subscribe(cluster2Data => {
      this.clusteringService.getCluster1Data().subscribe(cluster1Data => {
        const radarChart = new Chart('radarChart', {
          type: 'radar',
          data: {
            labels: ['revenue', 'charge_mensuel', 'epargne'],
            datasets: [{
              label: 'Solvable',
              data: cluster2Data, // Données du Cluster 2
              fill: true,
              backgroundColor: 'rgba(255, 99, 132, 0.2)', // Couleur pour le Cluster 2
              borderColor: 'rgb(255, 99, 132)',
              pointBackgroundColor: 'rgb(255, 99, 132)',
              pointBorderColor: '#fff',
              pointHoverBackgroundColor: '#fff',
              pointHoverBorderColor: 'rgb(255, 99, 132)'
            }, {
              label: 'Non solvable',
              data: cluster1Data, // Données du Cluster 1
              fill: true,
              backgroundColor: 'rgba(54, 162, 235, 0.2)', // Couleur pour le Cluster 1
              borderColor: 'rgb(54, 162, 235)',
              pointBackgroundColor: 'rgb(54, 162, 235)',
              pointBorderColor: '#fff',
              pointHoverBackgroundColor: '#fff',
              pointHoverBorderColor: 'rgb(54, 162, 235)'
            }]
          },
          options: {
            elements: {
              line: {
                borderWidth: 3
              }
            }
          }
        });
      });
    });
  }
  
}