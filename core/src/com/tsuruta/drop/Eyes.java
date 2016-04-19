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
    private Animator blinkAnimation, openEyes;
    private SpriteBatch batcher;
    private Sprite eye;
    private String state;
    private Random random;
    private int blinkChance;
    Drop drop;

    public Eyes(float x, float y, SpriteBatch batcher, Drop drop)
    {
        this.x = x;
        this.y = y;
        this.drop = drop;
        this.random = new Random();

        Texture centerEye = new Texture("Centered.png");

        eye = new Sprite(centerEye);

        blinkAnimation = new Animator("blinkSheet.png", 5, 2, .04f, batcher, this);
        openEyes = new Animator("EyeOpenSheet.png", 5, 1, .12f, batcher, this);

        blinkAnimation.create();
        openEyes.create();

        this.batcher = batcher;
        state = "Open";
    }

    public void draw(float x, float y, float xShift)
    {
        this.x = x + xShift;
        this.y = y;

        //Draw the base eye.
        batcher.begin();
        batcher.draw(eye, x + xShift, y, 103, 117);
        batcher.end();

        //Draw extras for the eyes.
        if (!Gdx.input.isKeyPressed(Input.Keys.LEFT)
                || !Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            if (state.equals("Closed"))
            {
                if (!(openEyes.getFinished()))
                {
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