package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Game extends ApplicationAdapter {

	private Texture player;

	private Texture pinchos;

	private TextureRegion regionPinchos;

	private SpriteBatch batch;

	private int width, height;

	private int widthPlayer, heightPlayer;

	@Override
	public void create() {
		player = new Texture("player.png");
		pinchos = new Texture("spike.png");

		regionPinchos = new TextureRegion(pinchos, 0, 64, 128, 64);
		batch = new SpriteBatch();

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		widthPlayer = player.getWidth();
		heightPlayer = player.getHeight();
	}

	@Override
	public void dispose() {
		player.dispose();
		batch.dispose();
		pinchos.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(player, 50, 0);
		batch.draw(pinchos, 250, 0);
		batch.end();
	}
}
