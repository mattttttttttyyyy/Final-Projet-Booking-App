import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CorporationMainPageComponent } from './corporation-main-page/corporation-main-page.component';

const routes: Routes = [
  {
    path: 'corporation-main-page/:id',
    component: CorporationMainPageComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
