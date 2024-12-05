import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';

import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from 'src/model/User';
import { AuthService } from '../Services/auth-service.service';
import { UserService } from '../Services/User.service';
import Chart from 'chart.js/auto';
import { ClusteringServiceService } from 'src/app/Services/clustering-service.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  users: User[] = [];
  agents: User[]= [];


  constructor(private userService: UserService, private router: Router,private http: HttpClient, private authService: AuthService,private clusteringService: ClusteringServiceService) {

  }
      ngOnInit(): void {
      this.loadUsers();
      this.generateChart();
      this.generateChart2();
    }

  loadUsers() {
    const token = localStorage.getItem('jwt');
    if (token) {
      const headers = new HttpHeaders({
        'Authorization': 'Bearer ' + token
      });

      this.http.get<any[]>('http://localhost:8084/api/admin/users', { headers }).subscribe(
        users => {
          this.users = users;
        },
        error => {
          this.router.navigate(['/login']);

          console.error('Erreur lors du chargement des utilisateurs :', error);
        }
      );
    } else {
      console.error('Aucun token JWT trouvé dans le localStorage');
      this.router.navigate(['/login']);

    }
  }
  blockUser(cin: number) {
    const token = localStorage.getItem('jwt');
    if (token) {
      const headers = new HttpHeaders({
        'Authorization': 'Bearer ' + token
      });

      this.http.put<void>(`http://localhost:8084/api/admin/block/${cin}`, null, { headers }).subscribe(
        () => {
          console.log('Utilisateur bloqué avec succès');
          // Recharger la liste des utilisateurs après le blocage
          this.loadUsers();
        },
        error => {
          console.error('Erreur lors du blocage de l\'utilisateur :', error);
        }
      );
    } else {
      console.error('Aucun token JWT trouvé dans le localStorage');
    }
  }

  unblockUser(cin: number) {
    const token = localStorage.getItem('jwt');
    if (token) {
      const headers = new HttpHeaders({
        'Authorization': 'Bearer ' + token
      });

      this.http.put<void>(`http://localhost:8084/api/admin/unblock/${cin}`, null, { headers }).subscribe(
        () => {
          console.log('Utilisateur débloqué avec succès');
          // Recharger la liste des utilisateurs après le déblocage
          this.loadUsers();
        },
        error => {
          console.error('Erreur lors du déblocage de l\'utilisateur :', error);
        }
      );
    } else {

      console.error('Aucun token JWT trouvé dans le localStorage');
    }
  }

//////////////////////////////////////////////    CLUSTERING FUNCTION /////////////////////////////////////////////////////////

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
            label: 'Non Solvable',
            data: cluster2Data, // Données du Cluster 2
            fill: true,
            backgroundColor: 'rgba(54, 162, 235, 0.2)', // Couleur pour le Cluster 1
            borderColor: 'rgb(54, 162, 235)',
            pointBackgroundColor: 'rgb(54, 162, 235)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgb(54, 162, 235)'

          }, {
            label: ' solvable',
            data: cluster1Data, // Données du Cluster 1
            fill: true,
            backgroundColor: 'rgba(255, 99, 132, 0.2)', // Couleur pour le Cluster 2
            borderColor: 'rgb(255, 99, 132)',
            pointBackgroundColor: 'rgb(255, 99, 132)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgb(255, 99, 132)'

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
currentPage: number = 1;
pageSize: number = 10;
getUsersForPage(): User[] {
  const startIndex = (this.currentPage - 1) * this.pageSize;
  const endIndex = Math.min(startIndex + this.pageSize, this.users.length);
  return this.users.slice(startIndex, endIndex);
}



  }



