package com.match3.game.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.match3.game.registry.Registry;
import com.match3.game.utility.TileType;


public class Input implements InputProcessor {

    public Registry reg;

    public Vector3 mouse_position = new Vector3(0,0,0);

    private int touchedAtX = -1;
    private int touchedAtY = -1;


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    public int getCoordToCol(float coord)
    {
        return (int)(( coord - reg.tilesXOffset) / reg.TILESIZE);
    }

    public int getCoordToRow(float coord)
    {
        return (reg.tiles.length-1)-(int)(( coord - reg.tilesYOffset) / reg.TILESIZE);
    }

    public boolean isIdxInsideRowAndCol(int row, int col)
    {
        if((row >= 0) && (row < reg.tiles.length))
            if((col >= 0) && (col < reg.tiles.length))
                return true;

        return false;
    }

    public boolean trySwap(int posX, int posY, float swapDistance)
    {
        int dx = posX - this.touchedAtX;
        int dy = posY - this.touchedAtY;

        System.out.println("dx:" +dx + "  dy:"+dy);

        if (Math.abs(dx) > swapDistance || Math.abs(dy) > swapDistance)
        {
            int rowA = reg.activeRow;
            int colA = reg.activeCol;

            int rowB = reg.activeRow;
            int colB = reg.activeCol;


            if (Math.abs(dx) > Math.abs(dy))
            {
                colB += dx > 0 ? 1 : -1;
            }
            else
            {
                rowB += dy > 0 ? -1 : 1;
            }

            if(isIdxInsideRowAndCol(rowB, colB))
            {

                reg.trySwapTiles(rowA, colA, rowB, colB);

            }
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (reg.animations.size() > 0)
            return false;

        mouse_position.set(screenX, screenY, 0);
        reg.camera.unproject(mouse_position, reg.viewport.getScreenX(), reg.viewport.getScreenY(), reg.viewport.getScreenWidth(), reg.viewport.getScreenHeight());

        int rowIdx = this.getCoordToRow(mouse_position.y);
        int colIdx = this.getCoordToCol(mouse_position.x);

        System.out.println("S" + screenX + " " + screenY+ " == I " + mouse_position.x + " " + mouse_position.y);


        if(isIdxInsideRowAndCol(rowIdx, colIdx))
        {
            if (reg.tiles[rowIdx][colIdx].type != TileType.NONE) {

                this.touchedAtX = (int) mouse_position.x;
                this.touchedAtY = (int) mouse_position.y;

                if (reg.tileIsActive)
                    reg.tiles[reg.activeRow][reg.activeCol].isActivated = false;

                reg.tiles[rowIdx][colIdx].isActivated = true;
                reg.tileIsActive = true;
                reg.activeCol = colIdx;
                reg.activeRow = rowIdx;

                return true;
            }
        }
        else
        {

            if (reg.tileIsActive)
                reg.tiles[reg.activeRow][reg.activeCol].isActivated = false;

            reg.tileIsActive = false;
            reg.activeCol = -1;
            reg.activeRow = -1;

        }

        return false;


    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (reg.animations.size() > 0)
            return false;

        mouse_position.set(screenX, screenY, 0);
        reg.camera.unproject(mouse_position, reg.viewport.getScreenX(), reg.viewport.getScreenY(), reg.viewport.getScreenWidth(), reg.viewport.getScreenHeight());

        if(reg.tileIsActive == true)
         this.trySwap((int) mouse_position.x, (int) mouse_position.y, 0.25f * reg.TILESIZE);

        this.touchedAtX = -1;
        this.touchedAtY = -1;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
