import { Client } from './Client';

export class DueDate {
  idDueDate: number;
  clients: Client[];
  dueDate: Date;
  datePaid: Date;
  amountDue: number;
  amountPaid: number;

  constructor(
    idDueDate: number,
    clients: Client[],
    dueDate: Date,
    datePaid: Date,
    amountDue: number,
    amountPaid: number
  ) {
    this.idDueDate = idDueDate;
    this.clients = clients;
    this.dueDate = dueDate;
    this.datePaid = datePaid;
    this.amountDue = amountDue;
    this.amountPaid = amountPaid;
  }

  // other methods...

  // getters and setters...
}
