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
