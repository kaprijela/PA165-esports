import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {Observable, throwError} from 'rxjs';
import {Router} from "@angular/router";
import {Team} from "../model/team";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'my-auth-token'
  })
};

@Injectable({
  providedIn: 'root'
})
export class TeamService {
  private readonly teamsUrl: string = "http://localhost:8080/pa165/api/v2/teams";

  private handleError(error: HttpErrorResponse, team: Team) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // Return an observable with a user-facing error message.
    return throwError(
      'Something bad happened; please try again later.');
  }

  constructor(private http: HttpClient, private router: Router) { }

  public findAll(): Observable<Team[]> {
    return this.http.get<Team[]>(this.teamsUrl);
  }

  public findById(id: number): Observable<Team> {
    return this.http.get<Team>(this.teamsUrl + "/" + id);
  }

  public findByAbbreviation(abbreviation: string): Observable<Team> {
    return this.http.get<Team>(this.teamsUrl + "/abbr/" + abbreviation);
  }

  public create(team: Team): Observable<Team> {
    this.router.navigateByUrl("/teams").then();
    return this.http.post<Team>(this.teamsUrl, team)
  }

  public delete(id: number): void {
    this.http.delete(this.teamsUrl + "/" + id);
  }

  public addPlayer(team: number, player: number): Observable<any> {
    return this.http.get<any>(this.teamsUrl + "/add/" + team + "/addPlayer/" + player); // FIXME: cannot be GET
  }

  public removePlayer(team: number, player: number): Observable<any> {
    return this.http.get<Team>(this.teamsUrl + "/remove/" + team + "/removePlayer/" + player); // FIXME: cannot be GET
  }


}
