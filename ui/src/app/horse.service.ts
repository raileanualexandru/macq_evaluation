import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Horse } from './horse';

@Injectable({
  providedIn: 'root'
})

export class HorseService {
  private apiServerUrl = '';
  constructor(private http: HttpClient) { }

  public getAllHorses(): Observable<Horse[]> {
    return this.http.get<Horse[]>(`${this.apiServerUrl}/horses`);
  }

  public addHorse(horse: Horse): Observable<Horse[]> {
    return this.http.post<Horse[]>(`${this.apiServerUrl}/horses`, horse);
  }

  public updateHorse(horseId: String, horse: Horse): Observable<Horse[]> {
    return this.http.put<Horse[]>(`${this.apiServerUrl}/horses/${horseId}`, horse);
  }

  public deleteHorse(horseId: String): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/horses/${horseId}`);
  }

}
     
