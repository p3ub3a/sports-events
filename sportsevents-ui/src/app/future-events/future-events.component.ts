import { KeycloakService } from 'keycloak-angular';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { of } from 'rxjs';
import { Subscription } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { EventService } from '../event.service';
import { Event } from '../_model';

@Component({
  selector: 'app-future-events',
  templateUrl: './future-events.component.html',
  styleUrls: ['./future-events.component.css']
})
export class FutureEventsComponent implements OnInit, OnDestroy {

  type: String = "future";

  ngOnInit(): void {
    
  }

  ngOnDestroy(): void {
    
  }
}
