
import { Component, OnInit } from '@angular/core';
import { PackC } from 'src/model/PackC';
import { FormBuilder,FormGroup,Validators } from '@angular/forms';
import { PackCService } from '../Services/pack-c.service';
import { CreditService } from '../Services/credit.service';
import { Credit } from 'src/model/Credit';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-pack',
  templateUrl: './add-pack.component.html',
  styleUrls: ['./add-pack.component.css']
})
export class AddPackComponent {
  packs: PackC[]=[];
  credits: Credit[]=[];
  packForm: FormGroup;
  PackId!:number;
  Pack!: PackC;
  editingPack: PackC| null=null;
  typePacks: string[]=['AGRICOLE',
    'NONAGRICOLE',
    'CREATION',
    'ENVIRONEMENT',
    'FIDELISATION',
    'AUTRE'];
 

  constructor(private formBuilder: FormBuilder, private packservice: PackCService, 
    private creditService: CreditService,private route: ActivatedRoute,) {
    // Initialisation de creditForm dans le constructeur
    this.packForm = this.formBuilder.group({
      name: ['', Validators.required],
      typePack: ['', Validators.required],
      description: ['', Validators.required]
    });
  }
  loadCredit(): void {
    console.log('OnInit....');
    this.packservice.findAllpack().subscribe(packs => {
      this.packs = packs;
    });
  }
 ngOnInit(){
  const idParam = this.route.snapshot.paramMap.get('idP');
  if (idParam !== null) {
    this.PackId = +idParam;
    this.packservice.findpack(this.PackId).subscribe(pack => {
      this.Pack = pack;
      this.editingPack = pack;
      this.packForm.patchValue({
        name: pack.name,
        TypePack: pack.typePack,
        description: pack.description
      });
    });
    
    
  } else {
    console.error("ID de crédit non fourni dans l'URL.");
  }
 }
  
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

}
