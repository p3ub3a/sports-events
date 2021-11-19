package com.sportsevents.api;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.sportsevents.api.model.ClosedEventModel;
import com.sportsevents.api.model.EventModel;
import com.sportsevents.api.model.UpdatePlayersModel;
import com.sportsevents.entity.Event;
import com.sportsevents.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("events")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EventsController {
    @Autowired
    EventService eventService;
    
    @GetMapping("/{eventId}")
    // @RolesAllowed({"admin","facilitator","player"})
    public Optional<Event> getEvent(@PathVariable("eventId") Long eventId){
        return eventService.getEvent(eventId);
    }

    @GetMapping()
    // @RolesAllowed({"admin","facilitator","player"})
    public ResponseEntity<List<Event>> getEvents(){
        return ResponseEntity.ok(eventService.getEvents());
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

    @DeleteMapping("/{id}")
    // @RolesAllowed({"admin"})
    public ResponseEntity delete(@PathVariable("id") Long id){
        eventService.deleteEvent(id);
        return ResponseEntity.status(204).build();
    }
}
