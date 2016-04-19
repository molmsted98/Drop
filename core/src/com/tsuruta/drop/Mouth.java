package com.tsuruta.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Mouth
{
    MouthAnimator O2C, C2O;
    float x, y;
    SpriteBatch batcher;
    Texture mouthPlain = new Texture("Mouth.png");
    Texture mouthClosed = new Texture("MouthClosed.png");
    String state;
    Drop character;

    public Mouth(SpriteBatch batcher, Drop character)
    {
        O2C = new MouthAnimator("O2C.png", 7, 1, .065f, batcher, this);
        C2O = new MouthAnimator("C2O.png", 7, 1, .065f, batcher, this);
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

        if (Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            if (!(O2C.getFinished()))
            {
                C2O.reset();
                O2C.render();
            }
            else
            {
                state = "Closed";
                batcher.begin();
                batcher.draw(mouthClosed, x, y, 261, 120);
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
