package pong;

import java.util.List;
import java.util.ArrayList;
import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import entities.*;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PongGame {
	
	private long lastFrame;
	public static final int HEIGHT = 480;
	public static final int WIDTH = 640;
	private boolean isRunning = true;
	private List<Entity> entities = new ArrayList<>();
	private List<MoveableEntity> movableEntities = new ArrayList<>();
	private Ball ball;
	private Bat batLeft;
	private Bat batRight;
	private Background background;
	private int backgroundState = -2;
	
	public PongGame() {
		setUpDisplay();
		setUpOpengl();
		setUpEntities();
		setUpTimer();
		while (isRunning) {
			render();
			Display.update();
			Display.sync(60);
			logic(getDelta());
			input();
			if (Display.isCloseRequested()) {
				isRunning = false;
			}
		}
		Display.destroy();
		System.exit(0);
	}
	
	private void input() {
		
		batLeft.controls();
		batRight.controls();
		
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			isRunning = false;
		}
	}
	
	private long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	private int getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}
	
	private void setUpDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Pong");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	private void setUpOpengl() {
		// Initialization code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 0, 800);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
	}
	
	private void setUpEntities() {
		
		background = new Background(4, 0);
		background.declareVertex(-WIDTH * 2, 0);
		background.declareVertex(WIDTH * 3, 0);
		background.declareVertex(WIDTH * 3, HEIGHT);
		background.declareVertex(-WIDTH * 2, HEIGHT);
		entities.add(background);
		movableEntities.add(background);
		// background = new Background(-WIDTH * 2, 0, 0, WIDTH * 5, HEIGHT);
		
		// batLeft = new Bat(30, HEIGHT / 2, -2, 15, 80, Keyboard.KEY_W, Keyboard.KEY_S, 0.3);
		batLeft = new Bat(4, -2, Keyboard.KEY_W, Keyboard.KEY_S, 0.3);
		batLeft.declareVertex(30, HEIGHT / 2);
		batLeft.declareVertex(45, HEIGHT / 2);
		batLeft.declareVertex(45, HEIGHT / 2 + 80);
		batLeft.declareVertex(30, HEIGHT / 2 + 80);
		entities.add(batLeft);
		movableEntities.add(batLeft);
		
		
		// batRight =new Bat(WIDTH - 40, HEIGHT / 2 - 80 /2, -2, 15, 80, Keyboard.KEY_UP, Keyboard.KEY_DOWN, 0.3);
		batRight = new Bat(4, -2, Keyboard.KEY_UP, Keyboard.KEY_DOWN, 0.3);
		batRight.declareVertex(WIDTH - 45, HEIGHT / 2);
		batRight.declareVertex(WIDTH - 30, HEIGHT / 2);
		batRight.declareVertex(WIDTH - 30, HEIGHT / 2 + 80);
		batRight.declareVertex(WIDTH - 45, HEIGHT / 2 + 80);
		entities.add(batRight);
		movableEntities.add(batRight);
		
		// ball = new Ball(WIDTH / 2 - 10 / 2, HEIGHT / 2 - 10 / 2, -2, 10, 10);
		ball = new Ball(4, -2);
		ball.declareVertex(WIDTH / 2 - 5, HEIGHT / 2 - 5);
		ball.declareVertex(WIDTH / 2 + 5, HEIGHT / 2 - 5);
		ball.declareVertex(WIDTH / 2 + 5, HEIGHT / 2 + 5);
		ball.declareVertex(WIDTH / 2 - 5, HEIGHT / 2 + 5);
		ball.setDX(-0.3);
		entities.add(ball);
		movableEntities.add(ball);
	}
	
	private void setUpTimer() {
		lastFrame = getTime();
	}
	
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		for (Entity entity : entities) {
			entity.draw();
		}
	}
	
	private void logic(int delta) {
		for (MoveableEntity entity : movableEntities) {
			entity.update(delta);
		}
		
		if (batLeft.oscillate) {
			batLeft.oscillate();
		}
	
		//Bouncing mechanics.
		if (ball.intersects(batLeft) && ball.getDX() < 0) {
			ball.setDX(-ball.getDX());
			ball.setDY(-(batLeft.getY() + batLeft.getHeight() / 2 - ball.getY() + ball.getHeight() / 2) / 200);
			batLeft.setOscillation(0.01, 0.001);
		}
		
		if (ball.intersects(batRight) && ball.getDX() > 0) {
			ball.setDX(-ball.getDX());
			ball.setDY(-(batRight.getY() + batRight.getHeight() / 2 - ball.getY() + ball.getHeight() / 2) / 200);
		}
		
		if ((ball.getY() < 0 && ball.getDY() < 0) || (ball.getY() > HEIGHT && ball.getDY() > 0)) {
			ball.setDY(-ball.getDY());
		}
		
		//Switching platform mechanic.
		if (ball.getX() <= 0) {
			ball.setDX(0.4);
			background.setDX(0.5);
		}
		if (background.getX() > WIDTH * backgroundState) {
			background.setDX(0);
			background.setX(WIDTH * backgroundState);
			ball.setDX(-0.3);
			backgroundState += 1;
		}
		
		if (ball.getX() >= WIDTH) {
			ball.setDX(-0.4);
			background.setDX(-0.5);
		}
		if (background.getX() < WIDTH * backgroundState - WIDTH) {
			background.setDX(0);
			background.setX(WIDTH * backgroundState - WIDTH);
			ball.setDX(0.3);
			backgroundState -= 1;
		}
	}
	
	public static Texture loadTexture(String key) {
		try {
			return TextureLoader.getTexture("PNG", new FileInputStream(new File("res/" + key + ".png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
		
	
	public static void main(String[] args) {
		new PongGame();
	}

}