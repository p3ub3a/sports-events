package com.sportsevents.entity.events;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sportsevents.entity.Event;
import com.sportsevents.entity.SportsEvent;

@Entity
@Table(name = "swimming_event")
public class SwimmingEvent extends Event implements SportsEvent {

    public SwimmingEvent(){}

    private SwimmingEvent(SwimmingEventBuilder builder){
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

    public static class SwimmingEventBuilder {
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
    
        public SwimmingEventBuilder name(String name){
            this.name = name;
            return this;
        }
    
        public SwimmingEventBuilder scheduledDate(LocalDateTime scheduledDate){
            this.scheduledDate = scheduledDate;
            return this;
        }
    
        public SwimmingEventBuilder duration(int duration){
            this.duration = duration;
            return this;
        }
    
        public SwimmingEventBuilder isOutdoors(boolean isOutdoors){
            this.isOutdoors = isOutdoors;
            return this;
        }

        public SwimmingEventBuilder closedDate(LocalDateTime closedDate){
            this.closedDate = closedDate;
            return this;
        }

        public SwimmingEventBuilder facilitator(String facilitator){
            this.facilitator = facilitator;
            return this;
        }

        public SwimmingEventBuilder closedBy(String closedBy){
            this.closedBy = closedBy;
            return this;
        }

        public SwimmingEventBuilder location(String location){
            this.location = location;
            return this;
        }

        public SwimmingEventBuilder status(String status){
            this.status = status;
            return this;
        }

        public SwimmingEventBuilder type(String type){
            this.type = type;
            return this;
        }

        public SwimmingEventBuilder maxPlayers(int maxPlayers){
            this.maxPlayers = maxPlayers;
            return this;
        }
    
        public SwimmingEvent build(){
            return new SwimmingEvent(this);
        }
    }

    @Override
    public void handleEvent() {
        System.out.println("handling swimming event");
    }
    
}
