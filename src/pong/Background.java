package pong;

/**
 * The background in the Pong game is a wide rectangle
 * so that it can be moved to give the illusion of
 * the spaceships moving to the right or to the left.
 */

import static org.lwjgl.opengl.GL11.*;
import java.util.Random;

import org.newdawn.slick.opengl.Texture;

import entities.AbstractMoveableEntity;

public class Background extends AbstractMoveableEntity {

	public Background(double z) {
		super(4, z);
	}

	Texture background = PongGame.loadTexture("space");

	/**
	 * Draws the background. Four vertices
	 * must have been declared.
	 */
	@Override
	public void draw() {
		int i = 0;
		background.bind();
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
			glTexCoord2f(1, 0);
		glEnd();
	}
}
