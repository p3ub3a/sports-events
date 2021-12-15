package com.sportsevents.api.model;

import java.util.List;

import com.sportsevents.entity.Event;

public class GetEventsResponse {
    private Long pagesNr;

    private List<Event> records;

    public Long getPagesNr() {
        return this.pagesNr;
    }

    public void setPagesNr(Long pagesNr) {
        this.pagesNr = pagesNr;
    }

    public List<Event> getRecords() {
        return this.records;
    }

    public void setRecords(List<Event> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "{" +
            " totalRecords='" + getPagesNr() + "'" +
            ", events='" + getRecords() + "'" +
            "}";
    }

}
