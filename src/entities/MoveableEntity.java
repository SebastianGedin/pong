package entities;

public interface MoveableEntity extends Entity {
	public void update(int delta);
	public double getDRotate();
	public double getDX();
	public double getDY();
	public void setDRotate(double dRotate);
	public void setDX(double dx);
	public void setDY(double dy);
	public void rotate(double degrees);
}
