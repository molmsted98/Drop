package com.tsuruta.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Graphics
{
    private SpriteBatch batch;
    Drop mainCharacter;
    Game instance;
    ArrayList<Platform> platforms;
    Texture drop, ground, logo;
    BitmapFont font;
    String text, text2;
    GlyphLayout layout, layout2;
    float fontX, fontY, fontX2, fontY2;

    public Graphics(Game instance, SpriteBatch batch, Drop mainCharacter, ArrayList<Platform> platforms)
    {
        this.batch = batch;
        this.mainCharacter = mainCharacter;
        this.instance = instance;
        this.platforms = platforms;
        font = new BitmapFont();
        font.getData().setScale(2f);
        drop = new Texture("Blank.png");
        ground = new Texture("Ground.png");
        logo = new Texture("Logo.png");
        text = "Press Space to Start! Press 'h' for help.";
        text2 = "Use Left and right to move. Hold space then let go to jump. Press 'h' to return to menu.";
        layout = new GlyphLayout(font, text);
        layout2 = new GlyphLayout(font, text2);
        fontX = (Gdx.graphics.getWidth() - layout.width)/2;
        fontY = ((Gdx.graphics.getHeight() - layout.height)/2) - logo.getHeight()/2;
        fontX2 =(Gdx.graphics.getWidth()-layout2.width)/2;
        fontY2 = (Gdx.graphics.getHeight()-layout2.height)/2;
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
        else if (instance.getGameState().equals("Help"))
        {
            clearOrange();

            batch.begin();
            font.draw(batch, layout2, fontX2, fontY2);
            batch.end();
        }
        else if (instance.getGameState().equals("Running"))
        {
            clearOrange();

            batch.begin();
            batch.draw(ground, 0, 0);
            batch.end();

            for (int i = 0; i < platforms.size(); i ++)
            {
                platforms.get(i).draw();
            }
            mainCharacter.draw();
        }
    }

    public void clearOrange()
    {
        Gdx.gl.glClearColor(255/255f, 102/255f, 0/255f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}