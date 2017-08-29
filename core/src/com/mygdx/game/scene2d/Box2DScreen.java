package com.mygdx.game.scene2d;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Game;
import com.mygdx.game.MainScreen;

public class Box2DScreen extends MainScreen {

    private World world;

    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthographicCamera camera;

    private Body bodyPlayer, bodyFloor, bodyEnemy;

    private Fixture fixturePlayer, fixtureFloor, fixtureEnemy;

    private boolean shouldJump, jumping, aLive;

    public Box2DScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.81f), true);
        box2DDebugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(16f, 9f);
        camera.translate(2,1);

        aLive = true;

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();


                if(fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("floor") || fixtureA.getUserData().equals("floor") && fixtureB.getUserData().equals("player")){
                    if (Gdx.input.justTouched()) shouldJump = true;
                    jumping = false;
                }

                if(fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("enemy") || fixtureA.getUserData().equals("enemy") && fixtureB.getUserData().equals("player")){
                    if (Gdx.input.justTouched()) shouldJump = true;
                    aLive = false;
                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if(fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("floor") || fixtureA.getUserData().equals("floor") && fixtureB.getUserData().equals("player")){
                    jumping = false;
                }

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        createFloor();
        createPlayer();
        createEnemy();

    }

    private void createEnemy() {
        bodyEnemy = world.createBody(createEnemyDef(5));

        fixtureEnemy = createEnemyFixture(bodyEnemy);
        fixtureEnemy.setUserData("enemy");
    }

    private void createFloor() {
        bodyFloor = world.createBody(createFloorDef());
        PolygonShape floorShape = new PolygonShape();
        floorShape.setAsBox(500f, 1f);
        fixtureFloor = bodyFloor.createFixture(floorShape, 1);
        fixtureFloor.setUserData("floor");
        floorShape.dispose();

    }

    private void createPlayer() {
        bodyPlayer = world.createBody(createPlayerDef());
        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(0.5f,0.5f);
        fixturePlayer = bodyPlayer.createFixture(playerShape, 1);
        fixturePlayer.setUserData("player");
        playerShape.dispose();
    }


    private BodyDef createEnemyDef(float x) {
        BodyDef def = new BodyDef();

        def.position.set(x, 0.5f);
        def.type = BodyDef.BodyType.DynamicBody;

        return def;
    }

    private BodyDef createFloorDef() {
        BodyDef def = new BodyDef();

        def.position.set(0, -1);

        return def;
    }

    private BodyDef createPlayerDef() {
        BodyDef def = new BodyDef();

        def.position.set(0, 0);
        def.type = BodyDef.BodyType.DynamicBody;

        return def;
    }

    @Override
    public void dispose() {
        world.dispose();
        box2DDebugRenderer.dispose();
        world.destroyBody(bodyPlayer);
        world.destroyBody(bodyFloor);
        world.destroyBody(bodyEnemy);
        bodyPlayer.destroyFixture(fixturePlayer);
        bodyFloor.destroyFixture(fixtureFloor);
        bodyEnemy.destroyFixture(fixtureEnemy);
    }

    private Fixture createEnemyFixture(Body bodyEnemy){

        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-0.5f, -0.5f);
        vertices[1] = new Vector2(0.5f, -0.5f);
        vertices[2] = new Vector2(0, 0.5f);


        PolygonShape shape = new PolygonShape();

        shape.set(vertices);

        Fixture fix = bodyEnemy.createFixture(shape, 1);
        shape.dispose();
        return fix;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1/60f, 6,2);
        camera.update();
        box2DDebugRenderer.render(world, camera.combined);

        if (shouldJump){
            shouldJump = false;
            jump();
        }

        if (aLive){
            float velocityY = bodyPlayer.getLinearVelocity().y;
            bodyPlayer.setLinearVelocity(8, velocityY);
        }


        if(Gdx.input.justTouched() && !jumping) jumping = true;
    }

    public void jump(){
        Vector2 position = bodyPlayer.getPosition();
        bodyPlayer.applyLinearImpulse(0, 20, position.x, position.y, true);
    }
}
