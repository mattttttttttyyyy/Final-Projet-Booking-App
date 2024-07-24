import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CorporationMainPageComponent } from '../corporation-main-page.component';
import { FormsModule } from '@angular/forms';
import { ConferenceRoomModule } from 'src/app/conference-room/conference-room.module';

@NgModule({
  declarations: [CorporationMainPageComponent],
  imports: [CommonModule, FormsModule, ConferenceRoomModule],
})
export class CorporationMainPageModule {}
