import { Component, OnInit, PipeTransform } from '@angular/core';
import { ViewChild, ElementRef } from '@angular/core';
import { PackC } from 'src/model/PackC';
import { PackCService } from 'src/app/Services/pack-c.service';
@Component({
  selector: 'app-bodyfront',
  templateUrl: './bodyfront.component.html',
  styleUrls: ['./bodyfront.component.css']
})
export class BodyfrontComponent implements OnInit,PipeTransform {
  @ViewChild('servicesSection') servicesSection!: ElementRef;
  packs: PackC[]=[];
  constructor( private packservice: PackCService) {
    // Initialisation de creditForm dans le constructeur

  }
  ngOnInit(): void {
    this.loadCredit();
  }
  loadCredit(): void {
    console.log('OnInit....');
    this.packservice.findAllpack().subscribe(packs => {
      this.packs = packs;
    });
    console.log(this.packs)
  }
  scrollToServices() {
    if (this.servicesSection) {
      this.servicesSection.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }
  getImageForPack(packId: number): string {
    // Mappez chaque pack à son image correspondante
    switch (packId) {
      case 1:
        return "assets/images/credit/Etre-creatif-c-est-ajouter-de-la-vie-a-la-vie-2.jpg";
      case 2:
        return "assets/images/credit/GettyImages-1313131428.png";
      case 3:
        return "assets/images/credit/Engin-agricole-e1492681735106 (1).jpg";
      case 4:
        return "assets/images/credit/Environment.jpg";
        case 5:
        return "assets/images/credit/signature-offre-pret_1.jpg";
      case 7:
        return "assets/images/credit/601109181.jpg";
      case 6:
        return "assets/images/credit/signature-offre-pret_1.jpg";
      // Ajoutez d'autres cas pour les autres packs si nécessaire
      default:
        return ""; // Retourne une chaîne vide par défaut si aucune correspondance trouvée
    }
  }
  transform(arr: any[], size: number): any[] {
    return arr.reduce((acc, curr, index) => {
      const chunkIndex = Math.floor(index / size);
      if (!acc[chunkIndex]) {
        acc[chunkIndex] = [];
      }
      acc[chunkIndex].push(curr);
      return acc;
    }, []);
  }
}
