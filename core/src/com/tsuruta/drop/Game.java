package com.tsuruta.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Game extends ApplicationAdapter
{
	private Graphics graphics;
	private String GameState;
	private Drop mainCharacter;
	private SpriteBatch batch;
	private ArrayList<Platform> platforms;
	private Platform p1, p2;
	private InputCheck input;
	private int timeout;
	private final float gravity = -1f;

	@Override
	public void create ()
	{
		batch = new SpriteBatch();

		//Set up game elements
		mainCharacter = new Drop(0, 0, batch, this);

		platforms = new ArrayList<Platform>();
		p1 = new Platform(600, 300, batch);
		p2 = new Platform(200, 600, batch);
		platforms.add(p1);
		platforms.add(p2);

		graphics = new Graphics(this, batch, mainCharacter, platforms);
		GameState = "Menu";
		input = new InputCheck(this);
	}

	@Override
	public void render ()
	{
		input.checkForInput();
		graphics.draw();

		//Physics magic or something
		if (getGameState().equals("Running"))
		{
			//Move the character up or down.
			mainCharacter.setY(mainCharacter.getY() + mainCharacter.getYVel());

			//Check for collision.
			if (mainCharacter.isContacting())
			{
				//Stop moving down when there is a platform underneath you.
				mainCharacter.setYVel(0f);
			}
			else
			{
				//Gravity pulls you down to the next platform.
				mainCharacter.setYVel(mainCharacter.getYVel() + gravity);
			}
		}

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

	public ArrayList<Platform> getPlatforms()
	{
		return platforms;
	}
}
