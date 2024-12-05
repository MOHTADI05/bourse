import { Partner } from './Partner';
import { User } from './User';
//import {ProductType} from './ProductType';
export enum ProductType {
  CAR = 'CAR',
  REAL_ESTATE = 'REAL_ESTATE',
  FIELD = 'FIELD'
}

export class Product {
    productId: number;
    productName: string;
    description: string;
    valueProduct: number;
    valueEXC: number;
    isAvailable: string;
    partner: Partner;
    productType: ProductType;
    productOwner: User;
    image: File;
    filename: string;
  
    constructor(
      productId: number,
      productName: string,
      description: string,
      valueProduct: number,
      valueEXC: number,
      isAvailable: string,
      partner: Partner,
      productType: ProductType,
      productOwner: User,
      filename: string,
      image: File
    ) {
      this.productId = productId;
      this.productName = productName;
      this.description = description;
      this.valueProduct = valueProduct;
      this.valueEXC = valueEXC;
      this.isAvailable = isAvailable;
      this.partner = partner;
      this.productType = productType;
      this.productOwner = productOwner;
      this.image = image;
      this.filename = filename;
    }
  
    // other methods...
  
    // getters and setters...
  }