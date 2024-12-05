import { TypeCredit } from "./TypeCredit";

export class DemandeCredit{
  idDemandecredit!: number;
  amount!: number;
  minamount!:number;
  maxamount!:number;
  name!:String;
  monthlyPaymentDate!: Date;
  demandedate!:Date;
  obtainingdate!:Date;
  state!:Boolean;
  mounthlypayment!:number;
  year!:number;
  interest!: number;
  typeCredit!: TypeCredit;
  Risk!: number;
  completed!: Boolean ;
  Reason!: String;
  differe!: Boolean;
  // PERIODE DE DIFFERE
  DIFF_period!: number;
}
