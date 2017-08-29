package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Game extends com.badlogic.gdx.Game {

	private AssetManager manager;

	@Override
	public void create() {
		manager = new AssetManager();
		manager.load("floor.png", Texture.class);
		manager.load("gameover.png", Texture.class);
		manager.load("overfloor.png", Texture.class);
		manager.load("logo.png", Texture.class);
		manager.load("spike.png", Texture.class);
		manager.load("player.png", Texture.class);
		manager.load("audio/die.ogg", Sound.class);
		manager.load("audio/jump.ogg", Sound.class);
		manager.load("audio/song.ogg", Music.class);

		loadScreen();

	}

	public AssetManager getManager() {
		return manager;
	}

	public void setManager(AssetManager manager) {
		this.manager = manager;
	}


	public void startGame(){
		setScreen(new GameScreen(this));
	}

	public void gameOver(){
		setScreen(new GameOverScreen(this));
	}

	public void menu(){
		setScreen(new MenuScreen(this));
	}

	public void loadScreen(){
		setScreen(new LoadingScreen(this));
	}
}
