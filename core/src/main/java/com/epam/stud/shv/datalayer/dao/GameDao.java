package com.epam.stud.shv.datalayer.dao;


import com.epam.stud.shv.datalayer.datalayer.entities.Game;
import com.epam.stud.shv.datalayer.datalayer.entities.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey_Shvardakov
 * Date: 2/19/14
 * Time: 6:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GameDao {

    int createGame(User creator);

    boolean deleteGame(int gameId);

    int makeStep(int gameId, int cell, int side);

    List<Game> getGameList();

    Game getGameById(int id);



}
