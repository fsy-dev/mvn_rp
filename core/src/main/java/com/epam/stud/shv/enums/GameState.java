package com.epam.stud.shv.enums;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 24.02.14
 * Time: 1:38
 * To change this template use File | Settings | File Templates.
 */
public enum GameState {

    NOT_STARTED(0),
    FIRST_STEP(5),
    IN_PROCESS(1),
    YOU_WIN(2),
    YOU_LOOSE(3),
    FRIENDSHIP(6),
    ENEMY_EXIT(4);


    private int state;

    private GameState(int state){
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
