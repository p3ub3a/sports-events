import { Component, OnDestroy, OnInit } from '@angular/core';
import { of } from 'rxjs';
import { Subscription } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { EventService } from '../event.service';
import { Event } from '../_model';
import { Page } from '../_model/page.model';
import { PaginationService } from '../pagination.serivce';

@Component({
  selector: 'app-future-events',
  templateUrl: './future-events.component.html',
  styleUrls: ['./future-events.component.css']
})
export class FutureEventsComponent implements OnInit, OnDestroy {

  type: String = "future";
  futureEvents: Event[] = [];
  futurePagesNr: number;

  private pageSubscription: Subscription;
  private firstOnSubscription: Subscription;

  constructor(private eventService: EventService, private paginationService: PaginationService) { }

  ngOnInit(): void {
    this.paginationService.changePage(new Page(0,5));
    this.pageSubscription = this.paginationService.currentPage.subscribe(page => {
      this.loadEvents(page)
    });
  }

  ngOnDestroy(): void {
    if(this.pageSubscription) this.pageSubscription.unsubscribe();
    if(this.firstOnSubscription) this.firstOnSubscription.unsubscribe();
  }

  loadEvents(page: Page){
    const events = this.eventService.getEvents(this.type, page.pageNum , page.pageSize).pipe(
      map(results => {
        this.futureEvents = this.eventService.parseDates(results.futureRecords);
        this.futurePagesNr = parseInt(results.futurePagesNr);
      }),
      catchError(error => {
        console.log(error);
        return of([]);
      })
    );
    this.firstOnSubscription = events.subscribe(data => data);
  }
}
