package com.epam.stud.shv.datalayer.cash;


import com.epam.stud.shv.datalayer.datalayer.entities.User;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey_Shvardakov
 * Date: 4/6/14
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameCashTest extends TestCase {

    private GameCash cash;

    @Before
    public void setUp() {
        cash = GameCash.getInstance();
    }

    @After
    public void tearDown(){
        cash = null;
    }

    @Test
    public void testRemoveGame(){
        int gameId = cash.addGame(new User(), 3);
        assertTrue(cash.deleteGame(gameId));
    }



}
