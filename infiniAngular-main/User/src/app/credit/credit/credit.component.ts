import { Component, MissingTranslationStrategy } from '@angular/core';
import { Credit } from 'src/model/Credit';
import { CreditService } from 'src/app/Services/credit.service';
import { FormBuilder,FormGroup,Validators } from '@angular/forms';
import { OnInit } from '@angular/core';
import { PackC } from 'src/model/PackC';
import { PackCService } from 'src/app/Services/pack-c.service';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-credit',
  templateUrl: './credit.component.html',
  styleUrls: ['./credit.component.css']
})
export class CreditComponent implements OnInit{
  credits: Credit[]=[];
  packs: PackC[]=[];
  creditForm: FormGroup;
  editingCredit: Credit| null=null;
  PackId!: number;
 

  constructor(private formBuilder: FormBuilder, 
    private creditService: CreditService, 
    private packsService:PackCService,
    private route: ActivatedRoute,
  private creditservice: CreditService) {
    // Initialisation de creditForm dans le constructeur
    this.creditForm = this.formBuilder.group({
      name: ['', Validators.required],
      minamount: ['', Validators.required],
      maxamount: ['', Validators.required],
      description: ['', Validators.required],
      packId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
   
    this.loadPacks();
    const idParam = this.route.snapshot.paramMap.get('idP');
    if (idParam !== null) {
      this.PackId = +idParam;
      this.creditservice.findCreditByPack(this.PackId).subscribe(credits => {
        this.credits = credits;
      });
    } else {
      console.error("ID de crédit non fourni dans l'URL.");
    }
  }
 
  loadCredit(): void {
    console.log('OnInit....');
    this.creditService.findAllCredit().subscribe(credits => {
      this.credits = credits;
    });
  }
  
  addCredit():void{
    
      const newCredit : Credit= this.creditForm.value as Credit;
      this.creditService.addCredit(newCredit).subscribe(()=>{
        this.loadCredit();
        this.loadPacks();
        this.creditForm.reset();
      });
  }

  loadPacks(): void {
    this.packsService.findAllpack().subscribe(packs => {
      this.packs = packs;
    });
  }
  cancelEdit(): void{
    this.editingCredit= null;
    this.creditForm.reset();
  }
 
  updateCredit():void{
    if(this.editingCredit && this.creditForm.valid)
      {
        const updateCredit={...this.editingCredit,...this.creditForm.value} as Credit;
        this.creditService.addCreditandAssignToPack(updateCredit, this.PackId).subscribe(()=>{
          this.loadCredit();
          this.creditForm.reset();
          this.editingCredit=null;
        });
  
      }
  }
  editCredit(credit : Credit): void{
    this.editingCredit = credit;
    this.creditForm.patchValue({
      name: credit.name,
      minamount: credit.minamount,
      maxamount: credit.maxamount,
      description: credit.description
    });
  }
  deleteCredit(id: number):void{
    this.creditService.deleteCredit(id).subscribe(()=>{
      this.loadCredit();
    });
  }
  logCreditId(id: number): void {
    console.log('ID du crédit sélectionné :', id);
  }
  addCreditAndAssingToPack(id: number){
    const newCredit : Credit= this.creditForm.value as Credit;
      this.creditService.addCreditandAssignToPack(newCredit, id).subscribe(()=>{
        this.loadCredit();
        this.creditForm.reset();
      });
  }
}
