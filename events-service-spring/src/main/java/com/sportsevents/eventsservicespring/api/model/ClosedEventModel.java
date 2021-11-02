package com.sportsevents.eventsservicespring.api.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ClosedEventModel {

    @NotNull
    @NotBlank(message = "Id is mandatory")
    private Long eventId;

    @NotNull
    @NotBlank(message = "Type is mandatory")
    @Pattern(message = "Invalid type: possible choices - chess, pingpong, swimming, tennis", regexp = "(?i)chess|tennis|swimming|pingpong")
    private String type;

    @NotNull
    @NotBlank(message = "Closed by is mandatory")
    private String closedBy;

    @NotNull
    @NotBlank(message = "Closed date is mandatory")
    @JsonFormat(pattern = "yyyy'-'MM'-'dd'T'HH':'mm':'ss")
    private LocalDateTime closedDate;

    @NotNull
    @NotBlank(message = "Winner is mandatory")
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
