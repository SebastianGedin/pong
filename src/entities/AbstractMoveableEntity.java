package entities;

public abstract class AbstractMoveableEntity extends AbstractEntity implements MoveableEntity {

	protected double dx, dy, dRotate;
	
	public AbstractMoveableEntity(int n, double z) {
		super(n, z);
	}
	
	@Override
	public void update(int delta) {
		for (double[] vector : vertices) {
			vector[0] += dx * delta;
			vector[1] += dy * delta;
		}
		rotate(dRotate * delta);
	}
	
	@Override
	public double getDRotate() {
		return dRotate;
	}

	@Override
	public void setDRotate(double dRotate) {
		this.dRotate = dRotate;
	}

	@Override
	public double getDX() {
		return dx;
	}
	
	@Override
	public double getDY() {
		return dy;
	}
	
	@Override
	public void setDX(double dx){
		this.dx = dx;
	}
	
	@Override
	public void setDY(double dy){
		this.dy = dy;
	}
	
	@Override
	public void rotate(double a) {
		//Get center of the Entity
		double x = getX() + getWidth()/2;
		double y = getY() + getHeight()/2;
		for (double[] vector : vertices) {
			
			//Move to origin
			vector[0] -= x;
			vector[1] -= y;
		
			//Rotate about the origin
			double temp = vector[0];
			vector[0] = vector[0] * Math.cos(a) - vector[1] * Math.sin(a);
			vector[1] = temp * Math.sin(a) + vector[1] * Math.cos(a);

			//Move back to initial position
			vector[0] += x;
			vector[1] += y;
		}
	}
}