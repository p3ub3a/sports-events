package com.sportsevents.api.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UpdatePlayersModel {
    @NotNull
    private Long eventId;

    @NotNull
    private String playerName;

    @NotNull
    @Pattern(message = "Invalid type: possible choices - chess, pingpong, swimming, tennis", regexp = "(?i)chess|tennis|swimming|pingpong")
    private String type;


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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
            " eventId='" + getEventId() + "'" +
            ", playerName='" + getPlayerName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
    

}
