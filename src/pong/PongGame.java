package pong;

/**
 * Main loop of the game.
 */

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
	private BatLeft batLeft;
	private BatRight batRight;
	private Background background;
	private int backgroundState = -4;
	
	public PongGame() {
		
		//Game setup
		setUpDisplay();
		setUpOpengl();
		setUpEntities();
		setUpTimer();
		
		//Main loop starts here
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
	
	/**
	 * Checks for user input.
	 */
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
	
	/**
	 * Calculates the change in time since getDelta()
	 * was last called.
	 * 
	 * @return	change in time since getDelta()
	 * was last called
	 */
	private int getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}

	private void setUpTimer() {
		lastFrame = getTime();
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
		glEnable(GL_BLEND);
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private void setUpEntities() {
		
		background = new Background(0);
		background.declareVertex(-WIDTH * 4, -80);
		background.declareVertex(WIDTH * 4, -80);
		background.declareVertex(WIDTH * 4, HEIGHT + 80);
		background.declareVertex(-WIDTH * 4, HEIGHT + 80);
		entities.add(background);
		movableEntities.add(background);
		
		batLeft = new BatLeft(-2, Keyboard.KEY_W, Keyboard.KEY_S, 0.3);
		batLeft.declareVertex(10, HEIGHT / 2);
		batLeft.declareVertex(55, HEIGHT / 2);
		batLeft.declareVertex(55, HEIGHT / 2 + 90);
		batLeft.declareVertex(10, HEIGHT / 2 + 90);
		entities.add(batLeft);
		movableEntities.add(batLeft);
		
		
		batRight = new BatRight(-2, Keyboard.KEY_UP, Keyboard.KEY_DOWN, 0.3);
		batRight.declareVertex(WIDTH - 55, HEIGHT / 2);
		batRight.declareVertex(WIDTH - 10, HEIGHT / 2);
		batRight.declareVertex(WIDTH - 10, HEIGHT / 2 + 90);
		batRight.declareVertex(WIDTH - 55, HEIGHT / 2 + 90);
		entities.add(batRight);
		movableEntities.add(batRight);
		
		ball = new Ball(-2);
		ball.declareVertex(WIDTH / 2 - 7, HEIGHT / 2 - 7);
		ball.declareVertex(WIDTH / 2 + 7, HEIGHT / 2 - 7);
		ball.declareVertex(WIDTH / 2 + 7, HEIGHT / 2 + 7);
		ball.declareVertex(WIDTH / 2 - 7, HEIGHT / 2 + 7);
		ball.setDX(-0.3);
		entities.add(ball);
		movableEntities.add(ball);
	}
	
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		for (Entity entity : entities) {
			entity.draw();
		}
	}
	
	/**
	 * This method contains the logic of the game. 
	 * 
	 * @param delta
	 */
	private void logic(int delta) {
		for (MoveableEntity entity : movableEntities) {
			entity.update(delta);
		}
	
		//Bouncing mechanics for ball intersecting bat or border
		if (ball.intersects(batLeft) && ball.getDX() < 0) {
			ball.setDX(-ball.getDX());
			ball.setDY(-(batLeft.getY() + batLeft.getHeight() / 2 - (ball.getY() + ball.getHeight() / 2)) / 200);
			if (ball.getY() + ball.getHeight() / 2 > batLeft.getY() + batLeft.getHeight() / 2){
				batLeft.setOscillation(Math.abs(-(batLeft.getY() + batLeft.getHeight() / 2 - (ball.getY() + ball.getHeight() / 2)) / 4000), 0.002, true);
				batLeft.botFire();
			}
			else {
				batLeft.setOscillation(Math.abs(-(batLeft.getY() + batLeft.getHeight() / 2 - (ball.getY() + ball.getHeight() / 2)) / 4000), 0.002, false);
				batLeft.topFire();
			}
		}
		
		if (ball.intersects(batRight) && ball.getDX() > 0) {
			ball.setDX(-ball.getDX());
			ball.setDY(-(batRight.getY() + batRight.getHeight() / 2 - ball.getY() + ball.getHeight() / 2) / 200);
			if (ball.getY() + ball.getHeight() / 2 > batRight.getY() + batRight.getHeight() / 2){
				batRight.setOscillation(Math.abs(-(batRight.getY() + batRight.getHeight() / 2 - (ball.getY() + ball.getHeight() / 2)) / 4000), 0.002, true);
				batRight.botFire();
			}
			else {
				batRight.setOscillation(Math.abs(-(batRight.getY() + batRight.getHeight() / 2 - (ball.getY() + ball.getHeight() / 2)) / 4000), 0.002, false);
				batRight.topFire();
			}
		}
		
		if ((ball.getY() < 0 && ball.getDY() < 0) || (ball.getY() > HEIGHT && ball.getDY() > 0)) {
			ball.setDY(-ball.getDY());
		}
		
		//Check for bat oscillation
		if (batLeft.oscillate) {
			batLeft.oscillate();
		}
		
		if (batRight.oscillate) {
			batRight.oscillate();
		}
		
		//Switching platform mechanic
		if (ball.getX() <= 0) {
			ball.setDX(0.4);
			background.setDX(0.5);
			batRight.allFire();
			batLeft.allFireInverted();
		}
		if (background.getX() > WIDTH * backgroundState) {
			background.setDX(0);
			background.setX(WIDTH * backgroundState);
			ball.setDX(-0.3);
			backgroundState += 1;
			batRight.noFire();
			batLeft.noFire();
		}
		
		if (ball.getX() >= WIDTH) {
			ball.setDX(-0.4);
			background.setDX(-0.5);
			batLeft.allFire();
			batRight.allFireInverted();
		}
		if (background.getX() < WIDTH * backgroundState - WIDTH) {
			background.setDX(0);
			background.setX(WIDTH * backgroundState - WIDTH);
			ball.setDX(0.3);
			backgroundState -= 1;
			batLeft.noFire();
			batRight.noFire();
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
