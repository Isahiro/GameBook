package com.GameBook;

/**
 * Created by Joshua Dalton on 11/18/2015.
 */
import java.util.Date;

public class CalEvent
{
    private Date date;
    private String name;
    private String location;
    private String game;
    private String description;

    CalEvent(){}

    CalEvent (Date d, String n, String l, String g, String desc)
    {
        date = d;
        name = n;
        location = l;
        game = g;
        description = desc;
    }

    public void setDate(Date d){date = d;}
    public void setName(String n){name = n;}
    public void setLocation(String l){location = l;}
    public void setGame(String g){game = g;}
    public void setDescription(String desc){description = desc;}

    public Date getDate(){return date;}
    public String getName(){return name;}
    public String getLocation(){return location;}
    public String getGame(){return game;}
    public String getDescription(){return description;}
}
