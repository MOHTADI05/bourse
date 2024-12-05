import { Component, OnInit } from '@angular/core';
import {InvHistory} from "../../model/InvHistory";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {InvHistoryService} from "../Services/invhistoryservice.service";

@Component({
  selector: 'app-inv-history',
  templateUrl: 'invhistory.component.html',
  styleUrls: ['invhistory.component.css']
})
export class InvHistoryComponent implements OnInit {
  invHistories: InvHistory[] = [];
  invHistoriesForm:FormGroup;
  constructor(private invHistoryService: InvHistoryService, private fb: FormBuilder) {
    this.invHistoriesForm = this.fb.group({
      hId: ['', Validators.required],
      amount: ['', Validators.required],
      transactionType: [null, Validators.required],
      invDate: ['', Validators.required],
      imbId: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.loadInvHistories();
  }

  loadInvHistories(): void {
    this.invHistoryService.getAllInvHistory().subscribe({
      next: (data) => {
        this.invHistories = data;
      },
      error: (error) => {
        console.error('There was an error!', error);
      }
    });
  }


}
