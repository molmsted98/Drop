package com.tsuruta.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputCheck
{
    private Game g;
    private Drop mainCharacter;
    private int timer;

    public InputCheck(Game g)
    {
        this.g = g;
        this.mainCharacter = g.getMainCharacter();
        this.timer = 0;
    }

    public boolean checkForInput()
    {
        //Exit the game when 0 is pressed.
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
        {
            Gdx.app.exit();
            return true;
        }

        if (g.getGameState().equals("Running"))
        {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            {
                if (mainCharacter.getX() > 0)
                {
                    mainCharacter.moveLeft();
                    return true;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            {
                if (mainCharacter.getX() < Gdx.graphics.getWidth())
                {
                    mainCharacter.moveRight();
                    return true;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            {
                timer ++;
            }
            else
            {
                timer = 0;
            }
        }
        else if (g.getGameState().equals("Menu"))
        {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            {
                g.setGameState("Running");
                return true;
            }
        }
        else
        {
            System.out.println("What? Invalid game state!");
            return false;
        }
        return false;
    }
}
