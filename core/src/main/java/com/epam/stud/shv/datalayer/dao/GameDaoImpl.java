package com.epam.stud.shv.datalayer.dao;


import com.epam.stud.shv.datalayer.cash.GameCash;
import com.epam.stud.shv.datalayer.datalayer.entities.Game;
import com.epam.stud.shv.datalayer.datalayer.entities.User;
import com.epam.stud.shv.enums.GameConstants;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey_Shvardakov
 * Date: 2/19/14
 * Time: 6:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameDaoImpl implements GameDao {

    private GameCash cash = GameCash.getInstance();



    @Override
    public int createGame(User creator) {
        return cash.addGame(creator, GameConstants.SQUIRE_SIDE.getNumber());
    }

    @Override
    public boolean deleteGame(int gameId) {
        return cash.deleteGame(gameId);
    }

    @Override
    public int makeStep(int gameId, int cell, int side) {
        return cash.makeStep(gameId, cell, side);
    }

    @Override
    public List<Game> getGameList() {
        return cash.getGameList();
    }

    @Override
    public Game getGameById(int id) {
        return cash.getGameById(id);
    }

}
