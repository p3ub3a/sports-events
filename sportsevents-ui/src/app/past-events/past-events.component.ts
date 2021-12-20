import { KeycloakService } from 'keycloak-angular';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { of } from 'rxjs';
import { Subscription } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { EventService } from '../event.service';
import { Event } from '../_model';
import { PaginationService } from '../pagination.serivce';
import { Page } from '../_model/page.model';

@Component({
  selector: 'app-past-events',
  templateUrl: './past-events.component.html',
  styleUrls: ['./past-events.component.css']
})
export class PastEventsComponent implements OnInit, OnDestroy {

  type: String = "past";

  private loadEventsSubscription: Subscription;
  private deleteEventSubscription: Subscription;
  private closeEventSubscription: Subscription;
  private showDetailsSubscription: Subscription;
  private pageSubscription: Subscription;
  selectedWinner;
  pastPagesNr: number;
  pastEvents: Event[] = [];

  constructor(private eventService: EventService, private paginationService: PaginationService) { }

  ngOnInit(): void {
    this.paginationService.changePage(new Page(0,5));
    this.pageSubscription = this.paginationService.currentPage.subscribe(page => {
      this.loadEvents(page)
    });
  }

  ngOnDestroy(): void {
    if(this.loadEventsSubscription) this.loadEventsSubscription.unsubscribe();
    if(this.deleteEventSubscription) this.deleteEventSubscription.unsubscribe();
    if(this.closeEventSubscription) this.closeEventSubscription.unsubscribe();
    if(this.showDetailsSubscription) this.showDetailsSubscription.unsubscribe();
    if(this.pageSubscription) this.pageSubscription.unsubscribe();
    console.log('unsubscribed from home get events');
  }

  loadEvents(page: Page){
    const events = this.eventService.getEvents(this.type, page.pageNum , page.pageSize).pipe(
      map(results => {
        this.pastEvents = this.eventService.parseDates(results.pastRecords);
        this.pastPagesNr = parseInt(results.pastPagesNr);
      }),
      catchError(error => {
        console.log(error);
        return of([]);
      })
    );
    this.loadEventsSubscription = events.subscribe(data => data);
  }
}
