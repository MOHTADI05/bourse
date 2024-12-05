export interface ProfilSolvabilite {
    id?: number;
    revenuMensuelNet: number;
    chargeMensuel: number;
    montantDetteTotale: number;
    ratioDetteRevenu: number;
    ancienneteEmploiActuel: number;
    epargneActifsLiquides: number;
    statutEmploi: string; // Vous devez remplacer le type de données en fonction de l'enum correspondant
    chargeFamille: number;
    niveauEducation: string; // Vous devez remplacer le type de données en fonction de l'enum correspondant
    age: number;
    regionResidence: string; // Vous devez remplacer le type de données en fonction de l'enum correspondant
    typeLogement: string; // Vous devez remplacer le type de données en fonction de l'enum correspondant
    situationMatrimoniale: string; // Vous devez remplacer le type de données en fonction de l'enum correspondant
  }