package com.sportsevents.eventsservicespring.api.model;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import org.springframework.format.annotation.DateTimeFormat;

public class EventModel {
    
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    @Pattern(message = "Invalid type: possible choices - chess, pingpong, swimming, tennis", regexp = "(?i)chess|tennis|swimming|pingpong")
    private String type;

    @DateTimeFormat( iso = DateTimeFormat.ISO.DATE_TIME)
    // @JsonDeserialize(using = LocalDateDeserializer.class)
    // @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDateTime scheduledDate;
    
    @NotNull
    private String status;

    @NotNull
    private String facilitator;

    @NotNull
    private String location;

    @NotNull
    private boolean outdoors;


    public Long getId() {
        return this.id;
    }

    @JsonIgnore
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

    public LocalDateTime getScheduledDate() {
        return this.scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFacilitator() {
        return this.facilitator;
    }

    public void setFacilitator(String facilitator) {
        this.facilitator = facilitator;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", type='" + getType() + "'" +
            ", scheduledDate='" + getScheduledDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", facilitator='" + getFacilitator() + "'" +
            ", location='" + getLocation() + "'" +
            ", outdoors='" + isOutdoors() + "'" +
            "}";
    }


}

    
