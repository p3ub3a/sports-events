package com.sportsevents.entity;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Random;

import com.sportsevents.service.EventFactory;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class InitialData {
    @Autowired
    private EventsRepo eventsRepo;

    @EventListener
    public void appReady(ApplicationReadyEvent e) {
        eventsRepo.deleteAll();
		for(int i = 0; i < 300; i++){
            Event event = null;
            if(i % 2 == 0){
                event = new Event.EventBuilder()
                    .name(EventFactory.PINGPONG_EVENT_NAME)
                    .duration(EventFactory.PINGPONG_EVENT_DURATION)
                    .scheduledDate(LocalDateTime.of(2022,Month.AUGUST, 22, 12, 30))
                    .isOutdoors(true)
                    .location("Bucuresti")
                    .facilitator("facilitator")
                    .status(EventStatus.CREATED.toString())
                    .type(EventType.PINGPONG.toString())
                    .maxPlayers(EventFactory.PINGPONG_EVENT_MAX_PLAYERS)
                    .build();
            } else{
                event = new Event.EventBuilder()
                    .name(EventFactory.CHESS_EVENT_NAME)
                    .duration(EventFactory.CHESS_EVENT_DURATION)
                    .scheduledDate(LocalDateTime.of(2021,Month.DECEMBER, 1, 12, 30))
                    .isOutdoors(true)
                    .location("Bucuresti")
                    .facilitator("facilitator")
                    .status(EventStatus.CLOSED.toString())
                    .type(EventType.CHESS.toString())
                    .maxPlayers(EventFactory.CHESS_EVENT_MAX_PLAYERS)
                    .players(new String[]{"gica", "gica2"})
                    .winner(new Random().nextBoolean() ? "gica" : "gica2")
                    .closedDate(LocalDateTime.of(2022,Month.DECEMBER, 3, 10, 30))
                    .build();
            }
            eventsRepo.save(event);
        }
    }

}
