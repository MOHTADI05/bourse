import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, finalize } from 'rxjs';
import { Product } from 'src/model/Product';
import { Partner } from 'src/model/Partner';
import { FormGroup } from '@angular/forms';
@Injectable({
  providedIn: 'root'
})
export class ProductService {
  readonly baseUrl = 'http://localhost:8084/products'; // L'URL de votre API Spring Boot

  constructor(private http: HttpClient) { }

  
  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.baseUrl+'/get');
  }

  getProductById(productId: number): Observable<Product> {
    const url = `${this.baseUrl+'/getbyid'}/${productId}`;
    return this.http.get<Product>(url);
  }

  saveProduct(product: Product): Observable<Product> {
    const formData: FormData = new FormData();
    console.log('Product:');
    formData.append('productName', product.productName);
    formData.append('description', product.description);
    formData.append('valueProduct', product.valueProduct.toString());
    formData.append('valueEXC', product.valueEXC.toString());
    formData.append('isAvailable', product.isAvailable);
    formData.append('productType', product.productType);
    console.log('Product:');
    formData.append('image', product.image, product.image.name); // Append image as multipart file

    console.log('Product:');
    return this.http.post<Product>(`${this.baseUrl}/create`, formData);
  }

  deleteProduct(productId: number): Observable<void> {
    const url = `${this.baseUrl+'/delete'}/${productId}`;
    return this.http.delete<void>(url);
  }

  saveOrUpdateProduct(product: Product): Observable<Product> {
    const url = `${this.baseUrl+'/create'}`;
    return this.http.post<Product>(url, product);
  }

  updateProduct(product: Product, partnerId: number): Observable<Product> {
    const url = `${this.baseUrl}/updateProduct/${partnerId}`;
    return this.http.put<Product>(url, product);
  }
  generateProductPDFReport(): Observable<Blob> {
    return this.http.get(`${this.baseUrl+'/PDF'}`, { responseType: 'blob' });
  }

  buyProduct(clientId: number, productId: number, periode: number): Observable<any> {
    const url = `${this.baseUrl+'/buy/'}${clientId}/${productId}/${periode}`;
    return this.http.post<any>(url, null); // Vous pouvez envoyer null car les données sont envoyées dans l'URL
  }

}
