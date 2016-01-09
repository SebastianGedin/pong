package entities;

import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntity implements Entity {

	protected double z;
	protected List<double[]> vertices = new ArrayList<>();
	
	/**
	 * Constructor for an Entity. All vertices are initially set to (-1, -1).
	 * 
	 * @param n	number of vertices
	 * @param z	depth coordinate
	 */
	public AbstractEntity(int n, double z) {
		this.z = z;
		for (int i=0;i<n;i++) {
			double[] vector = {-1.,-1.};
			vertices.add(vector);
		}
	}

	@Override
	public void declareVertex(double x, double y) {
		for (int i=0;i<vertices.size();i++) {
			if (vertices.get(i)[0] == -1) {
				vertices.get(i)[0] = x;
				vertices.get(i)[1] = y;
				break;
			}
		}
	}
	
	@Override
	public List<Line2D.Double> getLines() {
		List<Line2D.Double> lines = new ArrayList<>();
		lines.add(new Line2D.Double(vertices.get(0)[0], vertices.get(0)[1], vertices.get(vertices.size()-1)[0], vertices.get(vertices.size()-1)[1]));
		for (int i=0;i<vertices.size()-1;i++) {
			lines.add(new Line2D.Double(vertices.get(i)[0], vertices.get(i)[1], vertices.get(i+1)[0], vertices.get(i+1)[1]));
		}
		return lines;
	}

	@Override
	public boolean intersects(Entity other) {
		List<Line2D.Double> lines = getLines();
		List<Line2D.Double> otherLines = other.getLines();
		for (Line2D.Double line : lines) {
			for (Line2D.Double otherLine : otherLines) {
				if (line.intersectsLine(otherLine)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public double getX() {
		double smallestX = vertices.get(0)[0];
		for (double[] vertex : vertices) {
			if (vertex[0] < smallestX) {
				smallestX = vertex[0];
			}
		}
		return smallestX;
	}

	@Override
	public double getY() {
		double smallestY = vertices.get(0)[1];
		for (double[] vertex : vertices) {
			if (vertex[1] < smallestY) {
				smallestY = vertex[1];
			}
		}
		return smallestY;
	}

	@Override
	public double getHeight() {
		double greatestY = vertices.get(0)[1];
		for (double[] vertex : vertices) {
			if (vertex[1] > greatestY) {
				greatestY = vertex[1];
			}
		}
		return greatestY - getY();
	}

	@Override
	public double getWidth() {
		double greatestX = vertices.get(0)[0];
		for (double[] vertex : vertices) {
			if (vertex[0] > greatestX) {
				greatestX = vertex[0];
			}
		}
		return greatestX - getX();
	}

	@Override
	public void setX(double x) {
		double deltaX = x - getX();
		for (double[] vector : vertices) {
			vector[0] += deltaX;
		}
	}

	@Override
	public void setY(double y) {
		double deltaY = y - getY();
		for (double[] vector : vertices) {
			vector[1] += deltaY;
		}
	}

}
