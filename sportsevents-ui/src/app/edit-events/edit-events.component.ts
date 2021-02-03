import { EventService } from './../event.service';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-edit-events',
  templateUrl: './edit-events.component.html',
  styleUrls: ['./edit-events.component.css']
})
export class EditEventsComponent implements OnInit {

  constructor(private formModule: FormsModule, private eventService: EventService) { }

  ngOnInit(): void {
  }

  onSubmit(data){
    console.log(data);
    this.eventService.createEvent(data.form.value);
  }

}
