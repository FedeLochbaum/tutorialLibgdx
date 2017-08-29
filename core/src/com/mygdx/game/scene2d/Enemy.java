package com.mygdx.game.scene2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor{

    private Texture enemy;

    private Vector2 speed;

    public Enemy(Texture texture, int speedX){
        enemy = texture;
        speed = new Vector2();
        speed.x = speedX;

        setSize(texture.getWidth(), texture.getHeight());
    }

    @Override
    public void act(float delta) {
        setX(getX() - speed.x * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(enemy, getX(), getY());
    }
}
