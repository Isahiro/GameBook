package com.GameBook;

import javax.swing.*;

/**
 * Created by Joshua Dalton on 11/18/2015.
 * EventLabel is an extension of JLabel that will store information about an event
 */
public class EventLabel extends JLabel
{
    private CalEvent event;

    EventLabel(){}

    EventLabel(CalEvent e)
    {
        super(e.getName() );
        event = e;
    }

    public void setEvent(CalEvent e){event = e;}

    public CalEvent getEvent() {return event;}
}
