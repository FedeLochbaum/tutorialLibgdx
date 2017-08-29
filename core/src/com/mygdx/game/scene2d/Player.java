package com.mygdx.game.scene2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {

    private Texture playerTexture;

    private boolean alive;

    public Player(Texture texture){
        playerTexture = texture;

        alive = true;

        setSize(texture.getWidth(), texture.getHeight());
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(playerTexture, getX(), getY());
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
