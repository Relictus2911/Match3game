package com.match3.game.animation;

import com.match3.game.entities.Tile;

import java.util.Iterator;
import java.util.Set;

public class AnimationAppear implements Animation
{
    private static final float totalDuration = 0.1666666f;
    private static final float totalDurationInv = 6.0f;
    private AnimationHandler handler;
    public Set<Tile> tiles;

    private float currentDuration;
    private float gemSize;
    public AnimationAppear(Set<Tile> tiles, float gemSize, AnimationHandler handler)
    {
        this.handler = handler;
        this.tiles = tiles;

        this.gemSize = gemSize;
        this.currentDuration = 0;
        System.out.println("AnimationAppear");
    }

    @Override
    public void update(float delta)
    {
        // TODO Auto-generated method stub
        this.currentDuration += delta;
        if (this.currentDuration >= AnimationAppear.totalDuration)
        {
            // gives the last time step to archive total time
            float deltaDiff = AnimationAppear.totalDuration-(this.currentDuration-delta);

            for (Iterator<Tile> it = tiles.iterator(); it.hasNext(); )
            {
                Tile nextTile = it.next();
                nextTile.sizeX = this.gemSize;//(int) newSize;
                nextTile.sizeY = this.gemSize;//(int) newSize;

                float newPosX = -0.5f*this.gemSize * ( deltaDiff * AnimationAppear.totalDurationInv);
                float newPosY = -0.5f*this.gemSize * ( deltaDiff * AnimationAppear.totalDurationInv);
                nextTile.x += newPosX;
                nextTile.y += newPosY;
            }



            this.handler.onComplete(this);
        }
        else
        {

            float newSize = this.gemSize * ( this.currentDuration * AnimationAppear.totalDurationInv);
            float newPosX = -0.5f*this.gemSize * ( delta * AnimationAppear.totalDurationInv);
            float newPosY = -0.5f*this.gemSize * ( delta * AnimationAppear.totalDurationInv);

            for (Iterator<Tile> it = tiles.iterator(); it.hasNext(); )
            {
                Tile nextTile = it.next();
                nextTile.sizeX =  newSize;
                nextTile.sizeY =  newSize;

                nextTile.x += newPosX;
                nextTile.y += newPosY;
            }


        }
    }
}