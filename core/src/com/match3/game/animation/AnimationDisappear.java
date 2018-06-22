package com.match3.game.animation;

import com.match3.game.entities.Tile;

import java.util.Iterator;
import java.util.Set;


public class AnimationDisappear implements Animation
{
    private static final float totalDuration = 0.1666666f;
    private static final float totalDurationInv = 6.0f;
    private AnimationHandler handler;
    public Set<Tile> tiles;

    private float currentDuration;
    private float gemSize;
    public AnimationDisappear(Set<Tile> tiles, float gemSize, AnimationHandler handler)
    {
        this.handler = handler;
        this.tiles = tiles;

        this.gemSize = gemSize;
        this.currentDuration = 0;
        System.out.println("AnimationDisappear");
    }

    @Override
    public void update(float delta)
    {
        // TODO Auto-generated method stub
        this.currentDuration += delta;
        if (this.currentDuration >= AnimationDisappear.totalDuration)
        {

            this.handler.onComplete(this);
        }
        else
        {
            float newSize = this.gemSize * (1 - this.currentDuration * AnimationDisappear.totalDurationInv);
            float newPosX = 0.5f*this.gemSize * ( delta * AnimationDisappear.totalDurationInv);
            float newPosY = 0.5f*this.gemSize * ( delta * AnimationDisappear.totalDurationInv);

            for (Iterator<Tile> it = tiles.iterator(); it.hasNext(); )
            {
                Tile nextTile = it.next();
                nextTile.sizeX = (int) newSize;
                nextTile.sizeY = (int) newSize;

                nextTile.x += newPosX;
                nextTile.y += newPosY;
            }


        }
    }
}