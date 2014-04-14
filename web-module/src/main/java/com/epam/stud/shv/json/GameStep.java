package com.epam.stud.shv.json;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 24.02.14
 * Time: 2:02
 * To change this template use File | Settings | File Templates.
 */
public class GameStep {

    private int gameState;

    private int whoStep;



    private int yourSide;

    private int enemySide;

    private String yourName;

    private String enemyName;

    private long enemyTimeStep;

    /////////////////////////

    private int side; //размер доски
    private int cell;

    private int lastStep; // = либо yourSide либо enemySide

    private int gameId;


    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public int getWhoStep() {
        return whoStep;
    }

    public void setWhoStep(int whoStep) {
        this.whoStep = whoStep;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public int getYourSide() {
        return yourSide;
    }

    public void setYourSide(int yourSide) {
        this.yourSide = yourSide;
    }

    public int getEnemySide() {
        return enemySide;
    }

    public void setEnemySide(int enemySide) {
        this.enemySide = enemySide;
    }

    public String getYourName() {
        return yourName;
    }

    public void setYourName(String yourName) {
        this.yourName = yourName;
    }

    public int getLastStep() {
        return lastStep;
    }

    public void setLastStep(int lastStep) {
        this.lastStep = lastStep;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public long getEnemyTimeStep() {
        return enemyTimeStep;
    }

    public void setEnemyTimeStep(long enemyTimeStep) {
        this.enemyTimeStep = enemyTimeStep;
    }


}
