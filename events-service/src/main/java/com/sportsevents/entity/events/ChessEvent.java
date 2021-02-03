package com.sportsevents.entity.events;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sportsevents.entity.Event;
import com.sportsevents.entity.SportsEvent;

@Entity
@Table(name = "chess_event")
public class ChessEvent extends Event implements SportsEvent {

    public ChessEvent(){}

    private ChessEvent(ChessEventBuilder builder){
        this.setName(builder.name);
        this.setScheduledDate(builder.scheduledDate);
        this.setDuration(builder.duration);
        this.setOutdoors(builder.isOutdoors);
        this.setClosedDate(builder.closedDate);
        this.setFacilitator(builder.facilitator);
        this.setClosedBy(builder.closedBy);
        this.setLocation(builder.location);
        this.setStatus(builder.status);
        this.setType(builder.type);
        this.setMaxPlayers(builder.maxPlayers);
    }

    public static class ChessEventBuilder {
        private String name;
        private LocalDateTime scheduledDate;
        private int duration;
        private boolean isOutdoors;
        private LocalDateTime closedDate;
        private String facilitator;
        private String closedBy;
        private String location;
        private String status;
        private String type;
        private String[] players;
        private int maxPlayers;
    
        public ChessEventBuilder name(String name){
            this.name = name;
            return this;
        }
    
        public ChessEventBuilder scheduledDate(LocalDateTime scheduledDate){
            this.scheduledDate = scheduledDate;
            return this;
        }
    
        public ChessEventBuilder duration(int duration){
            this.duration = duration;
            return this;
        }
    
        public ChessEventBuilder isOutdoors(boolean isOutdoors){
            this.isOutdoors = isOutdoors;
            return this;
        }

        public ChessEventBuilder closedDate(LocalDateTime closedDate){
            this.closedDate = closedDate;
            return this;
        }

        public ChessEventBuilder facilitator(String facilitator){
            this.facilitator = facilitator;
            return this;
        }

        public ChessEventBuilder closedBy(String closedBy){
            this.closedBy = closedBy;
            return this;
        }

        public ChessEventBuilder location(String location){
            this.location = location;
            return this;
        }

        public ChessEventBuilder status(String status){
            this.status = status;
            return this;
        }

        public ChessEventBuilder players(String[] players){
            this.players = players;
            return this;
        }

        public ChessEventBuilder type(String type){
            this.type = type;
            return this;
        }

        public ChessEventBuilder maxPlayers(int maxPlayers){
            this.maxPlayers = maxPlayers;
            return this;
        }
    
        public ChessEvent build(){
            return new ChessEvent(this);
        }
    }

    @Override
    public void handleEvent() {
        System.out.println("handling chess event");
    }
    
}
