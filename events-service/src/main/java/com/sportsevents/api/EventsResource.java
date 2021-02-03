package com.sportsevents.api;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.sportsevents.api.model.UpdatePlayersModel;
import com.sportsevents.api.model.ClosedEventModel;
import com.sportsevents.api.model.EventModel;
import com.sportsevents.entity.SportsEvent;
import com.sportsevents.service.EventService;

@Path("/events")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class EventsResource {
    @Inject
    EventService eventService;
    
    @GET
    @Path("/{type}/{eventId}")
    @RolesAllowed({"admin","facilitator","player"})
    public Optional<SportsEvent> getEvent(@PathParam("type") String type, @PathParam("eventId") Long eventId){
        return eventService.getEvent(type, eventId);
    }

    @GET
    @Path("/{type}")
    @RolesAllowed({"admin","facilitator","player"})
    public Response getEvents(@PathParam("type") String type){
        return Response.ok(eventService.getEvents(type)).status(200).build();
    }

    @POST
    @RolesAllowed({"admin"})
    public Response createEvent(@Valid EventModel event){
        try{ 
            Optional<SportsEvent> opt = eventService.createEvent(event);
            if(opt.isPresent()){
                return Response.ok(opt.get()).status(201).build();
            }else{
                return Response.status(400).build();
            }
        }catch(IllegalArgumentException e){
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @PATCH
    // @RolesAllowed({"player"})
    @Path("/addPlayer")
    public Response addPlayer(@Valid UpdatePlayersModel updatePlayersModel){

        if(eventService.addPlayer(updatePlayersModel)){
            return Response.ok().status(200).build();
        }else{
            return Response.status(400).build();
        }
        
    }

    @PATCH
    @RolesAllowed({"player"})
    @Path("/removePlayer")
    public Response removePlayer(@Valid UpdatePlayersModel updatePlayersModel){

        if(eventService.removePlayer(updatePlayersModel)){
            return Response.ok().status(200).build();
        }else{
            return Response.status(400).build();
        }
        
    }

    @POST
    @RolesAllowed({"admin", "facilitator"})
    @Path("/closeEvent")
    public Response closeEvent(@Valid ClosedEventModel closedEventModel){

        if(eventService.closeEvent(closedEventModel)){
            return Response.ok().status(201).build();
        }else{
            return Response.status(400).build();
        }
        
    }

    @DELETE
    @Path("/{type}/{id}")
    @RolesAllowed({"admin"})
    public Response delete(@PathParam("type") String type, @PathParam("id") Long id){
        if(eventService.deleteEvent(type, id)){
            return Response.ok().status(204).build();
        }else{
            return Response.status(400).build();
        }
    }
}
