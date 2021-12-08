package com.sportsevents.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "event")
public class Event extends PanacheEntityBase{
    @Id
    @GeneratedValue(generator = "event_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
      name = "event_generator", 
      sequenceName = "event_id_seq",
      allocationSize = 1,
      initialValue = 1)
    private long id;

    private String name;
    private String type;
    private String status;
    @Column(name = "scheduled_date")
    private LocalDateTime scheduledDate;
    private int duration;
    private boolean outdoors;
    @Column(name = "closed_date")
    private LocalDateTime closedDate;
    private String facilitator;
    @Column(name = "closed_by")
    private String closedBy;
    private String location;
    private String winner;
    private String[] players;
    @Column(name = "max_players")
    private int maxPlayers;

    public Event(){}

    private Event(EventBuilder builder){
        if(builder.id != null) this.setId(builder.id);
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
        this.setPlayers(builder.players);
        this.setWinner(builder.winner);
    }

    public static class EventBuilder {
        private Long id;
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
        private String winner;
    
        public EventBuilder id(Long id){
            this.id = id;
            return this;
        }

        public EventBuilder name(String name){
            this.name = name;
            return this;
        }
    
        public EventBuilder scheduledDate(LocalDateTime scheduledDate){
            this.scheduledDate = scheduledDate;
            return this;
        }
    
        public EventBuilder duration(int duration){
            this.duration = duration;
            return this;
        }
    
        public EventBuilder isOutdoors(boolean isOutdoors){
            this.isOutdoors = isOutdoors;
            return this;
        }

        public EventBuilder closedDate(LocalDateTime closedDate){
            this.closedDate = closedDate;
            return this;
        }

        public EventBuilder facilitator(String facilitator){
            this.facilitator = facilitator;
            return this;
        }

        public EventBuilder closedBy(String closedBy){
            this.closedBy = closedBy;
            return this;
        }

        public EventBuilder location(String location){
            this.location = location;
            return this;
        }

        public EventBuilder status(String status){
            this.status = status;
            return this;
        }

        public EventBuilder players(String[] players){
            this.players = players;
            return this;
        }

        public EventBuilder type(String type){
            this.type = type;
            return this;
        }

        public EventBuilder maxPlayers(int maxPlayers){
            this.maxPlayers = maxPlayers;
            return this;
        }

        public EventBuilder winner(String winner){
            this.winner = winner;
            return this;
        }
    
        public Event build(){
            return new Event(this);
        }
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getScheduledDate() {
        return this.scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isOutdoors() {
        return this.outdoors;
    }

    public boolean getOutdoors() {
        return this.outdoors;
    }

    public void setOutdoors(boolean outdoors) {
        this.outdoors = outdoors;
    }

    public LocalDateTime getClosedDate() {
        return this.closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    public String getFacilitator() {
        return this.facilitator;
    }

    public void setFacilitator(String facilitator) {
        this.facilitator = facilitator;
    }

    public String getClosedBy() {
        return this.closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWinner() {
        return this.winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String[] getPlayers() {
        return this.players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            " type='" + getType() + "'" +
            ", scheduledDate='" + getScheduledDate() + "'" +
            ", duration='" + getDuration() + "'" +
            ", outdoors='" + isOutdoors() + "'" +
            ", maxPlayers='" + getMaxPlayers() + "'" +
            ", closedDate='" + getClosedDate() + "'" +
            ", facilitator='" + getFacilitator() + "'" +
            ", closedBy='" + getClosedBy() + "'" +
            ", location='" + getLocation() + "'" +
            ", winner='" + getLocation() + "'" +
            ", players='" + getPlayers() + "'" +
            "}";
    }

}
