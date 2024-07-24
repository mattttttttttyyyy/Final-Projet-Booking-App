import { Corporation } from './../corporation';
import { CorporationComponent } from './corporation.component';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-type': 'application/json',
  }),
};

@Injectable({
  providedIn: 'root',
})
export class CorporationService {
  private createCorporationLink = 'http://localhost:8080/corporation/add';
  private getAllCorporations = 'http://localhost:8080/corporation/all';
  private getCorporationById = 'http://localhost:8080/corporation';
  private delteCorporation = 'http://localhost:8080/corporation/delete';
  private updateCorporation = 'http://localhost:8080/corporation/update';

  constructor(private http: HttpClient) {}
  createCorporation(Corporation: Corporation): Observable<any> {
    return this.http.post(this.createCorporationLink, Corporation, httpOptions);
  }
  getCorporation(): Observable<Corporation[]> {
    return this.http.get<Corporation[]>(this.getAllCorporations, httpOptions);
  }

  loadCorporation(corporationId: number): Observable<Corporation> {
    return this.http.get<Corporation>(
      this.getCorporationById + '/' + corporationId,
      httpOptions
    );
  }

  deleteCorporation(corporationId: number): Observable<any> {
    return this.http.delete(
      this.delteCorporation + '/' + corporationId,
      httpOptions
    );
  }

  getCorporationIdByName(corporationName: string): Observable<number> {
    const params = new HttpParams().set('name', corporationName);
    return this.http
      .get<any>(this.getCorporationById + '/byName', {
        ...httpOptions,
        params,
        observe: 'response', // Set observe option to 'response' to access the full response
      })
      .pipe(map((response) => response.body as number));
  }

  changeCorporationName(Corporation: Corporation): Observable<any> {
    return this.http.patch(
      this.updateCorporation + '/' + Corporation.id,
      Corporation,
      httpOptions
    );
  }
}
