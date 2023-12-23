package com.example.cyclingclub.events;

public class ClubEvent {
    private int id = -1;
    private int eventTypeID;
    private String eventTypeName;
    private int clubOwnerID;
    private String diff;
    private String route;
    private double fee;
    private final int MAX_NUM_PARTICIPANTS;
    private int numParticipants;

    public ClubEvent(int eventTypeID, int clubOwnerID, String diff, String route, double fee,
                     int maxNumParticipants, int numParticipants) {
        this.eventTypeID = eventTypeID;
        this.clubOwnerID = clubOwnerID;
        this.diff = diff;
        this.route = route;
        this.fee = fee;
        MAX_NUM_PARTICIPANTS = maxNumParticipants;
        this.numParticipants = numParticipants;
    }

    public int getClubEventID() { return id; }
    public int getEventType() {
        return eventTypeID;
    }
    public String getEventTypeName() { return eventTypeName; }
    public int getClubOwnerID() { return clubOwnerID; }
    public String getDifficulty() {
        return diff;
    }
    public String getRoute() {
        return route;
    }
    public double getFee() {
        return fee;
    }
    public int getMaxNumParticipants() { return MAX_NUM_PARTICIPANTS; }
    public int getNumParticipants() { return numParticipants; }

    public void setClubEventID(int id) { this.id = id; }
    public void setEventTypeName(String name) { this.eventTypeName = name; }

}
