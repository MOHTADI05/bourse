import { Component } from '@angular/core';
import { ViewChild, ElementRef } from '@angular/core';


@Component({
  selector: 'app-hero-section',
  templateUrl: './hero-section.component.html',
  styleUrls: ['./hero-section.component.css']
})
export class HeroSectionComponent {
  @ViewChild('servicesSection') servicesSection!: ElementRef;
  constructor() { }

  scrollToServices() {
    if (this.servicesSection) {
      this.servicesSection.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }
}
