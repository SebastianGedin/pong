package pong;

import entities.AbstractMoveableEntity;
import java.lang.Math;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

public class Bat extends AbstractMoveableEntity {
	
	protected int upKey;
	protected int downKey;
	protected double speed;
	
	public boolean oscillate = false;
	private double amplitude;
	private double dampening;
	private int x = 0;
	
	public Bat(int n, double z, int upKey, int downKey, double speed) {
		super(n, z);
		this.upKey = upKey;
		this.downKey = downKey;
		this.speed = speed;
	}

	@Override
	public void draw() {
		int i = 0;
		glBegin(GL_QUADS);
			glVertex3d(vertices.get(i)[0], vertices.get(i)[1], z);
			i++;
			glVertex3d(vertices.get(i)[0], vertices.get(i)[1], z);
			i++;
			glVertex3d(vertices.get(i)[0], vertices.get(i)[1], z);
			i++;
			glVertex3d(vertices.get(i)[0], vertices.get(i)[1], z);
		glEnd();
	}
		
//			glBegin(GL_TRIANGLES);
//				glVertex2d(x + width*Math.cos(Math.PI/4), y - height/2);
//				glVertex2d(x + width*Math.cos(i), (y - height/2) + width*Math.sin(i));
//				glVertex2d(x + width*Math.cos(i + Math.PI/12), (y - height/2) + width*Math.sin(i + Math.PI/12));
//			glEnd();
//		}
		
//		for (double i=0 ; i<=((3*Math.PI)/4) ; i += Math.PI/100) {
//			glBegin(GL_TRIANGLES);
//		        glVertex2d(x, y);
//		        glVertex2d(x + Math.sin((3*Math.PI) / 4 - i) * (height / 2), -(Math.cos((3*Math.PI) / 4 - i) * (height / 2)) + y);
//		        glVertex2d(x + Math.sin(i) * (height / 2), Math.cos(i) * (height / 2) + y);
//	        glEnd();
//		}
	
	public int getUpKey() {
		return upKey;
	}
	
	public void setUpKey(int upKey) {
		this.upKey = upKey;
	}
	
	public int getDownKey() {
		return downKey;
	}
	
	public void setDownKey(int downKey) {
		this.downKey = downKey;
	}
	
	public void controls() {
		if (Keyboard.isKeyDown(upKey) && getY() > 0) {
			setDY(-speed);
		}
		else if (Keyboard.isKeyDown(downKey) && getY() < PongGame.HEIGHT - getHeight()) {
			setDY(speed);
		}
		else {
			setDY(0);
		}
	}
	
	public void setOscillation(double amplitude, double dampening) {
		oscillate = true;
		this.amplitude = amplitude;
		this.dampening = dampening;
	}
	
	public void oscillate() {
		if (dampening * x < amplitude) {
			setDRotate((amplitude - dampening*x)*Math.cos(x));
			x++;
		}
		else {
			setDRotate(0);
			oscillate = false;
			x = 0;
		}
	}
}