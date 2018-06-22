package com.match3.game.gamestate;

import com.match3.game.animation.AnimationAppear;
import com.match3.game.animation.AnimationFalling;
import com.match3.game.entities.Tile;
import com.match3.game.registry.Registry;
import com.match3.game.utility.TileType;

import java.util.HashSet;
import java.util.Set;

public class GStateInsertTiles implements GState {

    public Registry reg;

    public Set<Tile> InsertTiles = new HashSet<Tile>();

    public GStateInsertTiles(Registry reg)
    {
        this.reg = reg;
    }
    @Override
    public void doLogic() {

        insertRandomTilesOnTop();

        if(InsertTiles.size() != 0)
        {
            reg.animations.add(new AnimationAppear(InsertTiles, reg.TILESIZE, reg));
            reg.changeGameState(new GStateFallingTiles(reg));
        }
        else {
            reg.changeGameState(new GStateFindMatch(reg));
        }
    }

    public void insertRandomTilesOnTop()
    {
        InsertTiles.clear();


        for (int col = 0; col < reg.tiles.length; col++) {
            if (reg.tiles[0][col].type == TileType.NONE) {
                reg.tiles[0][col].type = TileType.getRandom();
                reg.tiles[0][col].sizeX = 0;
                reg.tiles[0][col].sizeY = 0;

                reg.tiles[0][col].x = (col * reg.TILESIZE) + reg.tilesXOffset +  reg.TILESIZE/2.0f;
                reg.tiles[0][col].y = ((reg.tiles.length - 1 - 0) * reg.TILESIZE) + reg.tilesYOffset +  reg.TILESIZE/2.0f;

                InsertTiles.add(reg.tiles[0][col]);
            }
        }

    }

}
