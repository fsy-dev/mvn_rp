package com.epam.stud.shv.datalayer.cash;

import com.epam.stud.shv.enums.GameConstants;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey_Shvardakov
 * Date: 2/24/14
 * Time: 12:27 PM
 * To change this template use File | Settings | File Templates.
 */
public final class GameSolverTest extends TestCase {


    @Test
    public void testZeroWinner(){
        int[] grid = {1, 10 , 10 ,
                      1, 10 , 10 ,
                      1, 10 , 10
                     };

        int winner = GameSolver.solve(grid);

        assertEquals(winner, GameConstants.ZERO_PLAYER.getNumber());
    }

    @Test
    public void testCrossWinner(){
        int[] grid = {2, 10 , 10 ,
                      2, 10 , 10 ,
                      2, 10 , 10
                     };

        int winner = GameSolver.solve(grid);

        assertEquals(winner, GameConstants.CROSS_PLAYER.getNumber());
    }



}
