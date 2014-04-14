package com.epam.stud.shv.datalayer.datalayer.entities;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey_Shvardakov
 * Date: 2/19/14
 * Time: 6:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class Game {

    private int id;

    private User creator;    // player1
    private User enemy;      // player2



    private int state;

    private int lastStep;

    private int lastCell;

    /**
     * 2 - крестик
     * 1 - нолик
     * 0 - пусто
     */
    private int[] cells;

    /**
     * Создает квадратное поле игры
     * @param n число клеток на 1 стороне квадрата
     */
    public Game(int id, User creator,int n){
        this.id = id;
        this.creator = creator;
        cells = new int[n*n];
        for(int i = 0; i < cells.length; i++){
            cells[i]= 10;
        }
    }



    public int[] getCells() {
        return cells;
    }

    public void setCell(int cell, int side) {
        cells[cell] = side;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getEnemy() {
        return enemy;
    }

    public void setEnemy(User enemy) {
        this.enemy = enemy;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getLastCell() {
        return lastCell;
    }

    public void setLastCell(int lastCell) {
        this.lastCell = lastCell;
    }

    public int getLastStep() {
        return lastStep;
    }

    public void setLastStep(int lastStep) {
        this.lastStep = lastStep;
    }

}
