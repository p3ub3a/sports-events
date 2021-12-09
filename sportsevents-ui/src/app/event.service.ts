import { KeycloakService } from 'keycloak-angular';
import { AddPlayer } from './_model/add-player.model';
import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { Event, CloseEvent } from './_model';
import * as env  from '../environments/environment';
import { HttpClient } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private datePipe: DatePipe=new DatePipe("en-us");
  private environment = env.current_environment;

  constructor(private http: HttpClient, private keycloakService: KeycloakService){}

  getEvents(): Observable<Event[]>{
      const uri = `${this.environment.api_host}/events`;
      return this.http.get<Event[]>(uri);
  }

  getEvent(data): Observable<Event>{
    const uri = `${this.environment.api_host}/events/${data.id}`;
    return this.http.get<Event>(uri);
  }

  createEvent(data): void{
    const uri = `${this.environment.api_host}/events`;
    this.http.post<void>(uri, data).pipe(
      catchError(err=>{
        console.log(err);
        return throwError(err);
      })).subscribe();
  }

  joinEvent(eventId): Observable<void>{
    const uri = `${this.environment.api_host}/events/addPlayer`;
    let player = new AddPlayer();
    player.eventId = eventId;
    player.playerName = this.keycloakService.getUsername();
    return this.http.patch<void>(uri, player).pipe(
      catchError(err=>{
        console.log(err);
        return throwError(err);
      }));
  }

  closeEvent(event): Observable<void>{
    const uri = `${this.environment.api_host}/events/closeEvent`;
    let closedEvent = new CloseEvent();
    closedEvent.eventId = event.id;
    closedEvent.closedBy = this.keycloakService.getUsername();
    closedEvent.winner = event.winner;
    closedEvent.closedDate = this.getNowDate();
    return this.http.post<void>(uri, closedEvent).pipe(
      catchError(err=>{
        console.log(err);
        return throwError(err);
      }));
  }

  deleteEvent(event): Observable<void>{
    const uri = `${this.environment.api_host}/events/${event.id}`;
    console.log('Deleting event with id ' + event.id);
    return this.http.delete<void>(uri).pipe(
      catchError(err=>{
        console.log(err);
        return throwError(err);
      }));
  }

  getFutureDates(evs): Event[]{
    let futureEvs = [];
    evs.forEach(ev => {
      let then = this.extractTime(ev.scheduledDate);
      if(this.isFuture(then)){
        ev.scheduledDate = this.formatDate(then);
        futureEvs.push(ev);
      }
    });
    return futureEvs;
  }

  getFutureDate(ev): Event{
    let now = Date.now();
    let then;

    if(ev.scheduledDate){
      then = new Date(this.extractString(ev.scheduledDate)).getTime();
    }
    if(now < then){
      ev.scheduledDate = this.formatDate(then);
      return ev;
    }
    return null;
  }

  getPastDates(evs): Event[]{
    let pastEvs = [];
    evs.forEach(ev => {
      // 2021,1,31,16,34,36
      let date = new Date();
      let offset = date.getTimezoneOffset();
      let now = Date.now();
      let then;

      if(ev.scheduledDate){
        then = new Date(this.extractString(ev.scheduledDate)).getTime();
      }
      if(now + offset > then){
        ev.scheduledDate = this.formatDate(then);
        if(ev.closedDate) ev.closedDate = this.formatDate(new Date(this.extractString(ev.closedDate)).getTime());
        pastEvs.push(ev);
      }
    });
    return pastEvs;
  }

  getPastDate(ev): Event{
    // 2021,1,31,16,34,36
    let date = new Date();
    let offset = date.getTimezoneOffset();
    let now = Date.now();
    let then;

    if(ev.scheduledDate){
      then = new Date(this.extractString(ev.scheduledDate)).getTime();
    }
    if(now + offset > then){
      ev.scheduledDate = this.formatDate(then);
      if(ev.closedDate) ev.closedDate = this.formatDate(new Date(this.extractString(ev.closedDate)).getTime());
    }
    return ev;
  }

  extractTime(date): number{
    if(date){
      return new Date(this.extractString(date)).getTime();
    }
  }

  isFuture(then): boolean {
    // 2021,1,31,16,34,36
    let now = Date.now();
    return now<then;
  }

  private extractString(date): string{
    if(env.current_environment.env === 'quarkus'){
      for(let i=1; i<=5; i++){
        let str = date[i] + '';
        if(str.length < 2){
          console.log(str);
          date[i] = '0'+date[i];
        }
      }
      return `${date[0]}-${date[1]}-${date[2]}T${date[3]}:${date[4]}`;
    }
    
    return date;
    // return `${date[0]}-${date[1]}-${date[2]}T${date[3]}:${date[4]}:${date[5]}`;
  }

  private formatDate(dateString): string {
    return this.datePipe.transform(new Date(dateString), 'medium');
  }

  private getNowDate(): string{
    //"yyyy'-'MM'-'dd'T'HH':'mm':'ss"
    let today = new Date();

    let yyyy = today.getFullYear().toString();
    let MM = today.getMonth().toString();
    let dd = today.getDay().toString();
    let HH = today.getHours().toString();
    let mm = today.getMinutes().toString();
    // let ss = today.getSeconds().toString();
    if(dd.length < 2){
      dd='0'+dd;
    }
    if(MM.length < 2){
      MM='0'+MM;
    }
    // if(ss.length < 2){
    //   ss='0'+ss;
    // }
    if(HH.length < 2){
      HH='0'+HH;
    }
    if(mm.length < 2){
      mm='0'+mm;
    }

    return `${yyyy}-${MM}-${dd}T${HH}:${mm}`;
    // return `${yyyy}-${MM}-${dd}T${HH}:${mm}:${ss}`;
  }
}
