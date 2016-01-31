package pong;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

import entities.AbstractMoveableEntity;

public abstract class AbstractBat extends AbstractMoveableEntity {
	
	protected Texture texture;
	protected Texture noFire;
	protected Texture topFire;
	protected Texture botFire;
	protected Texture allFire;
	protected Texture inverted;
	
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
	
	public void draw() {
		int i = 0;
		texture.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex3d(vertices.get(i)[0], vertices.get(i)[1], z);
			i++;
			glTexCoord2f(1, 0);
			glVertex3d(vertices.get(i)[0], vertices.get(i)[1], z);
			i++;
			glTexCoord2f(1, 1);
			glVertex3d(vertices.get(i)[0], vertices.get(i)[1], z);
			i++;
			glTexCoord2f(0, 1);
			glVertex3d(vertices.get(i)[0], vertices.get(i)[1], z);
		glEnd();
	}
	
	public void topFire() {
		texture = topFire;
	}
	
	public void botFire() {
		texture = botFire;
	}
	
	public void allFire() {
		texture = allFire;
	}
	
	public void noFire() {
		texture = noFire;
	}
	
	public void allFireInverted() {
		texture = inverted;
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
	
	public abstract void oscillate();
}
