package com.epam.stud.shv.datalayer.cash;


import com.epam.stud.shv.enums.GameConstants;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey_Shvardakov
 * Date: 2/24/14
 * Time: 12:27 PM
 * To change this template use File | Settings | File Templates.
 */
public final class GameSolver {


    private static int CROSS_WIN = 6;
    private static int ZERO_WIN = 3;


    public static int solve(int[] grid) {
        /*
            0 3 6
            1 4 7
            2 5 8

            game grid
         */


        // по вертикали
        int line036 = grid[0] + grid[3] + grid[6];
        int line147 = grid[1] + grid[4] + grid[7];
        int line258 = grid[2] + grid[5] + grid[8];

        // по горизонтали
        int line012 = grid[0] + grid[1] + grid[2];
        int line345 = grid[3] + grid[4] + grid[5];
        int line678 = grid[6] + grid[7] + grid[8];

        // диагонали
        int line048 = grid[0] + grid[4] + grid[8];
        int line246 = grid[2] + grid[4] + grid[6];


        if(isWin(CROSS_WIN, line036, line147, line258, line012, line345, line678, line048, line246)){
            return GameConstants.CROSS_PLAYER.getNumber();
        } else if(isWin(ZERO_WIN, line036, line147, line258, line012, line345, line678, line048, line246)){
            return GameConstants.ZERO_PLAYER.getNumber();
        } else{
            return GameConstants.NONE.getNumber();
        }

    }

    private static boolean isWin(int winner, int line036, int line147, int line258,
                                 int line012, int line345, int line678, int line048, int line246) {
        return (winner == line036 || winner == line147
                || winner == line258 || winner == line012
                || winner == line345 || winner == line678
                || winner == line048 || winner == line246);
    }

}
