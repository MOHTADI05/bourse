export class Amortissement{
     idA!:number;
      montantR!: number;
      interest!:number;
     amortissement!:number;
     mensualite!:number;
     constructor( montantR: number, interest: number, amortissement: number, mensualite: number) {
          this.montantR = montantR;
          this.interest = interest;
          this.amortissement = amortissement;
          this.mensualite = mensualite;
      }
}