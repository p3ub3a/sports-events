package com.sportsevents.api.model;

import java.util.List;

import com.sportsevents.entity.Event;

public class GetEventsResponse {
    private int pagesNr;

    private List<Event> records;

    public int getPagesNr() {
        return this.pagesNr;
    }

    public void setPagesNr(int pagesNr) {
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
