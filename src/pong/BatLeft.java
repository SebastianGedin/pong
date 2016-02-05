package pong;

import java.lang.Math;

import org.lwjgl.input.Keyboard;

public class BatLeft extends AbstractBat {
	
	public BatLeft(double z, int upKey, int downKey, double speed) {
		super(z, upKey, downKey, speed);
		noFire = PongGame.loadTexture("leftSpaceship");
		botFire = PongGame.loadTexture("leftSpaceshipBot");
		topFire = PongGame.loadTexture("leftSpaceshipTop");
		allFire = PongGame.loadTexture("leftSpaceshipAll");
		inverted = PongGame.loadTexture("rightSpaceshipAll");
		noFire();
	}

	@Override
	public void oscillate() {
		if (clockwise) {
			if (dampening * x < amplitude) {
				setDRotate((amplitude - dampening*x)*Math.cos(x) - dampening*Math.sin(x));
				x += 0.35;
			}
			else {
				setDRotate(-0.0012);
				if (vertices.get(0)[1] >= vertices.get(1)[1]) {
					setDRotate(0);
					oscillate = false;
					x = 0;
					noFire();
				}
			}
		}
		
		else {
			if (dampening * x < amplitude) {
				setDRotate(-((amplitude - dampening*x)*Math.cos(x) - dampening*Math.sin(x)));
				x += 0.35;
			}
			else {
				setDRotate(0.0012);
				if (vertices.get(0)[1] <= vertices.get(1)[1]) {
					setDRotate(0);
					oscillate = false;
					x = 0;
					noFire();
				}
			}
		}
	}
}