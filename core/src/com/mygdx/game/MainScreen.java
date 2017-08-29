package com.mygdx.game;

import com.badlogic.gdx.Screen;

public abstract class MainScreen implements Screen {

    private Game mainGame;

    public MainScreen(Game game){
        mainGame = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Game getMainGame() {
        return mainGame;
    }

    public void setMainGame(Game mainGame) {
        this.mainGame = mainGame;
    }
}
