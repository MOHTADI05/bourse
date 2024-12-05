import { User } from './User';
import { immobilier } from './Immobilier';
export class Demand {
  id!: number;
  user!: User;           // Relationship to User
  immobilier!: immobilier;   // Relationship to Immobilier
  amountRequested!: number;
}
