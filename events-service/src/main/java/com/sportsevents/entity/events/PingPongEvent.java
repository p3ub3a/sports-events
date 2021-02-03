package com.sportsevents.entity.events;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sportsevents.entity.Event;
import com.sportsevents.entity.SportsEvent;

@Entity
@Table(name = "pingpong_event")
public class PingPongEvent extends Event implements SportsEvent{

    public PingPongEvent(){}

    private PingPongEvent(PingPongEventBuilder builder){
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

    public static class PingPongEventBuilder {
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
        private int maxPlayers;
    
        public PingPongEventBuilder name(String name){
            this.name = name;
            return this;
        }
    
        public PingPongEventBuilder scheduledDate(LocalDateTime scheduledDate){
            this.scheduledDate = scheduledDate;
            return this;
        }
    
        public PingPongEventBuilder duration(int duration){
            this.duration = duration;
            return this;
        }
    
        public PingPongEventBuilder isOutdoors(boolean isOutdoors){
            this.isOutdoors = isOutdoors;
            return this;
        }

        public PingPongEventBuilder closedDate(LocalDateTime closedDate){
            this.closedDate = closedDate;
            return this;
        }

        public PingPongEventBuilder facilitator(String facilitator){
            this.facilitator = facilitator;
            return this;
        }

        public PingPongEventBuilder closedBy(String closedBy){
            this.closedBy = closedBy;
            return this;
        }

        public PingPongEventBuilder location(String location){
            this.location = location;
            return this;
        }

        public PingPongEventBuilder status(String status){
            this.status = status;
            return this;
        }

        public PingPongEventBuilder type(String type){
            this.type = type;
            return this;
        }

        public PingPongEventBuilder maxPlayers(int maxPlayers){
            this.maxPlayers = maxPlayers;
            return this;
        }
    
        public PingPongEvent build(){
            return new PingPongEvent(this);
        }
    }

    @Override
    public void handleEvent() {
        System.out.println("handling ping pong event");
    }
    
}
