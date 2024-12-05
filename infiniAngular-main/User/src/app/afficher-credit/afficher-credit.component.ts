import { Component } from '@angular/core';
import { Credit } from 'src/model/Credit';
import { CreditService } from 'src/app/Services/credit.service';
import { FormBuilder,FormGroup,Validators } from '@angular/forms';
import { OnInit } from '@angular/core';
import { PackC } from 'src/model/PackC';
import { PackCService } from 'src/app/Services/pack-c.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-afficher-credit',
  templateUrl: './afficher-credit.component.html',
  styleUrls: ['./afficher-credit.component.css']
})
export class AfficherCreditComponent implements OnInit{
  credits: Credit[]=[];
  packs: PackC[]=[];
  creditForm: FormGroup;
  editingCredit: Credit| null=null;
  PackId!: number;
 

  constructor(private formBuilder: FormBuilder, 
    private creditService: CreditService, 
    private packsService:PackCService,
    private route: ActivatedRoute,
  private creditservice: CreditService,
  private router: Router) {
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
    this.loadCredit();
    this.loadPacks();
    
    
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
        this.creditService.addCredit(updateCredit).subscribe(()=>{
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
  ajouterCredit(): void {
    this.router.navigate(['/back/addCredit']); 
  }
  ouvrirFormulaireEditCredit(creditId: number): void {
    this.router.navigate(['/back/addCredit', creditId]); // Utilisez la route avec l'ID du crédit
  }
}
