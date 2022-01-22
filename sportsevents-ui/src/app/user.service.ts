import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import * as env  from '../environments/environment';
import { UsersByRole } from "./_model/users-by-role.model";

@Injectable({
    providedIn: 'root'
})
export class UserService{
    private environment = env.current_environment;
    private keycloak_client_id: String = "7bed4b4e-43d3-4217-a574-fd85a723f6e3";
    private keycloak_realm: String = "sportsevents-realm";

    constructor(private http: HttpClient){}

    // keycloak rest api: https://www.keycloak.org/docs-api/16.0/rest-api/index.html
    getUsersWithRole(role): Observable<UsersByRole[]>{
        const uri = `${this.environment.keycloak.issuer}admin/realms/${this.keycloak_realm}/clients/${this.keycloak_client_id}/roles/${role}/users`;
        return this.http.get<UsersByRole[]>(uri);
    }
}