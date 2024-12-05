
import { TypeUser } from './TypeUser';
import { Account } from './Account';
 import { Product } from './Product';
 import { DemandeCredit } from './DemandeCredit';
import { TalentReview } from './TalentReview';

export class User {
    cin!: number;
    nom: string;
    prenom: string;
    age: number;
    email: string;
    password: string;
    adresse: string;
    gender: string;
    phoneNum: number;
    role: TypeUser;
    isbloked: boolean;
    code: number;
    performance: string;
    potentiel: string;
    netIncome: number;
    classification: TalentReview;
    creditAuthorization: boolean;
    credits: Set<DemandeCredit>;
    acc: Account;

    constructor() {
      this.cin = 0;
      this.nom = '';
      this.prenom = '';
      this.age = 0;
      this.email = '';
      this.password = '';
      this.adresse = '';
      this.gender = '';
      this.phoneNum = 0;
      this.role = TypeUser.ROLE_CLIENT;
      this.isbloked = false;
      this.code = 0;
      this.performance = '';
      this.potentiel = '';
      this.netIncome = 0;
      this.classification = TalentReview.LOW;
      this.creditAuthorization = false;
      this.credits = new Set<DemandeCredit>();
      // @ts-ignore
      this.acc = new Account();
    }

    /*constructor(
        cin: number,
        nom: string,
        prenom: string,
        age: number,
        email: string,
        password: string,
        adresse: string,
        gender: string,
        phoneNum: number,
        role: TypeUser,
        isbloked: boolean,
        code: number,
        performance: string,
        potentiel: string,
        netIncome: number,
        classification: TalentReview,
        creditAuthorization: boolean,
        credits: Set<DemandeCredit>,
        acc: Account,
        notifications: Notification[],
        product: Product,
      ) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = email;
        this.password = password;
        this.adresse = adresse;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.role = role;
        this.isbloked = isbloked;
        this.code = code;
        this.performance = performance;
        this.potentiel = potentiel;
        this.netIncome = netIncome;
        this.classification = classification;
        this.creditAuthorization = creditAuthorization;
        this.credits = credits;
        this.acc = acc;
        this.product = product;
       }*/
    }
