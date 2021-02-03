import { KeycloakService } from 'keycloak-angular';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  username: string;

  constructor(private keycloakService: KeycloakService) { }

  ngOnInit(): void {
    this.username = this.keycloakService.getUsername();
  }

}
