package com.match3.game.logic;


import com.badlogic.gdx.math.Vector3;
import com.match3.game.registry.Registry;
import com.match3.game.utility.TileType;

public class Logic {

    public Registry reg;

    public Vector3 mouse_position = new Vector3(0,0,0);



    public void update() {

        if (reg.animations.size() > 0)
            return;

    }

    public boolean isSwapSuccessfulDryRun(int rowA, int colA, int rowB, int colB)
    {
        boolean moveIsPossible = false;

        TileType swapTmpType = reg.tiles[rowA][colA].type;
        reg.tiles[rowA][colA].type = reg.tiles[rowB][colB].type;
        reg.tiles[rowB][colB].type = swapTmpType;

        moveIsPossible = checkMatchesDryRun();

        reg.tiles[rowB][colB].type = reg.tiles[rowA][colA].type;
        reg.tiles[rowA][colA].type = swapTmpType;

        return moveIsPossible;
    }

    public boolean checkMatchesDryRun()
    {
        boolean foundMatch = false;

        foundMatch = checkMatchInRowDryRun(3) | checkMatchInColumnDryRun(3);
        return foundMatch;

    }



    public boolean checkMatchInRowDryRun(int length) {

        boolean foundMatchInRow = false;

        for (int row = 0; row < reg.tiles.length; row++) {
            for (int col = 0; col < reg.tiles.length; col++) {
                if ((reg.tiles[row][col].type != TileType.MATCH) && (reg.tiles[row][col].type != TileType.NONE) ) {
                    if ((row + (length-1)) < reg.tiles.length) {

                        int count = 0;
                        for(int idx = 1; idx < length; idx++)
                        {
                            if(reg.tiles[row][col].type == reg.tiles[row + idx][col].type)
                                count++;
                        }

                        if(count == (length-1))
                        {
                            foundMatchInRow = true;
                        }

                    }
                }
            }
        }

        return foundMatchInRow;
    }

    public boolean checkMatchInColumnDryRun(int length) {

        boolean foundMatchInCol = false;

        for (int row = 0; row < reg.tiles.length; row++) {
            for (int col = 0; col < reg.tiles.length; col++) {
                if ((reg.tiles[row][col].type != TileType.MATCH) && (reg.tiles[row][col].type != TileType.NONE) ) {
                    if ((col + (length-1)) < reg.tiles.length) {

                        int count = 0;
                        for(int idx = 1; idx < length; idx++)
                        {
                            if(reg.tiles[row][col].type == reg.tiles[row][col + idx].type)
                                count++;
                        }

                        if(count == (length-1))
                        {
                            foundMatchInCol = true;
                        }

                    }
                }
            }
        }

        return foundMatchInCol;
    }
}
