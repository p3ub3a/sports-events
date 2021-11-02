package com.sportsevents.eventsservicespring.api;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.sportsevents.eventsservicespring.api.model.ClosedEventModel;
import com.sportsevents.eventsservicespring.api.model.EventModel;
import com.sportsevents.eventsservicespring.api.model.UpdatePlayersModel;
import com.sportsevents.eventsservicespring.entity.Event;
import com.sportsevents.eventsservicespring.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("events")
@RestController
public class EventsController {
    @Autowired
    EventService eventService;
    
    @GetMapping("/{type}/{eventId}")
    // @RolesAllowed({"admin","facilitator","player"})
    public Optional<Event> getEvent(@PathVariable("type") String type, @PathVariable("eventId") Long eventId){
        return eventService.getEvent(type, eventId);
    }

    @GetMapping("/{type}")
    // @RolesAllowed({"admin","facilitator","player"})
    public ResponseEntity<List<Event>> getEvents(@PathVariable("type") String type){
        return ResponseEntity.ok(eventService.getEvents(type));
    }

    @PostMapping
    // @RolesAllowed({"admin"})
    public ResponseEntity<Event> createEvent(@Valid EventModel event){
        try{ 
            Optional<Event> opt = eventService.createEvent(event);
            if(opt.isPresent()){
                return ResponseEntity.ok(opt.get());
            }else{
                return ResponseEntity.status(400).build();
            }
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(400).eTag(e.getMessage()).build();
        }
    }

    // @RolesAllowed({"player"})
    @PatchMapping("/addPlayer")
    public ResponseEntity addPlayer(@Valid UpdatePlayersModel updatePlayersModel){

        if(eventService.addPlayer(updatePlayersModel)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(400).build();
        }
        
    }

    // @RolesAllowed({"player"})
    @PatchMapping("/removePlayer")
    public ResponseEntity removePlayer(@Valid UpdatePlayersModel updatePlayersModel){

        if(eventService.removePlayer(updatePlayersModel)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(400).build();
        }
        
    }

    // @RolesAllowed({"admin", "facilitator"})
    @PostMapping("/closeEvent")
    public ResponseEntity closeEvent(@Valid ClosedEventModel closedEventModel){

        if(eventService.closeEvent(closedEventModel)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(400).build();
        }
        
    }

    @DeleteMapping("/{type}/{id}")
    // @RolesAllowed({"admin"})
    public ResponseEntity delete(@PathVariable("type") String type, @PathVariable("id") Long id){
        eventService.deleteEvent(id);
        return ResponseEntity.status(204).build();
    }
}
