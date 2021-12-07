import { Component, OnDestroy, OnInit } from '@angular/core';
import { of, Subscription } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { LeaderboardService } from '../leaderboard.serivce';
import { LeaderboardEntry } from '../_model/leaderboard-entry.model';

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.css']
})
export class LeaderboardComponent implements OnInit, OnDestroy {
  private leaderboardSubscription: Subscription;

  leaderboardEntries: LeaderboardEntry[];
  
  constructor(private leaderboardService: LeaderboardService) { }
  
  ngOnInit(): void {
    const leaderboard = this.leaderboardService.getLeaderboard().pipe(
      map(results => {
        this.leaderboardEntries = results;
      }),
      catchError(error => {
        console.log(error);
        return of([]);
      })
    );
    this.leaderboardSubscription = leaderboard.subscribe(data => data);
  }

  ngOnDestroy(): void {
    this.leaderboardSubscription.unsubscribe();
  }

}
