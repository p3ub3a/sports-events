package com.sportsevents.api;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.sportsevents.api.model.LeaderboardEntryModel;
import com.sportsevents.service.EventService;

import io.micrometer.core.annotation.Timed;

import javax.ws.rs.core.MediaType;

@Path("/leaderboard")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class LeaderboardResource {
    @Inject
    EventService eventService;
    
    @GET
    @Path("/")
    @RolesAllowed({"admin","facilitator","player"})
    @Timed(value="get_leaderboard.request", histogram=true)
    public Optional<List<LeaderboardEntryModel>> getLeaderboard(){
        return eventService.getLeaderboard();
    }
}
