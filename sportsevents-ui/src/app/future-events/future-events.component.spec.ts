import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PastEventsComponent } from './future-events.component';

describe('PastEventsComponent', () => {
  let component: PastEventsComponent;
  let fixture: ComponentFixture<PastEventsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PastEventsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PastEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
