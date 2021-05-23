import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Competition} from "../model/competition";
import {Observable} from "rxjs";
import {Player} from "../model/player";
import {Team} from "../model/team";

@Injectable({
  providedIn: 'root'
})
export class CompetitionService {
  private readonly apiEndpoint: string = "http://localhost:8080/pa165/api/v2/competitions";
  private readonly create = this.apiEndpoint + "/create";
  private readonly byId = this.apiEndpoint + "/id";
  private readonly byName = this.apiEndpoint + "/name";

  constructor(private http: HttpClient) { }

  public findAll(): Observable<Competition[]> {
    return this.http.get<Competition[]>(this.apiEndpoint);
  }

  public createCompetition(competition: Competition): Observable<Competition> {
    return this.http.post<Team>(this.create, competition)
  }

  public findById(id: number): Observable<Competition> {
    return this.http.get<Team>(this.byId + "/" + id);
  }

  public findByName(name: string): Observable<Competition> {
    return this.http.get<Team>(this.byName + "/" + name);
  }

  public deleteCompetition(id: number): void {
    this.http.delete(this.apiEndpoint + "/" + id);
  }

  public addTeam(competition: number, team: number){
    this.http.get(this.apiEndpoint + "/" + competition + "/addTeam/" + team );
  }

  public removeTeam(competition: number, team: number){
    this.http.get(this.apiEndpoint + "/" + competition + "/removeTeam/" + team );
  }
}
