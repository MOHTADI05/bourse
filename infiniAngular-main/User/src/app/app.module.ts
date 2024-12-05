


import { AgentComponent } from './Users/agent/agent.component';
import { HeroSectionComponent } from './frontOffice/hero-section/hero-section.component';
import { ProfilsolvaComponent } from './Users/profilsolva/profilsolva.component';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { UserComponent } from './user/user.component';
import { LoginComponent } from './login/login.component';
import { UserService } from './Services/User.service';
import { FormsModule } from '@angular/forms';
import { AllTemplatefrontComponent } from './frontOffice/all-templatefront/all-templatefront.component';
import { FooterFrontComponent } from './frontOffice/footer-front/footer-front.component';
 import { HeaderFrontComponent } from './frontOffice/header-front/header-front.component';
 import { NavbarComponent } from './backOffice/navbar/navbar.component';
import { HeaderbackComponent } from './backOffice/headerback/headerback.component';
import { AllTemplateBackComponent } from './backOffice/all-template-back/all-template-back.component';
import { BodyComponent } from './backOffice/body/body.component';
import { RegisterComponent } from './register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ResetpasswordComponent } from './resetpassword/resetpassword.component';
import { BodyfrontComponent } from './frontOffice/bodyfront/bodyfront.component';
import { SidebarComponent } from './backOffice/sidebar/sidebar.component';
import { FooterbackComponent } from './backOffice/footerback/footerback.component';
import { CreditComponent } from './credit/credit/credit.component';
import { PackCComponent } from './pack-c/pack-c.component';
import { DemandeCreditComponent } from './demande-credit/demande-credit.component';
import { SimulateComponent } from './simulate/simulate.component';
import { CustomDialogComponentComponent } from './custom-dialog-component/custom-dialog-component.component';
import { AddPackComponent } from './add-pack/add-pack.component';
import { AddCreditComponent } from './add-credit/add-credit.component';
import { AfficherCreditComponent } from './afficher-credit/afficher-credit.component';
import { PackFrontComponent } from './pack-front/pack-front.component';
import { DetailPackComponent } from './detail-pack/detail-pack.component';
import { ChunkPipe } from './ChunkPipe';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { PopUpComponent } from './Users/pop-up/pop-up.component';
import { PopUpCreditComponent } from './pop-up-credit/pop-up-credit.component';
import { InterfaceClientComponent } from './interface-client/interface-client.component';
import {LoanSimulationComponent} from "./LoanSimulationComponent/investsimul.component";
import {InvestmentManagementComponent} from "./investment-management/investment-management.component";
import {InvHistoryComponent} from "./invhistory/invhistory.component";
import {ImmobilierComponent} from "./immobilier/credit.component";
import {ImmobilierFontComponent} from "./immobilierfront/credit.component";
import {SellToDemandComponent} from "./sell-to-demand/sell-to-demand.component";
import {AreaChartComponent} from "./area-chart/area-chart.component";
import {ConfirmpaymentComponent} from "./confirmpayment/confirmpayment.component";
import {PartnerComponent} from "./partner/partner.component";
import {PaymentsBackComponent} from "./payments-back/payments-back.component";
import {ProductsBackComponent} from "./products-back/products-back.component";
import {PaymentComponent} from "./payment/payment.component";
import {ProductsFrontComponent} from "./products-front/products-front.component";
import {DemandesimulateurComponent} from "./demandesimulateur/demandesimulateur.component";
import { AddBankAccountComponent } from './add-bank-account/add-bank-account.component';
import { AddTransactionComponent } from './add-transaction/add-transaction.component';
import { BankAccountComponent } from './bank-account/bank-account.component';
import { BankAccountBonusComponent } from './bank-account-bonus/bank-account-bonus.component';
import { FlipcardComponent } from './flipcard/flipcard.component';
import { StatTransactionComponent } from './stat-transaction/stat-transaction.component';
import { TransactionComponent } from './transaction/transaction.component';
import { UserWalletComponent } from './user-wallet/user-wallet.component';
import { WalletTransactionComponent } from './wallet-transaction/wallet-transaction.component';
import { UpdateBankAccountComponent } from './update-bank-account/update-bank-account.component';
import { NewsComponent } from './news/news.component';




@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    LoginComponent,
    AllTemplatefrontComponent,
    FooterFrontComponent,
    HeaderFrontComponent,
    NavbarComponent,
    HeaderbackComponent,
    AllTemplateBackComponent,
    BodyComponent,
    RegisterComponent,
    ResetpasswordComponent,
    BodyfrontComponent,
    SidebarComponent,
    FooterbackComponent,
    AgentComponent,
    HeroSectionComponent,
    ProfilsolvaComponent,
    CreditComponent,
    PackCComponent,
    DemandeCreditComponent,
    SimulateComponent,
    CustomDialogComponentComponent,
    AddPackComponent,
    AddCreditComponent,
    AfficherCreditComponent,
    PackFrontComponent,
    DetailPackComponent,
    ChunkPipe,
    PopUpComponent,
    PopUpCreditComponent,
    InterfaceClientComponent,
    LoanSimulationComponent,
    InvestmentManagementComponent,
    InvHistoryComponent,
    ImmobilierComponent,
    ImmobilierFontComponent,
    SellToDemandComponent,
    AreaChartComponent,
    ConfirmpaymentComponent,
    ProductsFrontComponent,
    PaymentComponent,
    ProductsBackComponent,
    PaymentsBackComponent,
    PartnerComponent,
    DemandesimulateurComponent,
    AddBankAccountComponent,
    AddTransactionComponent,
    BankAccountComponent,
    BankAccountBonusComponent,
    FlipcardComponent,
    StatTransactionComponent,
    TransactionComponent,
    UserWalletComponent,
    WalletTransactionComponent,
    UpdateBankAccountComponent,
    NewsComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
     FormsModule ,
     ToastrModule.forRoot({
      timeOut: 150000, // 15 seconds
      closeButton: true,
      progressBar: true,
    }),
     BrowserAnimationsModule,
     MatButtonModule,
    MatDialogModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
