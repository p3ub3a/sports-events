import { DatePipe } from '@angular/common';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { of } from 'rxjs';
import { Subscription } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { EventService } from '../event.service';
import { PaginationService } from '../pagination.serivce';
import { Event } from '../_model';
import { HomeEvents } from '../_model/home-events.model';
import { Page } from '../_model/page.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {

  type: String = "home";
  futureEvents: Event[] = [];
  pastEvents: Event[] = [];
  futurePagesNr: number;
  pastPagesNr: number;

  private firstOnSubscription: Subscription;
  private joinEventSubscription: Subscription;
  private leaveEventSubscription: Subscription;
  private deleteEventSubscription: Subscription;
  private showDetailsSubscription: Subscription;
  private pageSubscription: Subscription;

  constructor(private eventService: EventService, private keycloakService: KeycloakService, private paginationService: PaginationService) { }

  ngOnInit(): void {
    this.paginationService.changePage(new Page(0,5));
    this.pageSubscription = this.paginationService.currentPage.subscribe(page => {
        this.loadEvents(page)
    });
  }

  ngOnDestroy(): void {
    if(this.firstOnSubscription) this.firstOnSubscription.unsubscribe();
    if(this.joinEventSubscription) this.joinEventSubscription.unsubscribe();
    if(this.leaveEventSubscription) this.leaveEventSubscription.unsubscribe();
    if(this.deleteEventSubscription) this.deleteEventSubscription.unsubscribe();
    if(this.showDetailsSubscription) this.showDetailsSubscription.unsubscribe();
    if(this.pageSubscription) this.pageSubscription.unsubscribe();
    console.log('unsubscribed from home get events');
  }

  loadEvents(page: Page){
    const events = this.eventService.getEvents(this.type, page.pageNum , page.pageSize).pipe(
      map(results => {
        this.futureEvents =this.eventService.parseDates(results.futureRecords);
        this.pastEvents =this.eventService.parseDates(results.pastRecords);
        this.futurePagesNr = parseInt(results.futurePagesNr);
        this.pastPagesNr = parseInt(results.pastPagesNr);
      }),
      catchError(error => {
        console.log(error);
        return of([]);
      })
    );
    this.firstOnSubscription = events.subscribe(data => data);
  }
}
