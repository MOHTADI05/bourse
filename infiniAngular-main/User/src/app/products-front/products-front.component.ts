import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import {  OnInit } from '@angular/core';
import { Product } from 'src/model/Product';
import { ProductService } from '../Services/product.service';
import { AuthService } from '../Services/auth-service.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-products-front',
  templateUrl: './products-front.component.html',
  styleUrls: ['./products-front.component.css']
})
export class ProductsFrontComponent implements OnInit {
  products: Product[] = [];
  id: number;
  productId: number;
  periode: number;

  constructor( private productService: ProductService , private authService: AuthService) {
    this.id=0;
    this.productId=0;
    this.periode=0;
  }

  ngOnInit(): void {
    this.loadProducts();

    const token = localStorage.getItem('jwt');

  if (token) {
    this.authService.getUserByToken(token).subscribe(
      id => {
        console.log('ID utilisateur récupéré:', id);
        // HOUNI HOT FONCTION LI TEST7AK EL ID W 3AYETLEL ID HOUNI
        this.id = id.cin;
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


  loadProducts(): void {
    console.log('OnInit....');
    this.productService.getAllProducts().subscribe(products => {
      this.products = products;
      console.log('Products:', this.products);
    });
  }
  tobuyProduct(product: Product): void {
    this.productId = product.productId;

    Swal.fire({
      title: 'Enter Periode',
      input: 'number',
      inputPlaceholder: 'Enter the periode',
      showCancelButton: true,
      confirmButtonText: 'Confirm',
      cancelButtonText: 'Cancel',
      inputValidator: (value) => {
        // Validate if the entered value is a number
        if (!value || isNaN(Number(value))) {
          return 'Please enter a valid number';
        }
        return null; // Add a return statement to ensure a value is always returned
      }
    }).then((result) => {
      if (result.isConfirmed) {
        // Set the periode value if the user confirmed
        this.periode = Number(result.value);
        this.buyProduct(this.periode);
      }
    });
  }


  buyProduct(periode: any): void {
    const clientId = this.id;
    const productId = this.productId;
    
    // For example, logging the values to the console
    console.log("Client ID:", clientId);
    console.log("Product ID:", productId);
    console.log("Period:", periode);
      // ... Récupérez les autres valeurs du formulaire ici ...
  
    // Utilisez les valeurs du formulaire pour effectuer l'achat du produit
    this.productService.buyProduct(clientId, productId, periode).subscribe(data => {
      console.log(data);
      window.open(data.paymentLink, '_blank');
    }, error => {
      if (error.status === 406) {
        // Display a SweetAlert to inform the user that they can't afford the product
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'You can\'t afford this product!',
        });
      } else {
        console.error('Error buying product:', error);
      }
    });
  }

}
