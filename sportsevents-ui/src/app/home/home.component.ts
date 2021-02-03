import { DatePipe } from '@angular/common';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { of } from 'rxjs';
import { Subscription } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { EventService } from '../event.service';
import { Event } from '../_model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {

  private firstOnSubscription: Subscription;
  private joinEventSubscription: Subscription;
  private deleteEventSubscription: Subscription;
  private showDetailsSubscription: Subscription;
  roles: string[];

  events: Event[];
  event: Event;
  selectedEvent: Event = null;
  constructor(private eventService: EventService, private keycloakService: KeycloakService) { }

  ngOnInit(): void {
    this.roles = this.keycloakService.getUserRoles();
    const events = this.eventService.getEvents().pipe(
      map(results => {
        results=this.eventService.getFutureDates(results);
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
    if(this.joinEventSubscription) this.joinEventSubscription.unsubscribe();
    if(this.deleteEventSubscription) this.deleteEventSubscription.unsubscribe();
    if(this.showDetailsSubscription) this.showDetailsSubscription.unsubscribe();
    console.log('unsubscribed from home get events');
  }

  showDetails(event): void{
    this.selectedEvent = event;
    // this.showDetailsSubscription = this.eventService.getEvent(event);
    const eventRes = this.eventService.getEvent(event).pipe(
      map(result => {
        result=this.eventService.getFutureDate(result);
        this.event = result;
      }),
      catchError(error => {
        console.log(error);
        return of([]);
      })
    );
    this.showDetailsSubscription = eventRes.subscribe(data => data);
  }

  joinEvent(eventId, type): void{
    this.joinEventSubscription = this.eventService.joinEvent(eventId, type).subscribe();
  }

  deleteEvent(event): void{
    if(event) this.deleteEventSubscription = this.eventService.deleteEvent(event).subscribe();
  }
}
