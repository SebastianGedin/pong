package pong;

/**
 * An abstract class for a bat in the Pong game. Bats can
 * move up and down by binding the controls to certain
 * keys. The are rectangularly shaped entities. In the
 * game the bats are spaceships with fire engine thus
 * their texture can change based on their movement.
 */

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3d;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

import entities.AbstractMoveableEntity;

public abstract class AbstractBat extends AbstractMoveableEntity {
	
	//Different texture depending on movement.
	protected Texture texture; //Standard texture
	protected Texture noFire;
	protected Texture topFire;
	protected Texture botFire;
	protected Texture allFire;
	protected Texture inverted;
	
	protected int upKey;
	protected int downKey;
	protected double speed;
	
	//Keeping track of current oscillation parameters.
	public boolean oscillate = false;
	protected double amplitude;
	protected double dampening;
	protected boolean clockwise;
	protected double x = 0;
	
	/**
	 * Bats are initialized with four vertices a given
	 * z position, controls and speed. Note that vertices must
	 * be declared serperately after contruction.
	 * 
	 * @param z
	 * @param upKey	key used to move up
	 * @param downKey	key used to move down
	 * @param speed
	 */
	public AbstractBat(double z, int upKey, int downKey, double speed) {
		super(4, z);
		this.upKey = upKey;
		this.downKey = downKey;
		this.speed = speed;
	}
	
	/**
	 * Draws the bat with its standard texture. Four vertices
	 * must have been declared.
	 */
	public void draw() {
		if (vertices.get(3)[0] == -1) {
			throw new IllegalStateException("Must declare precisely 4 vertices");
		}
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
	
	//The following methods are used to switch textures.
	
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
	
	/**
	 * Getter for the upKey.
	 * 
	 * @return	upKey
	 */
	public int getUpKey() {
		return upKey;
	}
	
	/**
	 * Setter for the upKey.
	 * 
	 * @param upKey	new upKey
	 */
	public void setUpKey(int upKey) {
		this.upKey = upKey;
	}
	
	/**
	 * Getter for the downKey.
	 * 
	 * @return	downKey
	 */
	public int getDownKey() {
		return downKey;
	}
	
	/**
	 * Setter for the downKey.
	 * 
	 * @param upKey	new downKey
	 */
	public void setDownKey(int downKey) {
		this.downKey = downKey;
	}
	
	/**
	 * Defines the bats movement based on keyboard input.
	 * Moves up or down when upKey or upKey are pressed 
	 * respectively. Doesn't move if keys are released.
	 */
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
	
	/**
	 * Sets the oscillation parameters and activates the
	 * oscillation.
	 * 
	 * @param amplitude
	 * @param dampening
	 * @param clockwise
	 */
	public void setOscillation(double amplitude, double dampening, boolean clockwise) {
		oscillate = true;
		this.amplitude = amplitude;
		this.dampening = dampening;
		this.clockwise = clockwise;
	}
	
	/**
	 * Defines the oscillation.
	 */
	public abstract void oscillate();
}
