package pong;

import java.lang.Math;

public class BatLeft extends AbstractBat {
	
	public BatLeft(int n, double z, int upKey, int downKey, double speed) {
		super(n, z, upKey, downKey, speed);
	}

	public void oscillate() {
		if (clockwise) {
			if (dampening * x < amplitude) {
				setDRotate((amplitude - dampening*x)*Math.cos(x) - dampening*Math.sin(x));
				x += 0.5;
			}
			else {
				setDRotate(-0.0012);
				if (vertices.get(0)[1] >= vertices.get(1)[1]) {
					setDRotate(0);
					oscillate = false;
					x = 0;
				}
			}
		}
		
		else {
			if (dampening * x < amplitude) {
				setDRotate(-((amplitude - dampening*x)*Math.cos(x) - dampening*Math.sin(x)));
				x += 0.5;
			}
			else {
				setDRotate(0.0012);
				if (vertices.get(0)[1] <= vertices.get(1)[1]) {
					setDRotate(0);
					oscillate = false;
					x = 0;
				}
			}
		}
	}
}