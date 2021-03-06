package com.match3.game.registry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.match3.game.animation.Animation;
import com.match3.game.animation.AnimationDisappear;
import com.match3.game.animation.AnimationFalling;
import com.match3.game.animation.AnimationHandler;
import com.match3.game.animation.AnimationSwap;
import com.match3.game.gamestate.GState;
import com.match3.game.gamestate.GStateFindMatch;
import com.match3.game.input.Input;
import com.match3.game.draw.Draw;
import com.match3.game.entities.Tile;
import com.match3.game.logic.Logic;
import com.match3.game.utility.TileType;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Gdx.input;


public class Registry extends ScreenAdapter implements AnimationHandler {

    public String windowName = "Match 3 Game";
    public int windowWidth = 480;
    public int windowHeight = 800;
    public OrthographicCamera camera;
    public FitViewport viewport;

    public Tile[][] tiles;
    public float tilesXOffset = 16;
    public float tilesYOffset = 48;

    public Draw draw;
    public Logic logic;

    public Input input;

    public SpriteBatch batch;
    public Texture img;

    public boolean tileIsActive = false;
    public int activeCol = 0;
    public int activeRow = 0;

    public int activeColToSwap = 0;
    public int activeRowToSwap = 0;

    public static final int TILESIZE = 56;

    public GState gameState;

    public int score = 0;

    public List<Animation> animations = new ArrayList<Animation>();
    public boolean swapOccurred = false;

    public Registry(){

        Gdx.graphics.setTitle(windowName);
        Gdx.graphics.setWindowedMode(windowWidth,windowHeight);
        camera = new OrthographicCamera(windowWidth,windowHeight);
        camera.setToOrtho(false, windowWidth, windowHeight);
        viewport = new FitViewport(windowWidth,windowHeight,camera);
        viewport.apply();

        draw = new Draw();
        draw.reg = this;

        logic = new Logic();
        logic.reg = this;

        input = new Input();
        input.reg = this;

        batch = new SpriteBatch();
        img = new Texture("background.jpg");

        tiles = new Tile[8][8];
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                Tile newTile = new Tile(TileType.getRandom(), (col * TILESIZE) + tilesXOffset, ((tiles.length -1 -row) * TILESIZE) + tilesYOffset, TILESIZE, TILESIZE);

                tiles[row][col] = newTile;
            }
        }


    }

    public void logic()
    {
        if (animations.size() > 0)
            return;

         gameState.doLogic();
    }

    @Override
    public void render(float delta)
    {

        for (int index = 0; index < this.animations.size(); ++index)
        {
            this.animations.get(index).update(delta);
        }


    }

    public void trySwapTiles(int row1, int col1, int row2, int col2)
    {
        Tile A = tiles[row1][col1];
        Tile B = tiles[row2][col2];
        boolean success = logic.isSwapSuccessfulDryRun(row1, col1, row2, col2);

        this.animations.add(new AnimationSwap(A, B, !success, this));

        activeCol = col1;
        activeRow = row1;

        activeColToSwap = col2;
        activeRowToSwap = row2;

        if (success)
        {
            swapOccurred = true;
            Tile swapTmpTile = tiles[activeRow][activeCol];
            tiles[activeRow][activeCol] = tiles[activeRowToSwap][activeColToSwap];
            tiles[activeRowToSwap][activeColToSwap] = swapTmpTile;

            swapOccurred = false;

            changeGameState(new GStateFindMatch(this));
        }
    }



    @Override
    public void onComplete(Animation animation)
    {
        this.animations.remove(animation);
    }




    public void changeGameState(GState gstate)
    {
        this.gameState = gstate;
    }


    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
