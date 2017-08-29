package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.entities.EnemyEntity;
import com.mygdx.game.entities.FloorEntity;
import com.mygdx.game.entities.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.entities.Constants.PIXELS_IN_METER;
import static com.mygdx.game.entities.Constants.PLAYER_SPEED;

public class GameScreen extends MainScreen {

    private Stage stage;

    private World world;

    private PlayerEntity player;

    private EnemyEntity enemy;

    private List<FloorEntity> floors = new ArrayList<FloorEntity>();

    private List<EnemyEntity> enemies = new ArrayList<EnemyEntity>();

    private Sound jumpSound, dieSound;

    private Music music;

    public GameScreen(final Game game) {
        super(game);
        jumpSound = game.getManager().get("audio/jump.ogg");
        dieSound = game.getManager().get("audio/die.ogg");

        music = game.getManager().get("audio/song.ogg");

        stage = new Stage(new FillViewport(640, 360));
        world = new World(new Vector2(0, -10), true);

        world.setContactListener(new ContactListener() {

            private boolean areCollided(Contact contact, Object userA, Object userB){
                return (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB)
                        || contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
            }

            @Override
            public void beginContact(Contact contact) {
                if(areCollided(contact, "player", "floor")){
                    player.setJumping(false);
                    if(Gdx.input.justTouched()){
                        jumpSound.play();
                        player.setMustJump(true);
                    }
                }

                if (areCollided(contact, "player", "spike")){
                    if (player.isAlive()) {
                        player.setAlive(false);
                        dieSound.play();
                        music.stop();

                        stage.addAction(
                                Actions.sequence(
                                        Actions.delay(1.5f),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                game.gameOver();
                                            }
                                        })
                                )
                        );
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

    }

    @Override
    public void show() {
        Texture playerTexture = getMainGame().getManager().get("player.png");
        Texture floorTexture = getMainGame().getManager().get("floor.png");
        Texture overFloorTexture = getMainGame().getManager().get("overfloor.png");
        Texture enemyTexture = getMainGame().getManager().get("spike.png");


        player = new PlayerEntity(world, playerTexture, new Vector2(1.5f,1.5f));

        FloorEntity floor = new FloorEntity(world, floorTexture, overFloorTexture, new Vector2(0, 1), 1000f);
        FloorEntity floor2 = new FloorEntity(world, floorTexture, overFloorTexture, new Vector2(12, 2), 10f);
        FloorEntity floor3 = new FloorEntity(world, floorTexture, overFloorTexture, new Vector2(20, 1), 10f);

        floors.add(floor);
        floors.add(floor2);
        floors.add(floor3);

        EnemyEntity enemy  = new EnemyEntity(world, enemyTexture, new Vector2(6, 1));
        EnemyEntity enemy2 = new EnemyEntity(world, enemyTexture, new Vector2(30, 1));
        EnemyEntity enemy3 = new EnemyEntity(world, enemyTexture, new Vector2(16, 2));
        EnemyEntity enemy4 = new EnemyEntity(world, enemyTexture, new Vector2(26, 1));


        enemies.add(enemy);
        enemies.add(enemy2);
        enemies.add(enemy3);
        enemies.add(enemy4);

        for (FloorEntity floorEntity : floors){
            stage.addActor(floorEntity);
        }

        for (EnemyEntity enemyI : enemies){
            stage.addActor(enemyI);
        }


        stage.addActor(player);

        music.setVolume(0.75f);
        music.play();
    }

    @Override
    public void hide() {
        player.detach();
        player.remove();

        for (EnemyEntity enemy : enemies){
            enemy.detach();
            enemy.remove();
        }
        for (FloorEntity floor : floors){
            floor.detach();
            floor.remove();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(player.getX() > 150 && player.isAlive()) {
            stage.getCamera().translate(PLAYER_SPEED * delta * PIXELS_IN_METER, 0, 0);
        }

        if (Gdx.input.justTouched()){
            jumpSound.play();
            player.jump();
        }

        stage.act();
        world.step(delta, 6, 2);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
