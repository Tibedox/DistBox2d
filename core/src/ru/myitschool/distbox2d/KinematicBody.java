package ru.myitschool.distbox2d;

import static ru.myitschool.distbox2d.MyGame.WIDTH;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class KinematicBody {
    Body body;
    float vx = 2;
    float width, height;

    KinematicBody(World world, float x, float y, float width, float height) {
        this.width = width;
        this.height = height;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(width/2, height/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        /*fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.8f;*/

        body.createFixture(fixtureDef);
        box.dispose();
        body.setLinearVelocity(vx, 0);
        body.setAngularVelocity(-1);
    }

    void move() {
        if(body.getPosition().x > WIDTH | body.getPosition().x < 0) {
            vx = -vx;
            body.setLinearVelocity(vx, 0);
        }
    }

    void touchMove(float tx, float ty) {
        body.setTransform(tx, ty, 0);
    }

    float getX() {
        return body.getPosition().x - width/2;
    }
    float getY() {
        return body.getPosition().y - height/2;
    }
    float getAngle() {
        return (float) Math.toDegrees(body.getAngle());
    }
}
