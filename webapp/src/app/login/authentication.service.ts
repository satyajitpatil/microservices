import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment.prod';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  baseUrl = environment.baseUrl;
  private authenticationApiUrl = this.baseUrl + '/authenticate';
  private token: string;

  constructor(private http: HttpClient) { }

  authenticate(user: string, password: string): Observable<any> {
    let credentials = btoa(user + ':' + password)
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', 'Basic ' + credentials);
    return this.http.get(this.authenticationApiUrl, { headers })

  }

  public setToken(token: string) {
    this.token = token;
  }
  public getToken() {
    return this.token;
  }

}
