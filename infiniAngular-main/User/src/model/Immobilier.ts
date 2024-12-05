import { Investisment } from './investisment';

export class immobilier{
    immobilierId!: number;
    description!: string;
    pourcentage!: number;
    foundingDate!: Date;
    name!: string;
    location!: string;
    prixtotlal!: number;
    restprix!: number;
    investments!: Investisment[]; 

    constructor(
        name: string,
        description: string,
        pourcentage: number,
        foundingDate: Date,
        location: string,
        prixtotal: number,
        restprix: number
      ) {
        this.name = name;
        this.description = description;
        this.pourcentage = pourcentage;
        this.foundingDate = foundingDate;
        this.location = location;
        this.prixtotlal = prixtotal;
        this.restprix = restprix;
      }



}
