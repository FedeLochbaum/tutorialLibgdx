package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FloorEntity extends Actor {

    private Texture floor, overfloor;

    private World world;

    private Body body, leftBody;

    private Fixture fixture, leftFixture;

    public FloorEntity(World worldD, Texture floorTexture, Texture overFloorTexture, Vector2 pos, float width) {
        world = worldD;
        floor = floorTexture;
        overfloor = overFloorTexture;
        createFloor(pos, width);
    }

    private void createFloor(Vector2 pos, float width) {

        BodyDef def = new BodyDef();
        def.position.set(pos.x + width / 2, pos.y - 0.5f);
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(width / 2, 0.5f);
        fixture = body.createFixture(box, 1);
        fixture.setUserData("floor");
        box.dispose();

        BodyDef leftDef = new BodyDef();
        leftDef.position.set(pos.x, pos.y - 0.55f);
        leftBody = world.createBody(leftDef);

        PolygonShape leftBox = new PolygonShape();
        leftBox.setAsBox(0.02f, 0.45f);
        leftFixture = leftBody.createFixture(leftBox, 1);
        leftFixture.setUserData("spike");
        leftBox.dispose();

        setSize(width * Constants.PIXELS_IN_METER, Constants.PIXELS_IN_METER);
        setPosition(pos.x * Constants.PIXELS_IN_METER, (pos.y - 1) * Constants.PIXELS_IN_METER);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(floor, getX(), getY(), getWidth(), getHeight());
        batch.draw(overfloor, getX(), getY() + 0.9f * getHeight(), getWidth(), 0.1f * getHeight());
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
