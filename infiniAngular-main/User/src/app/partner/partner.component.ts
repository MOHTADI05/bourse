import { Component } from '@angular/core';
import { Partner } from 'src/model/Partner';
import { PartnerService } from '../Services/partner.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-partner',
  templateUrl: './partner.component.html',
  styleUrls: ['./partner.component.css']
})
export class PartnerComponent {
  partners: Partner[] = [];
  partnerForm: FormGroup;
  editingPartner: Partner | null = null;

  constructor(private formBuilder: FormBuilder, private partnerService: PartnerService) {
    this.partnerForm = this.formBuilder.group({
      partnerName: ['', Validators.required],
      sectorPartner: ['', Validators.required],
      emailPartner: ['', [Validators.required, Validators.email]],
      activitePartner: ['']
      // Ajoutez d'autres champs ici selon vos besoins
    });
  }

  ngOnInit(): void {
    this.loadPartners();
  }

  loadPartners(): void {
    this.partnerService.getAllPartners().subscribe(partners => {
      this.partners = partners;
    });
  }

  addPartner(): void {
    const newPartner: Partner = this.partnerForm.value as Partner;
    this.partnerService.saveOrUpdatePartner(newPartner).subscribe(() => {
      this.loadPartners();
      this.partnerForm.reset();
    });
  }

  cancelEdit(): void {
    this.editingPartner = null;
    this.partnerForm.reset();
  }

  updatePartner(): void {
    if (this.editingPartner && this.partnerForm.valid) {
      const updatePartner = { ...this.editingPartner, ...this.partnerForm.value } as Partner;
      this.partnerService.saveOrUpdatePartner(updatePartner).subscribe(() => {
        this.loadPartners();
        this.partnerForm.reset();
        this.editingPartner = null;
      });
    }
  }

  editPartner(partner: Partner): void {
    this.editingPartner = partner;
    this.partnerForm.patchValue({
      partnerName: partner.partnerName,
      sectorPartner: partner.sectorPartner,
      emailPartner: partner.emailPartner,
      activitePartner: partner.activitePartner
      // Patchez d'autres champs ici selon vos besoins
    });
  }

  deletePartner(id: number): void {
    this.partnerService.deletePartnerById(id).subscribe(() => {
      this.loadPartners();
    });
  }

}
