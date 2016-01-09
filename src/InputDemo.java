import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.*;
import org.lwjgl.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;

public class InputDemo {
	
	List<Box> shapes = new ArrayList<Box>(16);
	private boolean somethingIsSelected = false;
	private volatile boolean randomColorCooldown = false;
	
	public InputDemo() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Input Demo");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		shapes.add(new Box(15, 15));
		shapes.add(new Box(100, 150));
		
		// Initialization code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		while (!Display.isCloseRequested()) {
			// Render
			glClear(GL_COLOR_BUFFER_BIT);
			
			while (Keyboard.next()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_C && Keyboard.getEventKeyState()) {
					shapes.add(new Box(15, 15));
				}
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				Display.destroy();
				System.exit(0);
			}
			
			for(Box box : shapes) {
				if (Mouse.isButtonDown(0) && box.inBounds(Mouse.getX(), 480 - Mouse.getY() - 1) && !somethingIsSelected) {
					somethingIsSelected = true;
					box.selected = true;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_N) && box.inBounds(Mouse.getX(), 480 - Mouse.getY() - 1) && !somethingIsSelected) {
					box.randomizeColors();
					randomColorCooldown = true;
					new Thread(new Runnable() {
						
						public void run() {
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
								randomColorCooldown = false;
							}
						}
					}).run();
				}
				if (Mouse.isButtonDown(1)) {
					somethingIsSelected = false;
					box.selected = false;
				}
				if (box.selected) {
					box.update(Mouse.getDX(), -Mouse.getDY());
				}
				box.draw();
			}
			
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
	}
	
	public static class Box {
		public int x, y;
		public boolean selected = false;
		private float colorBlue, colorRed, colorGreen;
		
		public Box (int x, int y) {
			this.x = x;
			this.y = y;
			
			Random randomGenerator = new Random();
			colorRed = randomGenerator.nextFloat();
			colorBlue = randomGenerator.nextFloat();
			colorGreen = randomGenerator.nextFloat();
		}
		
		public boolean inBounds(int mousex, int mousey) {
			if (mousex > x && mousex < x + 50 && mousey > y && mousey < y + 50) {
				return true;
			}
			else {
				return false;
			}
		}
		
		public void update(int dx, int dy) {
			x += dx;
			y += dy;
		}
		
		public void randomizeColors() {
			Random randomGenerator = new Random();
			colorRed = randomGenerator.nextFloat();
			colorBlue = randomGenerator.nextFloat();
			colorGreen = randomGenerator.nextFloat();
		}
		
		public void draw() {
			glColor3f(colorRed, colorGreen, colorBlue);
			
			glBegin(GL_QUADS);
				glVertex2f(x, y);
				glVertex2f(x + 50, y);
				glVertex2f(x + 50, y + 50);
				glVertex2f(x, y + 50);
			glEnd();
		}
	}

	public static void main(String[] args) {
		new InputDemo();

	}

}
