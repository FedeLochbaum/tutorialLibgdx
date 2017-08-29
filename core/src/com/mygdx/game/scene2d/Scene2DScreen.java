package com.mygdx.game.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Game;
import com.mygdx.game.MainScreen;

public class Scene2DScreen extends MainScreen {

    private Stage stage;

    private Player player;

    private Enemy enemy;

    private Texture texturePlayer;

    private Texture textureEnemy;

    public Scene2DScreen(Game game) {
        super(game);
        texturePlayer = new Texture("player.png");
        textureEnemy = new Texture("spike.png");
    }

    @Override
    public void show() {
        stage = new Stage();

        stage.setDebugAll(true);

        player = new Player(texturePlayer);
        enemy = new Enemy(textureEnemy, 250);

        stage.addActor(player);
        stage.addActor(enemy);

        player.setPosition(20,100);
        enemy.setPosition(500, 100);
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();

        checkForCollision();

        stage.draw();
    }

    private void checkForCollision() {
        if(player.isAlive() && player.getX() + player.getWidth() > enemy.getX()){
            player.setAlive(false);
        }
    }

    @Override
    public void dispose() {
        texturePlayer.dispose();
    }
}
