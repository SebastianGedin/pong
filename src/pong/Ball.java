package pong;

import entities.AbstractMoveableEntity;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

public class Ball extends AbstractMoveableEntity {
	
	private Texture texture = PongGame.loadTexture("ball");

	public Ball(double z) {
		super(4, z);
	}

	@Override
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

}
