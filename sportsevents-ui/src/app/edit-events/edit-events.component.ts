import { EventService } from './../event.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../user.service';
import { of, Subscription } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Component({
  selector: 'app-edit-events',
  templateUrl: './edit-events.component.html',
  styleUrls: ['./edit-events.component.css']
})
export class EditEventsComponent implements OnInit, OnDestroy {

  facilitators: String[] = [];
  private facilitatorsSubscription: Subscription;

  constructor(private formModule: FormsModule, private eventService: EventService, private userService: UserService) { }

  ngOnInit(): void {

    const facilitators = this.userService.getUsersWithRole("facilitator").pipe(
      map(results => {
        results.forEach(e => {
          this.facilitators.push(e.username);
        });
        console.log(this.facilitators);
      }),
      catchError(error => {
        console.log(error);
        return of([]);
      })
    );
    this.facilitatorsSubscription = facilitators.subscribe(data => data);
  }

  onSubmit(data){
    console.log(data);
    this.eventService.createEvent(data.form.value);
  }

  ngOnDestroy(): void {
    this.facilitatorsSubscription.unsubscribe();
  }
}
