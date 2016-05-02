package com.tsuruta.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Eyes
{
    private float x, y;
    private Animator blinkAnimation, openEyes, closeEyes;
    private SpriteBatch batcher;
    private Sprite eye, closedEye;
    private String state;
    private Random random;
    Drop drop;

    public Eyes(float x, float y, SpriteBatch batcher, Drop drop)
    {
        this.x = x;
        this.y = y;
        this.drop = drop;
        this.random = new Random();

        Texture centerEye = new Texture("Centered.png");
        Texture eyeClosed = new Texture("EyeClosed.png");

        eye = new Sprite(centerEye);
        closedEye = new Sprite(eyeClosed);

        blinkAnimation = new Animator("blinkSheet.png", 5, 2, .04f, batcher, 103, 117);
        openEyes = new Animator("EyeOpenSheet.png", 5, 1, .05f, batcher, 103, 117);
        closeEyes = new Animator("EyeCloseSheet.png", 5, 1, .05f, batcher, 90, 100);

        blinkAnimation.create();
        openEyes.create();
        closeEyes.create();

        this.batcher = batcher;
        state = "Open";
    }

    public void draw(float x, float y, float xShift)
    {
        this.x = x + xShift;
        this.y = y;

        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            //Draw the base eye.
            batcher.begin();
            batcher.draw(eye, x + xShift, y, 103, 117);
            batcher.end();
        }
        else
        {
            if (!(closeEyes.getFinished()))
            {
                batcher.begin();
                batcher.draw(eye, x + xShift-5, y-10, 90, 100);
                batcher.end();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            if (!(closeEyes.getFinished()))
            {
                closeEyes.updateLocation(x-5 + xShift, y-10);
                closeEyes.render();
            }
            else
            {
                state = "Closed";
                batcher.begin();
                batcher.draw(closedEye, x + xShift - 5, y-30, 90, 100);
                batcher.end();
            }
        }
        else
        {
            closeEyes.reset();
        }

        //Draw extras for the eyes.
        if ((!Gdx.input.isKeyPressed(Input.Keys.LEFT)
                || !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) && !Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            if (state.equals("Closed"))
            {
                if (!(openEyes.getFinished()))
                {
                    openEyes.updateLocation(x + xShift , y);
                    openEyes.render();
                }
                else
                {
                    state = "Open";
                    openEyes.reset();
                }
            }
        }
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }
}