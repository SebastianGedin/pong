package pong;

import entities.AbstractMoveableEntity;
import static org.lwjgl.opengl.GL11.*;

public class Ball extends AbstractMoveableEntity {

	public Ball(int n, double z) {
		super(n, z);
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

}
