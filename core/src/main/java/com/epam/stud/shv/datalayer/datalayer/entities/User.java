package com.epam.stud.shv.datalayer.datalayer.entities;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey_Shvardakov
 * Date: 2/19/14
 * Time: 6:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class User {


    private String name;

    private String sessionId;

    /**
     * 2 - крестик
     * 1 - нолик
     */
    private int side;

    private Date timeStep;


    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(Date timeStep) {
        this.timeStep = timeStep;
    }

    @Override
    public String toString(){
        return name + "/" + side + "/";
    }


}
