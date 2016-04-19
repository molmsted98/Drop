package com.tsuruta.drop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by student on 3/23/16.
 */
public class PuddleAnimator implements ApplicationListener
{
    Animation animation;
    Texture sheet;
    TextureRegion[] frames;
    SpriteBatch batcher;
    TextureRegion currentFrame;
    String sheetName;
    int columns, rows, count;
    Drop drop;
    float speed;
    private boolean finished;

    float stateTime;

    public PuddleAnimator(String sheetName, int columns, int rows, float speed, SpriteBatch batcher, Drop drop)
    {
        this.sheetName = sheetName;
        this.columns = columns;
        this.rows = rows;
        this.speed = speed;
        this.batcher = batcher;
        this.drop = drop;
    }

    @Override
    public void create()
    {
        sheet = new Texture(Gdx.files.internal(sheetName));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/columns, sheet.getHeight()/rows);
        frames = new TextureRegion[columns * rows];
        int index = 0;
        for (int i = 0; i < rows; i ++)
        {
            for (int j = 0; j < columns; j ++)
            {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(speed, frames);
        stateTime = 0f;
    }

    @Override
    public void render()
    {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animation.getKeyFrame(stateTime, true);
        count ++;
        batcher.begin();

        if (currentFrame != null)
        {
            batcher.draw(currentFrame, drop.getX(), drop.getY(), 290.4f, 449.6f);
        }
        batcher.end();

        if (currentFrame.equals(frames[frames.length-1]))
        {
            finished = true;
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public void reset()
    {
        stateTime = 0f;
        finished = false;
    }

    public boolean getFinished()
    {
        return finished;
    }
}
