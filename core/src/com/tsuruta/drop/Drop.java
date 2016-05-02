package com.tsuruta.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Drop
{
    private float x, y;
    private float yVel;
    private PuddleAnimator D2P, P2D, D2PR, P2DR;
    private WiggleAnimator wiggleLeft, wiggleRight;
    private Texture drop, compressed;
    private SpriteBatch batch;
    private String dropState;
    private Game g;
    private Eyes leftEye, rightEye;
    private Mouth mouth;
    private float mouthXShift, mouthYShift, eyeXShift, eyeYShift;
    private float dropXLength, dropYLength;
    private CompressAnimator compressAnimator, decompressAnimator;
    private boolean jumpStatus;

    public Drop(float x, float y, SpriteBatch batch, Game g)
    {
        this.x = x;
        this.y = y;
        this.yVel = 0;
        this.batch = batch;
        this.g = g;

        mouthXShift = 15;
        mouthYShift = 25;
        eyeXShift = 45;
        eyeYShift = 145;

        dropXLength = 290.4f;
        dropYLength = 449.6f;

        leftEye = new Eyes(100, 100, batch, this);
        rightEye = new Eyes(250, 100, batch, this);
        mouth = new Mouth(batch, this);

        //Left moving puddle animations.
        D2P = new PuddleAnimator("D2PSheet.png", 6, 2, .027f, batch, this);
        P2D = new PuddleAnimator("P2DSheet.png", 6, 2, .027f, batch, this);
        D2P.create();
        P2D.create();

        //Right moving puddle animations.
        D2PR = new PuddleAnimator("D2PSheet.png", 6, 2, .027f, batch, this);
        P2DR = new PuddleAnimator("P2DSheet.png", 6, 2, .027f, batch, this);
        D2PR.create();
        P2DR.create();

        //Wiggling animations.
        wiggleRight = new WiggleAnimator("WiggleRightSheet.png", 4, 1, .08f, batch, this);
        wiggleLeft = new WiggleAnimator("WiggleLeftNew.png", 5, 1, .08f, batch, this);
        wiggleRight.create();
        wiggleLeft.create();

        //Compressing animations.
        compressAnimator = new CompressAnimator("CompressSheet.png", 4, 1, .1f, batch, this);
        decompressAnimator = new CompressAnimator("DecompressSheet.png", 4, 1, .06f, batch, this);
        compressAnimator.create();
        decompressAnimator.create();
        
        drop = new Texture("Blank.png");
        compressed = new Texture("Compressed.png");
        dropState = "Drop";
    }

    public void draw()
    {
        //Moving left. Animate to a puddle, then wiggle left.
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            if (!(D2P.getFinished()))
            {
                wiggleLeft.reset();
                D2P.render();
                dropState = "Moving";
            }
            //In a puddle and moving left.
            else
            {
                dropState = "Puddle";
                mouth.setState("Closed");
                leftEye.setState("Closed");
                rightEye.setState("Closed");

                wiggleLeft.render();
            }
        }
        //Moving left. Animate to a  puddle, then wiggle right.
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            if (!(D2PR.getFinished()))
            {
                wiggleRight.reset();
                D2PR.render();
                dropState = "Moving";
            }
            //In a puddle and moving right.
            else
            {
                dropState = "Puddle";
                mouth.setState("Closed");
                leftEye.setState("Closed");
                rightEye.setState("Closed");

                wiggleRight.render();
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            decompressAnimator.reset();
            if (!compressAnimator.getFinished())
            {
                compressAnimator.render();
            }
            else
            {
                batch.begin();
                batch.draw(compressed, x, y, dropXLength-10, dropYLength-30);
                batch.end();
            }
        }
        else
        {
            compressAnimator.reset();
            D2P.reset();
            D2PR.reset();
            if (dropState.equals("Puddle") || dropState.equals("Moving"))
            {
                if (!(P2D.getFinished()))
                {
                    P2D.render();
                    dropState = "Moving";
                }
                else
                {
                    dropState = "Drop";
                    P2D.reset();
                }
            }
            else
            {
                if (!decompressAnimator.getFinished())
                {
                    decompressAnimator.render();
                }
                else
                {
                    batch.begin();
                    batch.draw(drop, x, y, dropXLength, dropYLength);
                    batch.end();
                }
            }
        }

        //Draw facial features if it is stationary.
        if (dropState.equals("Drop") || dropState.equals("Jump"))
        {
            leftEye.draw(x + eyeXShift, y + eyeYShift, 0f);
            rightEye.draw(x + eyeXShift, y + eyeYShift, 110f);
            mouth.draw(x + mouthXShift, y + mouthYShift);
        }
    }

    public boolean isContacting()
    {
        Platform temp;
        ArrayList<Platform> platforms = g.getPlatforms();

        //Check all platforms for a collision.
        for (int i = 0; i < g.getPlatforms().size(); i ++)
        {
            temp = platforms.get(i);

            //Within the platform side bounds
            if (x + (getWidth()/2) >= temp.getX() && (x + (getWidth()/2)) <= (temp.getX() + temp.getWidth()))
            {
                //Right above the platform.
                if (y <= temp.getY() + 20 && y >= temp.getY() && yVel <= 0)
                {
                    y = temp.getY() + 7;
                    return true;
                }
            }
        }

        //Check for collision with the ground.
        if (y < 0)
        {
            y = 0;
            return true;
        }

        return false;
    }

    public void moveRight()
    {
        x += 3;
    }

    public void moveLeft()
    {
        x -= 3;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float set)
    {
        this.y = set;
    }

    public float getWidth()
    {
        return drop.getWidth() - 73;
    }

    public boolean getJumpStatus()
    {
        return jumpStatus;
    }

    public void setJumpStatus(boolean set)
    {
        this.jumpStatus = set;
    }

    public void setDropState(String set)
    {
        this.dropState = set;
    }

    public float getYVel()
    {
        return yVel;
    }

    public void setYVel(float set)
    {
        this.yVel = set;
    }
}
