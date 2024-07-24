import { Corporation } from './../corporation';
import { CorporationService } from './../corporation/corporation.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-all-corporations',
  templateUrl: './all-corporations.component.html',
  styleUrls: ['./all-corporations.component.css'],
})
export class AllCorporationsComponent implements OnInit {
  onDeleteCorporation(corporationId: number) {
    this.corporationService.deleteCorporation(corporationId).subscribe(
      () => {
        // Reload the list of corporations
        this.loadCorporations();
      },
      (error) => {
        console.log('Error occurred while deleting corporation:', error);
      }
    );
  }

  loadCorporations() {
    this.corporationService.getCorporation().subscribe(
      (corporations: Corporation[]) => {
        this.corporations = corporations;
      },
      (error) => {
        console.log('Error occurred while fetching corporations:', error);
      }
    );
  }

  deleteCorporation(arg0: number) {
    throw new Error('Method not implemented.');
  }
  constructor(
    private corporationService: CorporationService,
    private router: Router
  ) {}
  corporations: Corporation[] = [];

  ngOnInit(): void {
    this.corporationService.getCorporation().subscribe(
      (corporations: Corporation[]) => {
        this.corporations = corporations;
      },
      (error) => {
        console.log('Error occurred while fetching corporations:', error);
      }
    );
  }

  onCorporationSelect(corporationId: number) {
    this.router.navigate(['corporation-main-page', corporationId]);
  }
}
