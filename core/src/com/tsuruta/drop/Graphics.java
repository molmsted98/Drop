package com.tsuruta.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Graphics
{
    private SpriteBatch batch;
    Drop mainCharacter;
    Game instance;
    Texture drop, ground, logo;
    BitmapFont font;
    String text;
    GlyphLayout layout;
    float fontX, fontY;

    public Graphics(Game instance, SpriteBatch batch, Drop mainCharacter)
    {
        this.batch = batch;
        this.mainCharacter = mainCharacter;
        this.instance = instance;
        font = new BitmapFont();
        font.getData().setScale(2f);
        drop = new Texture("Blank.png");
        ground = new Texture("Ground.png");
        logo = new Texture("Logo.png");
        text = "Press Space to Start!";
        layout = new GlyphLayout(font, text);
        fontX = (Gdx.graphics.getWidth() - layout.width)/2;
        fontY = ((Gdx.graphics.getHeight() - layout.height)/2) - logo.getHeight()/2;
    }

    public void draw()
    {
        if (instance.getGameState().equals("Menu"))
        {
            clearOrange();

            batch.begin();
            batch.draw(logo, Gdx.graphics.getWidth()/2-logo.getWidth()/2,
                    Gdx.graphics.getHeight()/2-logo.getHeight()/2);
            font.draw(batch, layout, fontX, fontY);
            batch.end();
        }
        else if (instance.getGameState().equals("Running"))
        {
            clearOrange();

            batch.begin();
            batch.draw(ground, 0, 0);
            batch.end();

            mainCharacter.draw();
        }
    }

    public void clearOrange()
    {
        Gdx.gl.glClearColor(255/255f, 102/255f, 0/255f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}