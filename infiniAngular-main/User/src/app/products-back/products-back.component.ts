import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import {  OnInit } from '@angular/core';
import { Product , ProductType } from 'src/model/Product';
import { ProductService } from '../Services/product.service';
import { AuthService } from '../Services/auth-service.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-products-back',
  templateUrl: './products-back.component.html',
  styleUrls: ['./products-back.component.css']
})
export class ProductsBackComponent implements OnInit {
  products: Product[] = [];
  productForm: FormGroup;
  buyForm: FormGroup;
  editingProduct: Product | null = null;
  productTypes: ProductType[] = Object.values(ProductType); // Initialisez productTypes avec les valeurs de l'énumération
  constructor(private formBuilder: FormBuilder, private productService: ProductService) {
    // Initialisation de productForm dans le constructeur
    this.productForm = this.formBuilder.group({
      productName: ['', Validators.required],
      description: ['', Validators.required],
      valueProduct: ['', Validators.required],
      valueEXC: ['', Validators.required],
      isAvailable: ['', Validators.required],
      productType: ['', Validators.required] ,// Ajoutez productType au formulaire
      image: [''] // Add input for image


      // Ajoutez d'autres champs ici selon vos besoins
    });
    this.buyForm = this.formBuilder.group({
      clientId: [{value: '1', disabled: true}, Validators.required],
      clientName: [{value: 'name', disabled: true}, Validators.required],
      netIncome: [{value: '100000', disabled: true}, Validators.required],
      productId: [{value: '', disabled: true}, Validators.required],
      productValue: [{value: '', disabled: true}, Validators.required],
      periode: ['', Validators.required],
    });
  }
  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    console.log('OnInit....');
    this.productService.getAllProducts().subscribe(products => {
      this.products = products;
      console.log('Products:', this.products);
    });
  }
  /*
  addProduct(): Observable<Product> {
    const partner = new Partner(
      this.productForm.value.partnerId,
      this.productForm.value.partnerName,
      this.productForm.value.partnerDescription,
      this.productForm.value.partnerType,
      this.productForm.value.partnerOwner,
      this.productForm.value.partnerIsAvailable
    );

    const newProduct = new Product(
      this.productForm.value.productId,
      this.productForm.value.productName,
      this.productForm.value.description,
      this.productForm.value.valueProduct,
      this.productForm.value.valueEXC,
      this.productForm.value.isAvailable,
      partner,
      this.productForm.value.productType,
      this.productForm.value.productOwner
    );

    this.productService.saveProduct(newProduct).subscribe(() => {
      console.log('Product added successfully');
      this.loadProducts(); // reload the products after a new one is added
      this.productForm.reset(); // reset the form
    }, (error: any) => {
      console.error('Error adding product:', error);
    });

    return this.productService.saveProduct(newProduct);
  }
*/
addProduct(): void {
  const newProduct: Product = this.productForm.value as Product;
  this.productService.saveProduct(newProduct).subscribe(() => {
    this.loadProducts();
    this.productForm.reset();
  });
}
  cancelEdit(): void {
    this.editingProduct = null;
    this.productForm.reset();
  }

  onImageChange(event: any): void {
    if (event.target.files && event.target.files.length) {
      console.log("chnage");
      const file = event.target.files[0];
      console.log('File size:', file.size, 'bytes');
      this.productForm.patchValue({
        image: file
      });
      console.log("DDDD");

    }
  }

  updateProduct(): void {
    if (this.editingProduct && this.productForm.valid)
       {
        const newProduct: Product = this.productForm.value as Product;
  this.productService.saveProduct(newProduct).subscribe(() => {
    this.loadProducts();
    this.productForm.reset();
    this.editingProduct = null;
});

    }
  }

  editProduct(product: Product): void {
    this.editingProduct = product;
    this.productForm.patchValue({
      productName: product.productName,
      description: product.description,
      valueProduct: product.valueProduct,
      valueEXC: product.valueEXC,
      isAvailable: product.isAvailable
      // Patchez d'autres champs ici selon vos besoins
    });
  }

  deleteProduct(productId: number): void {
    this.productService.deleteProduct(productId).subscribe(() => {
      console.log('Product deleted successfully');
    }, error => {
      console.error('Error deleting product:', error);
    });
  }/*
  downloadProductReport(): void {
    this.productService.generateProductPDFReport().subscribe((blob: Blob) => {
      // Gérer le téléchargement du rapport PDF ici
    });
  }*/
downloadProductReport(): void {
  this.productService.generateProductPDFReport().subscribe((blob: Blob) => {
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', 'Products.pdf');
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  });
}

}
