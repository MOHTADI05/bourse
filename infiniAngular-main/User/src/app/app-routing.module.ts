import { RegisterComponent } from './register/register.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { FooterFrontComponent } from './frontOffice/footer-front/footer-front.component';
import { AllTemplatefrontComponent } from './frontOffice/all-templatefront/all-templatefront.component';
import { AllTemplateBackComponent } from './backOffice/all-template-back/all-template-back.component';
import { ResetpasswordComponent } from './resetpassword/resetpassword.component';
import { AgentComponent } from './Users/agent/agent.component';
import { ProfilsolvaComponent } from './Users/profilsolva/profilsolva.component';
import { BodyfrontComponent } from './frontOffice/bodyfront/bodyfront.component';
import { AfficherCreditComponent } from './afficher-credit/afficher-credit.component';
import { PackCComponent } from './pack-c/pack-c.component';
import { AddCreditComponent } from './add-credit/add-credit.component';
import { AddPackComponent } from './add-pack/add-pack.component';
import { CreditComponent } from './credit/credit/credit.component';
import { DetailPackComponent } from './detail-pack/detail-pack.component';
import { SimulateComponent } from './simulate/simulate.component';
import { DemandeCreditComponent } from './demande-credit/demande-credit.component';
import { AuthGuard } from './auth.guard';
import { InterfaceClientComponent } from './interface-client/interface-client.component';
import {InvHistoryComponent} from "./invhistory/invhistory.component";
import {ImmobilierComponent} from "./immobilier/credit.component";
import {ImmobilierFontComponent} from "./immobilierfront/credit.component";
import {LoanSimulationComponent} from "./LoanSimulationComponent/investsimul.component";
import {SellToDemandComponent} from "./sell-to-demand/sell-to-demand.component";
import {AreaChartComponent} from "./area-chart/area-chart.component";
import {ProductsBackComponent} from "./products-back/products-back.component";
import {PaymentsBackComponent} from "./payments-back/payments-back.component";
import {PartnerComponent} from "./partner/partner.component";
import {ProductsFrontComponent} from "./products-front/products-front.component";
import {ConfirmpaymentComponent} from "./confirmpayment/confirmpayment.component";
import {PaymentComponent} from "./payment/payment.component";
import {DemandesimulateurComponent} from "./demandesimulateur/demandesimulateur.component";
import { TransactionComponent } from './transaction/transaction.component';
import { AddTransactionComponent } from './add-transaction/add-transaction.component';
import { BankAccountComponent } from './bank-account/bank-account.component';
import { StatTransactionComponent } from './stat-transaction/stat-transaction.component';
import { AddBankAccountComponent } from './add-bank-account/add-bank-account.component';
import { UserWalletComponent } from './user-wallet/user-wallet.component';
import { UpdateBankAccountComponent } from './update-bank-account/update-bank-account.component';
import { BankAccountBonusComponent } from './bank-account-bonus/bank-account-bonus.component';
import { WalletTransactionComponent } from './wallet-transaction/wallet-transaction.component';
import { NewsComponent } from './news/news.component';
const routes: Routes = [



  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Redirigez vers la page de connexion par défaut
  { path: 'login', component: LoginComponent }, // Définissez le chemin pour le composant LoginComponent
  { path: 'register', component: RegisterComponent }, // Définissez le chemin pour le composant LoginComponent
  { path: 'restpwd', component: ResetpasswordComponent }, // Définissez le chemin pour le composant LoginComponent
  { path: 'app-area-chart', component: AreaChartComponent },
  

  
 
  { path: 'stattransaction', component: StatTransactionComponent },
  
 
  {path: 'UpdateBankAccount', component: UpdateBankAccountComponent},
  {path: 'bonus', component:BankAccountBonusComponent},
  {path: 'one', component:WalletTransactionComponent},




  { path: 'back', component: AllTemplateBackComponent,
      children:[
          { path: 'listUser', component: UserComponent , canActivate: [AuthGuard]},
          { path: 'Agent', component: AgentComponent, canActivate: [AuthGuard] },
          {path:"AfficherCredit", component:AfficherCreditComponent, canActivate: [AuthGuard]},
          {path:"pack", component:PackCComponent, canActivate: [AuthGuard]},
          {path:"addCredit", component:AddCreditComponent, canActivate: [AuthGuard]},
          {path:"addCredit/:idcredit", component:AddCreditComponent, canActivate: [AuthGuard]},
          {path:"addPack", component:AddPackComponent, canActivate: [AuthGuard]},
          { path:"credit/:idP", component:CreditComponent, canActivate: [AuthGuard]},
          { path:"credit", component:CreditComponent, canActivate: [AuthGuard]},
          {path:"addPack/:idP", component:AddPackComponent, canActivate: [AuthGuard]},
          { path: 'investment', component: InvHistoryComponent , canActivate: [AuthGuard]},
          { path: 'investment/immobilier', component: ImmobilierComponent, canActivate: [AuthGuard] },
          { path: 'bankAccounts', component: BankAccountComponent, canActivate: [AuthGuard] },
          
          { path: 'transaction', component: TransactionComponent, canActivate: [AuthGuard]  },
          { path: 'products', component: ProductsBackComponent},
          { path: 'payments', component: PaymentsBackComponent},
          { path: 'partners', component: PartnerComponent}

      ]
    , canActivate: [AuthGuard]},
  { path: 'front', component: AllTemplatefrontComponent,
  // Appliquer le garde de route à toutes les routes enfants

       children:[
          { path: 'body', component: BodyfrontComponent },
          { path: 'profilsolva', component: ProfilsolvaComponent },
          { path: 'detailPack/:idP', component: DetailPackComponent , canActivate: [AuthGuard] },
          { path: 'credit', component: CreditComponent , canActivate: [AuthGuard] },
          { path: 'simulateur', component: SimulateComponent  },
          {path: 'interfaceclient', component: InterfaceClientComponent, canActivate: [AuthGuard]},
          { path: 'demandeCredit/:idcredit', component: DemandeCreditComponent, canActivate: [AuthGuard]},
          { path: 'app-immobilier', component: ImmobilierFontComponent },
          { path: 'property-details/:id', component: ImmobilierFontComponent, canActivate: [AuthGuard] },
         { path: 'loan-simulation', component: LoanSimulationComponent , canActivate: [AuthGuard]},
         { path: 'sell-to-demand', component: SellToDemandComponent , canActivate: [AuthGuard] },
         {path: 'AddBankAccount', component: AddBankAccountComponent, canActivate: [AuthGuard] },
         {path: 'wallet', component: UserWalletComponent, canActivate: [AuthGuard]},
         { path: 'addTransaction', component: AddTransactionComponent, canActivate: [AuthGuard] },
         { path: 'produits', component: ProductsFrontComponent},
         { path: 'confirm-payment/:paymentId', component: ConfirmpaymentComponent },
         { path: 'confirm-payment', component: ConfirmpaymentComponent },
         { path: 'simulateur-produit', component: PaymentComponent },
         {path: 'news', component: NewsComponent},
         { path: 'app-demandesimulateur', component: DemandesimulateurComponent }




       ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
