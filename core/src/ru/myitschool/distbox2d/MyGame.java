package ru.myitschool.distbox2d;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class MyGame extends Game {
	public static final float WIDTH = 16, HEIGHT = 9;
	SpriteBatch batch;
	OrthographicCamera camera;
	World world;
	Box2DDebugRenderer debugRenderer;
	Texture img;
	StaticBody floor;
	StaticBody wall0, wall1;
	ArrayList<DynamicBody> ball = new ArrayList<>();
	
	@Override
	public void create () {
		Box2D.init();
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		img = new Texture("badlogic.jpg");
		floor = new StaticBody(world, WIDTH/2, 1, WIDTH, 0.5f);
		wall0 = new StaticBody(world, 0.5f, HEIGHT/2, 0.5f, HEIGHT);
		wall1 = new StaticBody(world, WIDTH-0.5f, HEIGHT/2, 0.5f, HEIGHT);
		for (int i = 0; i < 50; i++) {
			ball.add(new DynamicBody(world, WIDTH/2+MathUtils.random(-0.1f, 0.1f), HEIGHT+i*2, 0.5f));
		}
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.3f, 0, 0, 1);
		debugRenderer.render(world, camera.combined);
		/*camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, 0, 0, 3, 3);
		batch.end();*/
		world.step(1/60f, 6, 2);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
