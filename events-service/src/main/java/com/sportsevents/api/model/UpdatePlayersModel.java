package com.sportsevents.api.model;

import javax.validation.constraints.NotNull;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection 
public class UpdatePlayersModel {
    @NotNull
    private Long eventId;

    @NotNull
    private String playerName;

    public Long getEventId() {
        return this.eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return "{" +
            " eventId='" + getEventId() + "'" +
            ", playerName='" + getPlayerName() + "'" +
            "}";
    }
    

}
