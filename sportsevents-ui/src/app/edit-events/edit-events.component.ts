import { EventService } from './../event.service';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { UserService } from '../user.service';
import { of, Subscription } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Component({
  selector: 'app-edit-events',
  templateUrl: './edit-events.component.html',
  styleUrls: ['./edit-events.component.css']
})
export class EditEventsComponent implements OnInit, OnDestroy {

  @ViewChild('createEvent', {static: false}) createEventForm: NgForm;
  facilitators: String[] = [];
  successMsg: String = null;
  private facilitatorsSubscription: Subscription;
  private submitSubscription: Subscription;

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
    this.eventService.createEvent(data.form.value).subscribe(
      data => {
        console.log(data);
        this.successMsg = "Created " + data.name + " event with id " + data.id;
      }
    );
    this.createEventForm.reset();
  }

  ngOnDestroy(): void {
    this.facilitatorsSubscription.unsubscribe();
    if(this.submitSubscription) this.submitSubscription.unsubscribe();
  }
}
