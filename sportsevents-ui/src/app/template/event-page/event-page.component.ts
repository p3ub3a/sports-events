import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { of } from 'rxjs';
import { Subscription } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { PaginationService } from 'src/app/pagination.serivce';
import { Page } from 'src/app/_model/page.model';
import { EventService } from '../../event.service';
import { Event } from '../../_model';

@Component({
  selector: 'app-template-event',
  templateUrl: './event-page.component.html',
  styleUrls: ['./event-page.component.css']
})
export class EventPageComponent implements OnInit, OnDestroy {

    @Input() typeQueryParam: String;

    private firstOnSubscription: Subscription;
    private joinEventSubscription: Subscription;
    private leaveEventSubscription: Subscription;
    private deleteEventSubscription: Subscription;
    private showDetailsSubscription: Subscription;
    private pageSubscription: Subscription;

    roles: string[];
    events: Event[];
    selectedEvent: Event = null;
    isParticipating: boolean = false;
    pagesNr: number;

    constructor(private eventService: EventService, private keycloakService: KeycloakService, private paginationService: PaginationService) { }

    ngOnInit(): void {
        console.log(this.typeQueryParam);
        this.roles = this.keycloakService.getUserRoles();
        this.paginationService.changePage(new Page(0,5));
        this.pageSubscription = this.paginationService.currentPage.subscribe(page => {
            this.loadEvents(page)
        });
    }

    ngOnDestroy(): void {
        this.firstOnSubscription.unsubscribe();
        if(this.joinEventSubscription) this.joinEventSubscription.unsubscribe();
        if(this.leaveEventSubscription) this.leaveEventSubscription.unsubscribe();
        if(this.deleteEventSubscription) this.deleteEventSubscription.unsubscribe();
        if(this.showDetailsSubscription) this.showDetailsSubscription.unsubscribe();
        if(this.pageSubscription) this.pageSubscription.unsubscribe();
        console.log('unsubscribed from home get events');
    }

    loadEvents(page: Page){
        const events = this.eventService.getEvents(this.typeQueryParam,page.pageNum , page.pageSize).pipe(
            map(results => {
                this.events =this.eventService.parseDates(results.records);
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
}
