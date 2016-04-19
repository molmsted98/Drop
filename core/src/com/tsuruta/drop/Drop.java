package com.tsuruta.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drop {

    private float x, y;
    private PuddleAnimator D2P, P2D, D2PR, P2DR;
    private Texture drop, puddle;
    private SpriteBatch batch;
    private String dropState;
    private Eyes leftEye, rightEye;
    private Mouth mouth;
    private float mouthXShift, mouthYShift, eyeXShift, eyeYShift;
    private float dropXLength, dropYLength, puddleXLength, puddleYLength;

    public Drop(float x, float y, SpriteBatch batch)
    {
        this.x = x;
        this.y = y;
        this.batch = batch;

        mouthXShift = 15;
        mouthYShift = 25;
        eyeXShift = 45;
        eyeYShift = 145;

        dropXLength = 290.4f;
        dropYLength = 449.6f;
        puddleXLength = 290.4f;
        puddleYLength = 120f;

        leftEye = new Eyes(100, 100, batch, this);
        rightEye = new Eyes(250, 100, batch, this);
        mouth = new Mouth(batch, this);

        //Left moving puddle animations.
        D2P = new PuddleAnimator("D2PSheet.png", 6, 2, .03f, batch, this);
        P2D = new PuddleAnimator("P2DSheet.png", 6, 2, .03f, batch, this);
        D2P.create();
        P2D.create();

        //Right moving puddle animations.
        D2PR = new PuddleAnimator("D2PSheet.png", 6, 2, .03f, batch, this);
        P2DR = new PuddleAnimator("P2DSheet.png", 6, 2, .03f, batch, this);
        D2PR.create();
        P2DR.create();

        drop = new Texture("Blank.png");
        puddle = new Texture("Puddle.png");
        dropState = "Drop";
    }

    public void draw()
    {
        //Moving left. Animate to a puddle, then wiggle left.
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            if (!(D2P.getFinished()))
            {
                D2P.render();
                dropState = "Moving";
            }
            else
            {
                dropState = "Puddle";
                mouth.setState("Closed");
                leftEye.setState("Closed");
                rightEye.setState("Closed");

                //Replace below with wiggle left.
                batch.begin();
                batch.draw(puddle, x, y, puddleXLength, puddleYLength);
                batch.end();
            }
        }
        //Moving left. Animate to a  puddle, then wiggle right.
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            if (!(D2PR.getFinished()))
            {
                D2PR.render();
                dropState = "Moving";
            }
            else
            {
                dropState = "Puddle";
                mouth.setState("Closed");
                leftEye.setState("Closed");
                rightEye.setState("Closed");

                //Replace below with wiggle right.
                batch.begin();
                batch.draw(puddle, x, y, puddleXLength, puddleYLength);
                batch.end();
            }
        }
        else
        {
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
                batch.begin();
                batch.draw(drop, x, y, dropXLength, dropYLength);
                batch.end();
            }
        }

        //Draw facial features if it is stationary.
        if (dropState.equals("Drop"))
        {
            leftEye.draw(x + eyeXShift, y + eyeYShift, 0f);
            rightEye.draw(x + eyeXShift, y + eyeYShift, 110f);
            mouth.draw(x + mouthXShift, y + mouthYShift);
        }
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
}
