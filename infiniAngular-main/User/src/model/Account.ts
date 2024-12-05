import { User } from "./User";
export class Account {
  rib!: number;
  solde!: number;
  openDate!: Date;
  expirationDate!: Date;
  user!: User;

  constructor(rib: number, solde: number, openDate: Date, expirationDate: Date, user: User) {
      this.rib = rib;
      this.solde = solde;
      this.openDate = openDate;
      this.expirationDate = expirationDate;
      this.user = user;
  }

  addFunds(amount: number): void {
      this.solde += amount;
  }
}
