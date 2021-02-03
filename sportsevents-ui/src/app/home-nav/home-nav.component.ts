import { Component, OnDestroy, OnInit } from '@angular/core';
import { EventService } from '../event.service'
import { map, catchError } from 'rxjs/operators';
import {of, Subscription} from 'rxjs';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-home-nav',
  templateUrl: './home-nav.component.html',
  styleUrls: ['./home-nav.component.css']
})
export class HomeNavComponent implements OnInit, OnDestroy {
  roles: string[];
  username: string;

  constructor(private keycloak: KeycloakService) { }

  ngOnInit(): void {
    this.roles = this.keycloak.getUserRoles();
  }

  ngOnDestroy(): void {

  }

  logout(): void {
    this.keycloak.logout();
  };
}
