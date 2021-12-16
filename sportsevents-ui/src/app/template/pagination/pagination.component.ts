import { Component, Input, OnDestroy, OnInit } from "@angular/core";
import { PaginationService } from "src/app/pagination.serivce";
import { Page } from "src/app/_model/page.model";

@Component({
    selector: 'app-pagination',
    templateUrl: './pagination.component.html',
    styleUrls: ['./pagination.component.css']
  })
  export class PaginationComponent implements OnInit, OnDestroy {
    
    @Input() lastPage: number;
    recordsPerPage = 5;
    currentPage: number = 1;
    firstPage: number;
    beforePage: number;
    afterPage: number;
    middlePage: number;
    thirdLast: number;

    constructor(private paginationService: PaginationService){}
    
    ngOnInit(): void {
      if(this.lastPage > 0){
        this.firstPage = 1;
        switch (this.lastPage) {
          case 1:
            break;
          case 2:
            break;
          case 3:
            this.middlePage = 2;
            break;
          default:
            this.middlePage = Math.ceil(this.lastPage / 2);
            this.beforePage = this.middlePage - 1;
            this.afterPage = this.middlePage + 1;
        }
      }
    }

    ngOnDestroy(): void {
    }

    onPageChange(pageNum: number) {
      this.currentPage = pageNum;
      if(this.lastPage > 3){
        if(this.currentPage <= this.firstPage + 3){
          this.beforePage = this.firstPage + 1;
          
          this.middlePage = this.firstPage + 2;
          this.afterPage = this.firstPage + 3;
        }
        if(this.currentPage >= this.lastPage - 3){
          this.beforePage = this.lastPage - 3;
          this.thirdLast = this.lastPage - 2;
          this.afterPage = this.lastPage - 1;
        }
        if(this.currentPage < this.thirdLast){
          this.thirdLast = undefined;
        }
        if(this.currentPage > this.middlePage){
          this.middlePage = undefined;
        }
        
        if(this.currentPage >= this.firstPage + 3 && this.currentPage <= this.lastPage - 3){
          this.beforePage = this.currentPage - 1;
          this.afterPage = this.currentPage + 1;
        }
      }
      
      this.paginationService.changePage(new Page(this.currentPage - 1, this.recordsPerPage));
    }
  }