import { Component, OnInit } from '@angular/core';
import { PackC } from 'src/model/PackC';
import { FormBuilder,FormGroup,Validators } from '@angular/forms';
import { PackCService } from '../Services/pack-c.service';
import { CreditService } from '../Services/credit.service';
import { Credit } from 'src/model/Credit';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pack-c',
  templateUrl: './pack-c.component.html',
  styleUrls: ['./pack-c.component.css']
})
export class PackCComponent implements OnInit {
  packs: PackC[]=[];
  credits: Credit[]=[];
  packForm: FormGroup;
  editingPack: PackC| null=null;
  typePacks: string[]=['AGRICOLE',
    'NONAGRICOLE',
    'CREATION',
    'ENVIRONEMENT',
    'FIDELISATION',
    'AUTRE'];
 

  constructor(private formBuilder: FormBuilder, private packservice: PackCService, private creditService: CreditService,private router: Router) {
    // Initialisation de creditForm dans le constructeur
    this.packForm = this.formBuilder.group({
      name: ['', Validators.required],
      typePack: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadCredit();
    
  }
 
  loadCredit(): void {
    console.log('OnInit....');
    this.packservice.findAllpack().subscribe(packs => {
      this.packs = packs;
    });
  }
 /* AfficherCreditByPack(id: number): void {
   
    this.creditService.findCreditByPack(id).subscribe(credits => {
      this.credits = credits;
    });
  }*/
  
  addCredit():void{
    
      const newCredit : PackC= this.packForm.value as PackC;
      this.packservice.addCredit(newCredit).subscribe(()=>{
        this.loadCredit();
        this.packForm.reset();
      });

    
      
  
  }
  cancelEdit(): void{
    this.editingPack= null;
    this.packForm.reset();
  }
 
  updateCredit():void{
    if(this.editingPack && this.packForm.valid)
      {
        const updateCredit={...this.editingPack,...this.packForm.value} as PackC;
        this.packservice.addCredit(updateCredit).subscribe(()=>{
          this.loadCredit();
          this.packForm.reset();
          this.editingPack=null;
        });
  
      }
  }
  editCredit(pack : PackC): void{
    this.editingPack = pack;
    this.packForm.patchValue({
      name: pack.name,
      TypePack: pack.typePack,
      description: pack.description
    });
  }
  deleteCredit(id: number):void{
    this.packservice.deleteCredit(id).subscribe(()=>{
      this.loadCredit();
    });
  }
  logCreditId(id: number): void {
    console.log('ID du crédit sélectionné :', id);
  }
  ouvrirFormulaireDemandeCredit(creditId: number): void {
    this.router.navigate(['/demandeCredit', creditId]); // Utilisez la route avec l'ID du crédit
  }
  ajouterPack(): void {
    this.router.navigate(['/back/addPack']); 
  }

  AfficherCreditByPack(idP:number):void{
    this.router.navigate(['/back/credit', idP]);
  }
  ouvrirFormulaireEditPack(PackId: number): void {
    this.router.navigate(['/back/addPack', PackId]); // Utilisez la route avec l'ID du crédit
  }
}
