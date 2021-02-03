package com.sportsevents.api.model;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ClosedEventModel {

    @NotNull
    private Long eventId;

    @NotNull
    @Pattern(message = "Invalid type: possible choices - chess, pingpong, swimming, tennis", regexp = "(?i)chess|tennis|swimming|pingpong")
    private String type;

    @NotNull
    private String closedBy;

    @NotNull
    @JsonbDateFormat(value = "yyyy'-'MM'-'dd'T'HH':'mm':'ss")
    private LocalDateTime closedDate;

    @NotNull
    private String winner;
    

    public Long getEventId() {
        return this.eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClosedBy() {
        return this.closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public String getWinner() {
        return this.winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public LocalDateTime getClosedDate() {
        return this.closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    @Override
    public String toString() {
        return "{" +
            " eventId='" + getEventId() + "'" +
            ", type='" + getType() + "'" +
            ", closedBy='" + getClosedBy() + "'" +
            ", winner='" + getWinner() + "'" +
            "}";
    }

}
