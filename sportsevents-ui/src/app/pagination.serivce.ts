import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import * as env  from '../environments/environment';
import { Page } from "./_model/page.model";

@Injectable({
    providedIn: 'root'
})
export class PaginationService{
    private environment = env.current_environment;

    private pageSource = new BehaviorSubject<Page>(new Page(0,5));
    currentPage = this.pageSource.asObservable();

    constructor(){}

    changePage(page: Page){
        this.pageSource.next(page);
    }
}