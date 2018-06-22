package com.match3.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.match3.game.gamestate.GStateFindMatch;
import com.match3.game.registry.Registry;

public class Match3 extends Game {

	public Registry reg;
	
	@Override
	public void create () {

		reg = new Registry();
		reg.changeGameState(new GStateFindMatch(reg));

		reg.draw.update();
	}

	@Override
	public void render () {

		reg.logic();

		reg.logic.update();

		reg.draw.update();

		if (this.getScreen() != reg )
		{
			this.setScreen(reg);
			Gdx.input.setInputProcessor(reg.input);
		}
		super.render();

	}

	@Override
	public void resize(int width, int height)
	{
		reg.viewport.update(width, height, true);
		super.resize(width, height);
	}
	
	@Override
	public void dispose () {

		reg.dispose();
	}
}
