import { Component } from '@angular/core';
import { UserService } from '../Services/User.service';
import { User } from 'src/model/User';
import { Router } from '@angular/router';

import { FormBuilder,FormGroup,Validator, Validators,ValidatorFn,AbstractControl } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  user: User = new User(); // Créez un nouvel objet User pour stocker les données du nouvel utilisateur
  userform: FormGroup;
  formSubmitted = false; // Ajoutez une variable pour suivre si le formulaire a été soumis

  constructor(private FormBuilder: FormBuilder,private userService: UserService, private router: Router){
    this.userform=this.FormBuilder.group({
      nom:['',[Validators.required, this.noNumbersOrSpecialCharsValidator()]],
      prenom:['',[Validators.required, this.noNumbersOrSpecialCharsValidator()]],
      age: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      adresse: ['', Validators.required],
      gender: ['', Validators.required],
      phoneNum: ['', Validators.required]

    })
  }

  noNumbersOrSpecialCharsValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const regex = /^[a-zA-Z\s]*$/; // Regex for letters and whitespace
      const valid = regex.test(control.value);
      return valid ? null : { containsNumbersOrSpecialChars: true };
    };
  }

 // constructor(private userService: UserService) { }

 get f() { return this.userform.controls; }

  registerUser(): void {
    
    this.formSubmitted = true; // Marquez le formulaire comme soumis

    // Vérifiez si le formulaire est valide avant de soumettre
    if (this.userform.invalid) {
      return  ; // Sortez de la fonction si le formulaire est invalide
    }


    const newUser: User = this.userform.value as User;
    this.userService.register(newUser)
      .subscribe(
        response => {
          console.log('Utilisateur enregistré avec succès :', response);
          this.router.navigate(['/login']);
        },
        error => {
          console.error('Erreur lors de l\'enregistrement de l\'utilisateur :', error);
          // Traitez les erreurs ici (par exemple, affichez un message d'erreur à l'utilisateur)
        }
      );
  }

}
