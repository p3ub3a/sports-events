import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import * as env  from '../environments/environment';
import { LeaderboardEntry } from "./_model/leaderboard-entry.model";

@Injectable({
    providedIn: 'root'
})
export class LeaderboardService{
    private environment = env.current_environment;

    constructor(private http: HttpClient){}

    getLeaderboard(): Observable<LeaderboardEntry[]>{
        const uri = `${this.environment.api_host}/leaderboard`;
        return this.http.get<LeaderboardEntry[]>(uri);
    }
}