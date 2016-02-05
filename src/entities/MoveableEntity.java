package entities;

public interface MoveableEntity extends Entity {
	
	/**
	 * Updates an entities position based on the time past
	 * and its x, y and rotation speed.
	 * 
	 * @param delta	time passed
	 */
	public void update(int delta);
	
	/**
	 * Getter for rotation speed.
	 * 
	 * @return	rotation speed.
	 */
	public double getDRotate();
	
	/**
	 * Getter for x speed.	
	 * 
	 * @return	x speed
	 */
	public double getDX();
	
	/**
	 * Getter for y speed.	
	 * 
	 * @return	y speed
	 */
	public double getDY();
	
	/**
	 * Sets the rotation speed.
	 * 
	 * @param dRotate	new rotation speed
	 */
	public void setDRotate(double dRotate);
	
	/**
	 * Sets the x speed.
	 * 
	 * @param dx new x speed
	 */
	public void setDX(double dx);
	
	/**
	 * Sets the y speed.
	 * 
	 * @param dx new y speed
	 */
	public void setDY(double dy);
	
	/**
	 * Rotates the entity. Positive is clockwise.
	 * 
	 * @param degrees
	 */
	public void rotate(double degrees);
}
