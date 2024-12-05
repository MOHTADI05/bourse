import { Component } from '@angular/core';
import { Credit } from 'src/model/Credit';
import { CreditService } from 'src/app/Services/credit.service';
import { FormBuilder,FormGroup,Validators } from '@angular/forms';
import { OnInit } from '@angular/core';
import { PackC } from 'src/model/PackC';
import { PackCService } from 'src/app/Services/pack-c.service';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-detail-pack',
  templateUrl: './detail-pack.component.html',
  styleUrls: ['./detail-pack.component.css']
})
export class DetailPackComponent implements OnInit{
  credits: Credit[]=[];
  PackId!: number;
  pack!: PackC;
  selectedCredit: any;

  constructor(private formBuilder: FormBuilder,
    private creditService: CreditService,
    private packsService:PackCService,
    private route: ActivatedRoute,
  private creditservice: CreditService,private router: Router) {}

  toggleCreditDetails(credit: any) {
    this.selectedCredit = this.selectedCredit === credit ? null : credit;
  }
  getImageForCredit(crediId: number): string {
    // Mappez chaque pack à son image correspondante
    switch (crediId) {
      case 1:
        return "assets/images/credit/imagesP.jpg";
      case 2:
        return "assets/images/credit/imagesF.jpg";
      case 3:
        return "assets/images/credit/imagesC.jpg";
      case 4:
        return "assets/images/credit/imagesEntre.jpg";
      case 5:
          return "assets/images/credit/imagesC.jpg";
       case 6:
            return "assets/images/credit/imagesMawsem.jpg";
      case 7:
        return "assets/images/credit/93a159e0-22d4-4bea-aaf0-3107ea0112e8.jpg";
      case 8:
        return "assets/images/credit/364329d3-1f5d-4162-8d90-54a365f265ee.jpg";
      // Ajoutez d'autres cas pour les autres packs si nécessaire
      default:
        return ""; // Retourne une chaîne vide par défaut si aucune correspondance trouvée
    }
  }

  ngOnInit(): void {

    const idParam = this.route.snapshot.paramMap.get('idP');
    if (idParam !== null) {
      this.PackId = +idParam;
      this.creditservice.findCreditByPack(this.PackId).subscribe(credits => {
        this.credits = credits;
      });
      this.packsService.findpack(this.PackId).subscribe(pack=>{
        this.pack=pack;

      })
    } else {
      console.error("ID de crédit non fourni dans l'URL.");
    }
  }

  ouvrirFormulaireDemandeCredit(creditId: number): void {
    this.router.navigate(['/front/demandeCredit', creditId]); // Utilisez la route avec l'ID du crédit
  }
}
