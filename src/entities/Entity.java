package entities;

import java.awt.geom.Line2D;
import java.util.List;

public interface Entity {

	/**
	 * Draws the Entity.
	 */
	public void draw();
	
	/**
	 * Declares the first not yet declared vertex in vertices.
	 * 
	 * @param x x-coordinate
	 * @param y	y-coordinate
	 */
	public void declareVertex(double x, double y);
	
	/**
	 * Returns a list of all adjacent vertices.
	 * 
	 * @return	a List of all adjacent vertices
	 */
	public List<Line2D.Double> getLines();
	
	/**
	 * Sets the position on the x-axis
	 * 
	 * @param x	new x position
	 */
	public void setX(double x);
	
	/**
	 * Sets the position on the y-axis
	 * 
	 * @param y	new y position
	 */
	public void setY(double y);
	
	/**
	 * Returns the smallest x-coordinate.
	 * 
	 * @return	the smallest x-coordinate
	 */
	public double getX();
	
	/**
	 * Returns the smallest x-coordinate.
	 * 
	 * @return	the smallest x-coordinate
	 */
	public double getY();
	
	/**
	 * Returns the difference between the greatest and smallest y-coordinates.
	 * 
	 * @return the height of the entity
	 */
	public double getHeight();
	
	/**
	 * Returns the difference between the greatest and smallest y-coordinates.
	 * 
	 * @return the width of the entity
	 */
	public double getWidth();
	
	/**
	 * Checks if the periphery of the entity intersects the periphery of another entity.
	 * 
	 * @param other	an entity
	 * @return	true if the entities intersect
	 */
 	public boolean intersects(Entity other);
	
}
