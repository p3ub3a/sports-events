package com.sportsevents.api.model;

import java.util.List;

import com.sportsevents.entity.Event;

public class GetEventsResponse {
    private int futurePagesNr;
    private int pastPagesNr;
    private List<Event> futureRecords;
    private List<Event> pastRecords;

    public int getFuturePagesNr() {
        return this.futurePagesNr;
    }

    public void setFuturePagesNr(int futurePagesNr) {
        this.futurePagesNr = futurePagesNr;
    }

    public int getPastPagesNr() {
        return this.pastPagesNr;
    }

    public void setPastPagesNr(int pastPagesNr) {
        this.pastPagesNr = pastPagesNr;
    }

    public List<Event> getFutureRecords() {
        return this.futureRecords;
    }

    public void setFutureRecords(List<Event> futureRecords) {
        this.futureRecords = futureRecords;
    }

    public List<Event> getPastRecords() {
        return this.pastRecords;
    }

    public void setPastRecords(List<Event> pastRecords) {
        this.pastRecords = pastRecords;
    }

    @Override
    public String toString() {
        return "{" +
            " futurePagesNr='" + getFuturePagesNr() + "'" +
            ", pastPagesNr='" + getPastPagesNr() + "'" +
            ", futureRecords='" + getFutureRecords() + "'" +
            ", pastRecords='" + getPastRecords() + "'" +
            "}";
    }
}
