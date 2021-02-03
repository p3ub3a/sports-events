package com.sportsevents.entity.events;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sportsevents.entity.Event;
import com.sportsevents.entity.SportsEvent;

@Entity
@Table(name = "tennis_event")
public class TennisEvent extends Event implements SportsEvent {

    public TennisEvent(){}

    private TennisEvent(TennisEventBuilder builder){
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

    public static class TennisEventBuilder {
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
    
        public TennisEventBuilder name(String name){
            this.name = name;
            return this;
        }
    
        public TennisEventBuilder scheduledDate(LocalDateTime scheduledDate){
            this.scheduledDate = scheduledDate;
            return this;
        }
    
        public TennisEventBuilder duration(int duration){
            this.duration = duration;
            return this;
        }
    
        public TennisEventBuilder isOutdoors(boolean isOutdoors){
            this.isOutdoors = isOutdoors;
            return this;
        }

        public TennisEventBuilder closedDate(LocalDateTime closedDate){
            this.closedDate = closedDate;
            return this;
        }

        public TennisEventBuilder facilitator(String facilitator){
            this.facilitator = facilitator;
            return this;
        }

        public TennisEventBuilder closedBy(String closedBy){
            this.closedBy = closedBy;
            return this;
        }

        public TennisEventBuilder location(String location){
            this.location = location;
            return this;
        }

        public TennisEventBuilder status(String status){
            this.status = status;
            return this;
        }

        public TennisEventBuilder type(String type){
            this.type = type;
            return this;
        }

        public TennisEventBuilder maxPlayers(int maxPlayers){
            this.maxPlayers = maxPlayers;
            return this;
        }
    
        public TennisEvent build(){
            return new TennisEvent(this);
        }
    }

    @Override
    public void handleEvent() {
        System.out.println("handling tennis event");
    }
    
}
