package com.sportsevents.api;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
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
import com.sportsevents.api.model.PageRequest;
import com.sportsevents.entity.Event;
import com.sportsevents.service.EventService;

import io.micrometer.core.annotation.Timed;

@Path("/events")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class EventsResource {
    @Inject
    EventService eventService;
    
    @GET
    @Path("/{eventId}")
    @RolesAllowed({"admin","facilitator","player"})
    @Timed(value="timed.get.event.request", histogram=true)
    public Optional<Event> getEvent(@PathParam("eventId") Long eventId){
        return eventService.getEvent(eventId);
    }

    @GET
    @Path("/")
    @RolesAllowed({"admin","facilitator","player"})
    @Timed(value="timed.get.events.request", histogram=true)
    public Response getEvents(@BeanParam PageRequest pageRequest){
        return Response.ok(eventService.getEvents(pageRequest)).status(200).build();
    }

    @POST
    @RolesAllowed({"admin"})
    @Timed(value="timed.create.event.request", histogram=true)
    public Response createEvent(@Valid EventModel event){
        try{ 
            Optional<Event> opt = eventService.createEvent(event);
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
    @RolesAllowed({"player"})
    @Path("/addPlayer")
    @Timed(value="timed.add.player.request", histogram=true)
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
    @Timed(value="timed.remove.player.request", histogram=true)
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
    @Timed(value="timed.close.event.request", histogram=true)
    public Response closeEvent(@Valid ClosedEventModel closedEventModel){

        if(eventService.closeEvent(closedEventModel)){
            return Response.ok().status(201).build();
        }else{
            return Response.status(400).build();
        }
        
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"admin"})
    @Timed(value="timed.delete.event.request", histogram=true)
    public Response delete(@PathParam("id") Long id){
        return Response.ok().status(204).build();
    }
}
