package com.sportsevents.external;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExternalEvent{
    private Long id;
    private String name;
    private String type;
    private String status;
    private LocalDateTime scheduledDate;
    private int duration;
    private boolean outdoors;
    private LocalDateTime closedDate;
    private String facilitator;
    private String closedBy;
    private String location;
    private String winner;
    private List<String> players;
    private int maxPlayers;

    static class Root{
        @JsonProperty("Event") 
        public ExternalEvent event;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
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

    public List<String> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<String> players) {
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
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", scheduledDate='" + getScheduledDate() + "'" +
            ", duration='" + getDuration() + "'" +
            ", outdoors='" + isOutdoors() + "'" +
            ", closedDate='" + getClosedDate() + "'" +
            ", facilitator='" + getFacilitator() + "'" +
            ", closedBy='" + getClosedBy() + "'" +
            ", lcoation='" + getLocation() + "'" +
            ", winner='" + getWinner() + "'" +
            ", players='" + getPlayers() + "'" +
            ", maxPlayers='" + getMaxPlayers() + "'" +
            "}";
    }
}


