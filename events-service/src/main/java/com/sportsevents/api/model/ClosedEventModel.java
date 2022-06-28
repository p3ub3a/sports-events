package com.sportsevents.api.model;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotNull;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ClosedEventModel {

    @NotNull
    private Long eventId;

    @NotNull
    private String closedBy;

    @NotNull
    @JsonbDateFormat(value = "yyyy'-'MM'-'dd'T'HH':'mm'")
    private LocalDateTime closedDate;

    @NotNull
    private String winner;
    

    public Long getEventId() {
        return this.eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
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
            ", closedBy='" + getClosedBy() + "'" +
            ", winner='" + getWinner() + "'" +
            "}";
    }

}
