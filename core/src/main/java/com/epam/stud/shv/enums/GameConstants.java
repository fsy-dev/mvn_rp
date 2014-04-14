package com.epam.stud.shv.enums;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey_Shvardakov
 * Date: 2/19/14
 * Time: 7:32 PM
 * To change this template use File | Settings | File Templates.
 */
public enum GameConstants {
    TIME_ON_STEP_IN_MINUTES(60 * 1000),

    ZERO_PLAYER(1),
    CROSS_PLAYER(2),

    NONE(0),

    SQUIRE_SIDE(3);

    private final int number;

    private GameConstants(int number){
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
