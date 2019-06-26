package com.example.nikitaparmar.eventsearch;

import android.app.Activity;
import android.widget.ImageView;

public class EventListClass {
    public String eid;
    public String edate;
    public String etime;
    public String eventName;
    public String category;
    public String venueName;

    public EventListClass() { }

    public EventListClass(String edate, String etime, String eventname, String category, String venueName) {
        this.edate = edate;
        this.etime = etime;
        this.eventName = eventname;
        this.category = category;
        this.venueName = venueName;
    }

    public String getDateTime() {
        return edate + " " + etime;
    }
    public String getEventName()
    {
        return eventName;
    }
    public String getCategory() {
        return category;
    }
    public String getVenueName(){
        return venueName;
    }

    public String getEid() {
        return eid;
    }
    public void setEid(String eid) {
        this.eid = eid;
    }

    public void setDateTime(String date, String time) {
        edate = date;
        etime = time;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public void setCategory(String category) {
        this.category = category;
    }
     public void setVenueName(String venueName){
        this.venueName = venueName;
    }
}
