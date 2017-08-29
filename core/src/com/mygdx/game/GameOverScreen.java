package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class GameOverScreen extends MainScreen{

    private Stage stage;

    private Skin skin;

    private Image gameOver;

    private TextButton retry;

    private TextButton menu;

    public GameOverScreen(final Game game) {
        super(game);
        Texture texture = game.getManager().get("gameover.png");
        stage = new Stage(new FillViewport(640,360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        gameOver = new Image(texture);
        retry = new TextButton("Retry", skin);
        menu = new TextButton("Menu", skin);

        menu.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.menu();
            }
        });

        retry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.startGame();
            }
        });

        gameOver.setPosition(320 - gameOver.getWidth() / 2, 320 - gameOver.getHeight());

        retry.setSize(200, 100);
        retry.setPosition(60, 50);

        menu.setSize(200,100);
        menu.setPosition(380, 50);

        stage.addActor(retry);
        stage.addActor(gameOver);
        stage.addActor(menu);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
