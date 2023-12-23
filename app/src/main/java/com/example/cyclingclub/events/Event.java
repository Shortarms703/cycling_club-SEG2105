package com.example.cyclingclub.events;

public class Event {

    private int eventID = -1;

    private String eventTypeName= "Not applicable";
    private String terrain = "Not applicable";
    private String eventLength= "Not applicable";
    private String eventAge = "Not applicable";
    private String eventFocus= "Not applicable";
    private String eventDate= "Not applicable"; //ddmmyyyy
    private String description= "Not applicable";
    private String distance = "Not applicable";

    public Event(String eventTypeName, String terrain, String eventLength, String eventAge, String eventFocus,
                 String eventDate, String description, String distance){
        this.eventTypeName= eventTypeName;
        this.terrain=terrain;
        this.eventLength=eventLength;
        this.eventAge=eventAge;
        this.eventFocus=eventFocus;
        this.eventDate=eventDate;
        this.description=description;
        this.distance=distance;
    }

    public int getEventID(){return this.eventID;}
    public String getTerrain(){return this.terrain;}
    public String getEventLength(){return this.eventLength;}
    public String getEventAge(){return this.eventAge;}
    public String getEventName(){return this.eventTypeName;}
    public String getEventDate(){return this.eventDate;}
    public String getEventFocus(){return this.eventFocus;}
    public String getDescription(){
        return this.description;
    }
    public String getDistance(){
        return this.distance;
    }

    public void setEventID(int id) {
        this.eventID = id;
    }
}
