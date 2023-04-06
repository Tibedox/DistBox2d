package ru.myitschool.distbox2d;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class MyGame extends Game {
	public static final float WIDTH = 16, HEIGHT = 9;
	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touch;
	World world;
	Box2DDebugRenderer debugRenderer;
	Texture imgColob, imgBrick;
	StaticBody floor;
	StaticBody wall0, wall1;
	ArrayList<DynamicBody> ball = new ArrayList<>();
	KinematicBody brick;
	
	@Override
	public void create () {
		Box2D.init();
		world = new World(new Vector2(0, -9.8f), true);
		debugRenderer = new Box2DDebugRenderer();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		touch = new Vector3();

		imgColob = new Texture("colob.png");
		imgBrick = new Texture("brick.png");

		floor = new StaticBody(world, WIDTH/2, 1, WIDTH, 0.5f);
		wall0 = new StaticBody(world, 0.5f, HEIGHT/2, 0.5f, HEIGHT);
		wall1 = new StaticBody(world, WIDTH-0.5f, HEIGHT/2, 0.5f, HEIGHT);
		brick = new KinematicBody(world, WIDTH/2, HEIGHT/4, 2, 1);
		for (int i = 0; i < 100; i++) {
			ball.add(new DynamicBody(world, WIDTH/2+MathUtils.random(-0.1f, 0.1f), HEIGHT+i*2, MathUtils.random(0.2f, 0.5f)));
		}
	}

	@Override
	public void render () {
		// обработка касаний
		if(Gdx.input.isTouched()) {
			touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touch);
			//brick.move(touch.x, touch.y);
			for (int i = 0; i < ball.size(); i++) {
				if(ball.get(i).hit(touch.x, touch.y)) {
					ball.get(i).body.applyForceToCenter(0, 20f, true);
				}
			}
		}

		// события
		//brick.move();

		// отрисовка
		ScreenUtils.clear(0.3f, 0, 0, 1);
		debugRenderer.render(world, camera.combined);
		/*camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(imgBrick, brick.getX(), brick.getY(),
				brick.width/2, brick.height/2, brick.width, brick.height, 1, 1,
				brick.getAngle(), 0, 0, 400, 200, false, false);
		for (int i = 0; i < ball.size(); i++) {
			batch.draw(imgColob, ball.get(i).getX(), ball.get(i).getY(),
					ball.get(i).r, ball.get(i).r, ball.get(i).r*2, ball.get(i).r*2, 1, 1,
					ball.get(i).getAngle(), 0, 0, 400, 400, false, false);
		}
		batch.end();*/
		world.step(1/60f, 6, 2);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		imgBrick.dispose();
	}
}
