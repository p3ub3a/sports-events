import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { EditEventsComponent } from './edit-events/edit-events.component';
import { EditUsersComponent } from './edit-users/edit-users.component';
import { HomeNavComponent } from './home-nav/home-nav.component';
import { HomeComponent } from './home/home.component';
import { LeaderboardComponent } from './leaderboard/leaderboard.component';
import { PastEventsComponent } from './past-events/past-events.component';
import { ProfileComponent } from './profile/profile.component';


const appRoutes: Routes = [
  { path: '', component: HomeNavComponent, children: [
    { path: '', component: HomeComponent },
    { path: 'profile', component: ProfileComponent },
    { path: 'pastevents', component: PastEventsComponent },
    { path: 'leaderboard', component: LeaderboardComponent },
    { path: 'editevents', component: EditEventsComponent },
    { path: 'editusers', component: EditUsersComponent },
    { path: 'events', component: HomeNavComponent },
    { path: 'events', component: EditEventsComponent, canActivate: [AuthGuard] }
  ] },
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
