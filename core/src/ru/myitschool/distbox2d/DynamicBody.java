package ru.myitschool.distbox2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class DynamicBody {
    Body body;
    float r;

    DynamicBody(World world, float x, float y, float r) {
        this.r = r;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(r);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 2.4f;
        fixtureDef.restitution = 0.5f;

        Fixture fixture = body.createFixture(fixtureDef);
        circle.dispose();

        body.setAngularDamping(0.2f);
    }

    float getX() {
        return body.getPosition().x - r;
    }
    float getY() {
        return body.getPosition().y - r;
    }
    float getAngle() {
        return (float) Math.toDegrees(body.getAngle());
    }
    Vector2 getPos() {
        return body.getPosition();
    }
    boolean hit(float tx, float ty) {
        if(Math.pow(tx-body.getPosition().x, 2) + Math.pow(ty-body.getPosition().y, 2) < r*r){
            body.applyLinearImpulse(new Vector2(0, 0.5f), body.getPosition(), true);
        }
        return Math.pow(tx-body.getPosition().x, 2) + Math.pow(ty-body.getPosition().y, 2) < r*r;
    }
}
