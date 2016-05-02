package com.tsuruta.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Mouth
{
    Animator O2C, C2O;
    float x, y;
    SpriteBatch batcher;
    Texture mouthPlain = new Texture("Mouth.png");
    Texture mouthClosed = new Texture("MouthClosed.png");
    String state;
    Drop character;

    public Mouth(SpriteBatch batcher, Drop character)
    {
        O2C = new Animator("O2C.png", 7, 1, .04f, batcher, 210, 110);
        C2O = new Animator("C2O.png", 7, 1, .04f, batcher, 261, 120);
        C2O.create();
        O2C.create();
        state = "Open";
        this.batcher = batcher;
        this.character = character;
    }

    public void draw(float x, float y)
    {
        this.x = x;
        this.y = y;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            if (!(O2C.getFinished()))
            {
                O2C.updateLocation(x + 20, y);
                C2O.reset();
                O2C.render();
            }
            else
            {
                state = "Closed";
                batcher.begin();
                batcher.draw(mouthClosed, x, y-20, 250, 110);
                batcher.end();
            }
        }
        else
        {
            O2C.reset();
            if (state.equals("Closed"))
            {
                if (!(C2O.getFinished()))
                {
                    C2O.updateLocation(x, y);
                    C2O.render();
                }
                else
                {
                    state = "Open";
                    C2O.reset();
                }
            }
            else
            {
                batcher.begin();
                batcher.draw(mouthPlain, x, y, 261, 120);
                batcher.end();
            }
        }
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public void setState(String state)
    {
        this.state = state;
    }
}
