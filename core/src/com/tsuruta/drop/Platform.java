package com.tsuruta.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by student on 4/26/16.
 */
public class Platform
{
    private Texture platform;
    private float x, y, width, height;
    SpriteBatch batch;

    public Platform (float x, float y, SpriteBatch batch)
    {
        this.x = x;
        this.y = y;
        this.batch = batch;
        platform = new Texture("Ground.png");
        width = 500;
        height = 50;
    }

    public void draw()
    {
        batch.begin();
        batch.draw(platform, x, y, width, height);
        batch.end();
    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
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
