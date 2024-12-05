import { User } from './User'
import { immobilier } from './Immobilier';
import { InvHistory } from './InvHistory';

export class Investisment {
  inv_id!: number;
  amount!: number;
  inv_date!: Date;
  inv_owner!: User;
  imb!: immobilier;
  invH!: InvHistory[];
}
