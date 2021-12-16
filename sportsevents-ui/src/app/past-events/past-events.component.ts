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

  private firstOnSubscription: Subscription;
  private deleteEventSubscription: Subscription;
  private closeEventSubscription: Subscription;
  private showDetailsSubscription: Subscription;
  private roles: string[];
  private pageSubscription: Subscription;
  selectedWinner;
  pagesNr: number;

  events: Event[];
  event: Event;
  selectedEvent: Event = null;
  constructor(private eventService: EventService, private keycloakService: KeycloakService,private paginationService: PaginationService) { }

  ngOnInit(): void {
    this.roles = this.keycloakService.getUserRoles();
    
    this.pageSubscription = this.paginationService.currentPage.subscribe(page => {
      this.loadEvents(page)
  });
  }

  ngOnDestroy(): void {
    this.firstOnSubscription.unsubscribe();
    if(this.deleteEventSubscription) this.deleteEventSubscription.unsubscribe();
    if(this.closeEventSubscription) this.closeEventSubscription.unsubscribe();
    if(this.showDetailsSubscription) this.showDetailsSubscription.unsubscribe();
    if(this.pageSubscription) this.pageSubscription.unsubscribe();
    console.log('unsubscribed from home get events');
  }

  loadEvents(page: Page){
    const events = this.eventService.getEvents(this.type, page.pageNum , page.pageSize).pipe(
      map(results => {
        this.events = this.eventService.parseDates(results.records);
        this.pagesNr = parseInt(results.pagesNr);
      }),
      catchError(error => {
        console.log(error);
        return of([]);
      })
    );
    this.firstOnSubscription = events.subscribe(data => data);
  }

  showDetails(event): void{
    this.selectedEvent = event;
    // this.showDetailsSubscription = this.eventService.getEvent(event);
    const eventRes = this.eventService.getEvent(event).pipe(
      map(result => {
        this.selectedEvent=this.eventService.getPastDate(result);
        // this.event = result;
      }),
      catchError(error => {
        console.log(error);
        return of([]);
      })
    );
    this.showDetailsSubscription = eventRes.subscribe(data => data, err=>console.log(err), () =>{});
  }

  deleteEvent(event): void{
    if(event) this.deleteEventSubscription = this.eventService.deleteEvent(event).subscribe();
  }

  closeEvent(event): void{
    event.winner = this.selectedWinner;
    this.closeEventSubscription = this.eventService.closeEvent(event).subscribe();
  }

  selected(): void{
    console.log(this.selectedWinner);
  }
}
