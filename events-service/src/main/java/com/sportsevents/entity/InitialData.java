package com.sportsevents.entity;

import java.time.LocalDateTime;
import java.time.Month;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

import com.sportsevents.service.EventFactory;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class InitialData {

    @Transactional
    public void onStart(@Observes StartupEvent ev) {
        for(int i = 0; i < 200; i++){
            Event event = null;
            if(i % 2 == 0){
                event = new Event.EventBuilder()
                    .name(EventFactory.PINGPONG_EVENT_NAME)
                    .duration(EventFactory.PINGPONG_EVENT_DURATION)
                    .scheduledDate(LocalDateTime.of(2022,Month.FEBRUARY, 22, 12, 30))
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
                    .winner("gica2")
                    .closedDate(LocalDateTime.of(2022,Month.DECEMBER, 3, 10, 30))
                    .build();
            }
            event.persist();;
        }
    }
}
