import { Product } from './Product';

export class Partner {
  partnerId: number;
  partnerName: string;
  sectorPartner: string;
  emailPartner: string;
  activitePartner: string;
  products: Product[];

  constructor(
    partnerId: number,
    partnerName: string,
    sectorPartner: string,
    emailPartner: string,
    activitePartner: string,
    products: Product[]
  ) {
    this.partnerId = partnerId;
    this.partnerName = partnerName;
    this.sectorPartner = sectorPartner;
    this.emailPartner = emailPartner;
    this.activitePartner = activitePartner;
    this.products = products;
  }

  // other methods...

  // getters and setters...
}
