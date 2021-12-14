import { KeycloakService } from 'keycloak-angular';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { of } from 'rxjs';
import { Subscription } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { EventService } from '../event.service';
import { Event } from '../_model';

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
  selectedWinner;

  events: Event[];
  event: Event;
  selectedEvent: Event = null;
  constructor(private eventService: EventService, private keycloakService: KeycloakService) { }

  ngOnInit(): void {
    this.roles = this.keycloakService.getUserRoles();
    const events = this.eventService.getEvents(this.type).pipe(
      map(results => {
        results=this.eventService.parseDates(results);
        this.events = results;
      }),
      catchError(error => {
        console.log(error);
        return of([]);
      })
    );
    this.firstOnSubscription = events.subscribe(data => data);
  }

  ngOnDestroy(): void {
    this.firstOnSubscription.unsubscribe();
    if(this.deleteEventSubscription) this.deleteEventSubscription.unsubscribe();
    if(this.closeEventSubscription) this.closeEventSubscription.unsubscribe();
    console.log('unsubscribed from home get events');
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
    this.showDetailsSubscription = eventRes.subscribe(data => data, err=>console.log(err), () =>{
    //  this.selectedEvent=event
    });
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
