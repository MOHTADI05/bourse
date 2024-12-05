import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators,ValidatorFn,AbstractControl  } from '@angular/forms';


import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';  
import { User } from 'src/model/User';
import { UserService } from 'src/app/Services/User.service';

@Component({
  selector: 'app-agent',
  templateUrl: './agent.component.html',
  styleUrls: ['./agent.component.css']
})
export class AgentComponent  implements OnInit {
   agents: User[]= [];
   agentForm: FormGroup;
   formSubmitted: boolean = false;
   selectedUser: User | null = null;
  classificationFormVisible = false;
  addFormVisible = false;

  performance: string = '';
  potentiel: string = '';
  classifications: string[] = ['Underperformer', 'EffectiveEmployee', 'InconsistentPlayer', 'TrustedProfessionel', 'CoreEmployee', 'RoughDiamond', 'HighImpactStar', 'FutureStar', 'ConsistentStar'];



   ngOnInit(): void {
    this.getAllAgents();
   }


   constructor(private formBuilder: FormBuilder,private userService: UserService, private router: Router,private http: HttpClient) 
   {
    this.agentForm = this.formBuilder.group({
       nom: ['', [Validators.required, this.noNumbersOrSpecialCharsValidator()]],
      prenom: ['', [Validators.required, this.noNumbersOrSpecialCharsValidator()]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      adresse: ['', Validators.required],
      gender: ['', Validators.required],
      phoneNum: ['', Validators.required]
        });
         }
         noNumbersOrSpecialCharsValidator(): ValidatorFn {
          return (control: AbstractControl): { [key: string]: any } | null => {
            const regex = /^[a-zA-Z\s]*$/; // Regex for letters and whitespace
            const valid = regex.test(control.value);
            return valid ? null : { containsNumbersOrSpecialChars: true };
          };
        }


  getAllAgents(): void {
 
    this.userService.getAllAgents('AGENT').subscribe(
      (data: User[]) => {
        this.agents = data;
      },
      (error) => {
        this.router.navigate(['/login']);

        console.error('Error fetching agents:', error);
      }
    );
  
  } 

  onSubmit(): void {
    if (this.agentForm.valid) {
      const agent: User = this.agentForm.value;
      this.userService.addAgent(agent).subscribe(
        (data: User) => {
          console.log('Agent ajouté avec succès :', data);
          window.location.reload();  
        },
        (error) => {
          console.error('Erreur lors de l\'ajout de l\'agent :', error);
        }
      );
    } else {
      console.error('ERREUR !!!');
    }
  }

  addAgent(agent: User): void {
   
    this.userService.addAgent(agent).subscribe(
      (data: User) => {
        console.log('Agent added successfully:', data);
        // Réactualiser la liste des agents après l'ajout
        this.getAllAgents();
      },
      (error) => {
        console.error('Error adding agent:', error);
      }
    );
  }


  saveClassification9box(id: number, per: string, pot: string): void {
    this.userService.saveClassification9box(id, per, pot).subscribe(
      (data) => {
        console.log('Classification en 9 box sauvegardée avec succès :', data);
        // Mettre à jour l'affichage ou effectuer d'autres actions si nécessaire
      },
      (error) => {
        console.error('Erreur lors de la sauvegarde de la classification en 9 box :', error);
      }
    );
  }

  onSelectUser(user: User): void {
    this.selectedUser = user;
    this.performance = user.performance || '';
    this.potentiel = user.potentiel || '';
    this.classificationFormVisible = true;
  }

  onSaveClassification(): void {
    if (this.selectedUser) {
this.saveClassification9box(this.selectedUser.cin, this.performance, this.potentiel)
      console.log('CIN :', this.selectedUser.cin);
      console.log('Performance :', this.performance);
      console.log('Potentiel :', this.potentiel);
      window.location.reload();  

      // Réinitialisez les valeurs et masquez le formulaire après la sauvegarde
      this.performance = '';
      this.potentiel = '';
      this.classificationFormVisible = false;
      this.selectedUser = null;
    }
  }

  onCancelClassification(): void {
    // Réinitialisez les valeurs et masquez le formulaire
    this.performance = '';
    this.potentiel = '';
    this.classificationFormVisible = false;
    this.selectedUser = null;
  }

  addformvisible(): void {
    this.addFormVisible = true;

  }
  addforminvisible(): void {
    this.addFormVisible = false;

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
          this.getAllAgents();
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
          this.getAllAgents();
        },
        error => {
          console.error('Erreur lors du déblocage de l\'utilisateur :', error);
        }
      );
    } else {

      console.error('Aucun token JWT trouvé dans le localStorage');
    }
  }
 // Méthode pour filtrer les agents par classification
  filterAgentsByClassification(classification: string): User[] {
    return this.agents.filter(agent => agent.classification === classification);
  }

  currentPage: number = 1;
  pageSize: number = 5;
  getUsersForPage(): User[] {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = Math.min(startIndex + this.pageSize, this.agents.length);
    return this.agents.slice(startIndex, endIndex);
  }

}
