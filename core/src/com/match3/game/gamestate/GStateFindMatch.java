package com.match3.game.gamestate;

import com.match3.game.animation.AnimationDisappear;
import com.match3.game.entities.Tile;
import com.match3.game.registry.Registry;
import com.match3.game.utility.TileType;

import java.util.HashSet;
import java.util.Set;


public class GStateFindMatch implements GState{

    public Set<Tile> MatchedTiles = new HashSet<Tile>();

    public Registry reg;

    public GStateFindMatch(Registry reg)
    {
        this.reg = reg;
    }

    @Override
    public void doLogic() {

        if(checkMatches() == false) {
            reg.changeGameState(new GStateIdleUser(reg));
        }
        else {
            animationTilesDisappear();
            reg.changeGameState(new GStateScoringMatch(reg, MatchedTiles));
        }
    }

    public boolean checkMatches()
    {
        boolean foundMatch = false;

        MatchedTiles.clear();
        foundMatch = checkMatchInRow(5) | checkMatchInColumn(5) | checkMatchInRow(4) | checkMatchInColumn(4) | checkMatchInRow(3) | checkMatchInColumn(3);

        return foundMatch;

    }


    public boolean checkMatchInRow(int length) {

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
                            for (int k = 0; k < length; k++) {

                                MatchedTiles.add(reg.tiles[row + k][col]);
                            }
                            foundMatchInRow = true;
                        }

                    }
                }
            }
        }

        return foundMatchInRow;
    }

    public boolean checkMatchInColumn(int length) {

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
                            for (int k = 0; k < length; k++) {

                                MatchedTiles.add(reg.tiles[row][col + k]);
                            }
                            foundMatchInCol = true;
                        }

                    }
                }
            }
        }

        return foundMatchInCol;
    }

    public void animationTilesDisappear()
    {
        if (MatchedTiles.size() > 0)
        {
            reg.animations.add(new AnimationDisappear(MatchedTiles, reg.TILESIZE, reg));
        }
    }

}
