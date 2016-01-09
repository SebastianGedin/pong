package pong;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.input.Keyboard;

import entities.AbstractMoveableEntity;

public abstract class AbstractBat extends AbstractMoveableEntity {
	
	protected int upKey;
	protected int downKey;
	protected double speed;
	
	public boolean oscillate = false;
	protected double amplitude;
	protected double dampening;
	protected boolean clockwise;
	protected double x = 0;
	
	public AbstractBat(int n, double z, int upKey, int downKey, double speed) {
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
	
	public void setOscillation(double amplitude, double dampening, boolean clockwise) {
		oscillate = true;
		this.amplitude = amplitude;
		this.dampening = dampening;
		this.clockwise = clockwise;
	}
	
	public void oscillate() {
	}
}
