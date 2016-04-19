package com.tsuruta.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {

	private Graphics graphics;
	private String GameState;
	private Drop mainCharacter;
	private SpriteBatch batch;
	private InputCheck input;
	private int timeout;

	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		mainCharacter = new Drop(0, 0, batch);
		graphics = new Graphics(this, batch, mainCharacter);
		GameState = "Menu";
		input = new InputCheck(this);
	}

	@Override
	public void render ()
	{
		input.checkForInput();
		graphics.draw();

		if (getGameState().equals("Running") && !input.checkForInput())
		{
			timeout ++;
		}
		else
		{
			timeout = 0;
		}

		if (timeout == 500)
		{
			setGameState("Menu");
		}
	}

	public String getGameState()
	{
		return GameState;
	}

	public void setGameState(String g)
	{
		this.GameState = g;
	}

	public Drop getMainCharacter()
	{
		return mainCharacter;
	}
}
