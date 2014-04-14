package com.epam.stud.shv.datalayer.cash;



import com.epam.stud.shv.datalayer.datalayer.entities.Game;
import com.epam.stud.shv.datalayer.datalayer.entities.User;
import com.epam.stud.shv.enums.GameConstants;
import com.epam.stud.shv.enums.GameState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey_Shvardakov
 * Date: 2/19/14
 * Time: 6:44 PM
 * To change this template use File | Settings | File Templates.
 */
public final class GameCash {

    private volatile static GameCash instance = null;


    private List<Game> games = new ArrayList<Game>();

    private List<User> users = new ArrayList<User>();

    private static int sequence = 0;

    private void GameCash() {
    }


    public static GameCash getInstance() {
        if (instance == null) {
            synchronized (GameCash.class) {
                if (instance == null) {
                    instance = new GameCash();
                }
            }
        }
        return instance;
    }

    public synchronized int addGame(User creator, int m) {
        sequence++;
        games.add(new Game(sequence, creator, m));
        return sequence;
    }

    public synchronized boolean deleteGame(int gameId) {
        Game game = getGameById(gameId);

        if(game != null){
            games.remove(game);
            return true;
        }

        return false;
    }

    public synchronized List<Game> getGameList() {
//        List<Game> result = new ArrayList<Game>();
//        Collections.copy(result, games);
//        return result;
        return games;
    }

    public synchronized Game getGameById(int id) {
        Game game = null;
        try{
            for(int i = 0; i < games.size(); i++){
                if(games.get(i).getId() == id){
                    return games.get(i);
                }
            }
        }
        catch(Exception e){
            // do nothing
            // another user deleted this game
        }
        return game;
    }

    private Game fingGameById(int id){
        Game game = null;
        try{
            for(int i = 0; i < games.size(); i++){
                if(games.get(i).getId() == id){
                    return games.get(i);
                }
            }
        }
        catch(Exception e){
            // do nothing
            // another user deleted this game
        }
        return game;
    }


    public synchronized int makeStep(int gameId, int cell, int side) {
        Game game = getGameById(gameId);

        // ход можно сделать только в пустое поле
        if (game.getCells()[cell] == 10) {
            game.setLastStep(side);
            game.setLastCell(cell);

            game.setCell(cell, side);

            //TODO наверняка ошибка где-то, перепроверить
            // создатель ходит
            if(side == game.getCreator().getSide()){

                if(game.getCreator().getTimeStep() != null){
                    // если время вышло
                    if((new Date()).getTime() - game.getCreator().getTimeStep().getTime() >  GameConstants.TIME_ON_STEP_IN_MINUTES.getNumber()){
                        return GameState.YOU_LOOSE.getState();
                    } else{
                        // время есть, я сходил
                        game.getEnemy().setTimeStep(new Date());
                    }

                } else{
                    // мой первый ход
                    game.getCreator().setTimeStep(new Date());

                }

            // противник ходит
            } else if(side == game.getEnemy().getSide()){
                if(game.getEnemy().getTimeStep() != null){
                    // если время вышло
                    if((new Date()).getTime() - game.getEnemy().getTimeStep().getTime() >  GameConstants.TIME_ON_STEP_IN_MINUTES.getNumber()){
                        return GameState.YOU_LOOSE.getState();
                    } else{
                        // время есть, я сходил
                        game.getCreator().setTimeStep(new Date());
                    }

                } else{
                    // мой первый ход
                    game.getEnemy().setTimeStep(new Date());

                }

            }

        }


        // проверка на выигрыш и смена состояния игры

        int result = GameSolver.solve(game.getCells());

        if (result == GameConstants.CROSS_PLAYER.getNumber()) {
            return (side == GameConstants.CROSS_PLAYER.getNumber()) ? GameState.YOU_WIN.getState() :
                    GameState.YOU_LOOSE.getState();


        } else if (result == GameConstants.ZERO_PLAYER.getNumber()) {
            return (side == GameConstants.ZERO_PLAYER.getNumber()) ? GameState.YOU_WIN.getState() :
                    GameState.YOU_LOOSE.getState();
        }
//        } else if(result == GameConstants.NONE.getNumber()){
//            return GameState.IN_PROCESS.getState();
//        }

        return GameState.IN_PROCESS.getState();

//        return GameState.YOU_LOOSE.getState();
//        return GameState.YOU_WIN.getState();
    }


}
