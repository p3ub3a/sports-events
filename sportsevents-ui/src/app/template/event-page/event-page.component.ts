import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { of } from 'rxjs';
import { Subscription } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { PaginationService } from 'src/app/pagination.serivce';
import { EventService } from '../../event.service';
import { Event } from '../../_model';

@Component({
  selector: 'app-template-event',
  templateUrl: './event-page.component.html',
  styleUrls: ['./event-page.component.css']
})
export class EventPageComponent implements OnInit, OnDestroy {

    @Input() isFuture: boolean;
    @Input() events: Event[];

    private joinEventSubscription: Subscription;
    private leaveEventSubscription: Subscription;
    private deleteEventSubscription: Subscription;
    private closeEventSubscription: Subscription;
    private showDetailsSubscription: Subscription;

    roles: string[];
    
    selectedEvent: Event = null;
    isParticipating: boolean = false;
    selectedWinner;

    constructor(private eventService: EventService, private keycloakService: KeycloakService, private paginationService: PaginationService) { }

    ngOnInit(): void {
        console.log("isFuture: " + this.isFuture);
        this.roles = this.keycloakService.getUserRoles();
    }

    ngOnDestroy(): void {
        if(this.joinEventSubscription) this.joinEventSubscription.unsubscribe();
        if(this.leaveEventSubscription) this.leaveEventSubscription.unsubscribe();
        if(this.deleteEventSubscription) this.deleteEventSubscription.unsubscribe();
        if(this.showDetailsSubscription) this.showDetailsSubscription.unsubscribe();
        if(this.closeEventSubscription) this.closeEventSubscription.unsubscribe();
        console.log('unsubscribed from home get events');
    }

    showDetails(event): void{
        const eventRes = this.eventService.getEvent(event).pipe(
            map(result => {
                result=this.eventService.getFutureDate(result);
                this.isParticipating = this.isPlayerParticipating(result);
                this.selectedEvent = result;
            }),
                catchError(error => {
                console.log(error);
                return of([]);
            })
        );
        this.showDetailsSubscription = eventRes.subscribe(data => data);
    }

    isPlayerParticipating(event): boolean {
        let playerName = this.keycloakService.getUsername();
        return event?.players?.includes(playerName);
    }

    joinEvent(eventId): void{
        this.joinEventSubscription = this.eventService.joinEvent(eventId).subscribe({
            complete: () => {
            this.showDetails(this.selectedEvent);
            }
        });
    }

    leaveEvent(eventId): void{
        this.leaveEventSubscription = this.eventService.leaveEvent(eventId).subscribe({
            complete: () => {
            this.showDetails(this.selectedEvent);
            }
        });
    }

    deleteEvent(event): void{
        if(event) this.deleteEventSubscription = this.eventService.deleteEvent(event).subscribe();
    }

    showPastDetails(event): void{
        this.selectedEvent = event;
        const eventRes = this.eventService.getEvent(event).pipe(
          map(result => {
            this.selectedEvent=this.eventService.getPastDate(result);
          }),
          catchError(error => {
            console.log(error);
            return of([]);
          })
        );
        this.showDetailsSubscription = eventRes.subscribe(data => data, err=>console.log(err), () =>{});
      }
    
    closeEvent(event): void{
      event.winner = this.selectedWinner;
      this.closeEventSubscription = this.eventService.closeEvent(event).subscribe({
          complete: () => {
            this.showDetails(this.selectedEvent);
          }
      });
    }
  
    selected(): void{
      console.log(this.selectedWinner);
    }
}
