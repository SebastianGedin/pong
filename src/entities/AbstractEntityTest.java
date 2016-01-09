package entities;

import static org.junit.Assert.*;

import java.awt.geom.Line2D;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pong.Ball;

public class AbstractEntityTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests that the constructor sets up the correct number of vertices.
	 */
	@Test
	public void testConstructor() {
		Ball test = new Ball(4, 0);
		assertEquals(4, test.vertices.size());
	}
	
	@Test
	public void testDeclareVertex() {
		Ball test = new Ball(4, 0);
		test.declareVertex(50, 100);
		test.declareVertex(75, 100);
		test.declareVertex(75, 10);
		test.declareVertex(50, 10);
		assertEquals(50, test.vertices.get(0)[0], 0.0001);
		assertEquals(100, test.vertices.get(0)[1], 0.0001);
		assertEquals(75, test.vertices.get(2)[0], 0.0001);
		assertEquals(10, test.vertices.get(2)[1], 0.0001);
	}
	
	@Test
	public void testGetLines() {
		Ball test = new Ball(4, 0);
		test.declareVertex(50, 100);
		test.declareVertex(75, 100);
		test.declareVertex(75, 10);
		test.declareVertex(50, 10);
		List<Line2D.Double> lines = test.getLines();
		assertEquals(test.vertices.get(0)[0], lines.get(0).getX1(), 0.0001);
		assertEquals(test.vertices.get(0)[1], lines.get(0).getY1(), 0.0001);
		assertEquals(test.vertices.get(test.vertices.size()-1)[0], lines.get(0).getX2(), 0.0001);
		assertEquals(test.vertices.get(test.vertices.size()-1)[1], lines.get(0).getY2(), 0.0001);
		for (int i=1;i<4;i++) {
			assertEquals(test.vertices.get(i-1)[0], lines.get(i).getX1(), 0.0001);
			assertEquals(test.vertices.get(i-1)[1], lines.get(i).getY1(), 0.0001);
			assertEquals(test.vertices.get(i)[0], lines.get(i).getX2(), 0.0001);
			assertEquals(test.vertices.get(i)[1], lines.get(i).getY2(), 0.0001);
		}
	}
	
	@Test
	public void testGetX() {
		Ball test = new Ball(4, 0);
		test.declareVertex(50, 100);
		test.declareVertex(75, 100);
		test.declareVertex(75, 10);
		test.declareVertex(50, 10);
		assertEquals(50, test.getX(), 0.0001);
	}
	
	@Test
	public void testGetY() {
		Ball test = new Ball(4, 0);
		test.declareVertex(50, 100);
		test.declareVertex(75, 100);
		test.declareVertex(75, 10);
		test.declareVertex(50, 10);
		assertEquals(10, test.getY(), 0.0001);
	}
	
	@Test
	public void testGetWidth() {
		Ball test = new Ball(4, 0);
		test.declareVertex(50, 100);
		test.declareVertex(75, 100);
		test.declareVertex(75, 10);
		test.declareVertex(50, 10);
		assertEquals(25, test.getWidth(), 0.0001);
	}
	
	@Test
	public void testGetHeight() {
		Ball test = new Ball(4, 0);
		test.declareVertex(50, 100);
		test.declareVertex(75, 100);
		test.declareVertex(75, 10);
		test.declareVertex(50, 10);
		assertEquals(90, test.getHeight(), 0.0001);
	}
	
	/**
	 * Tests setX() for a greater x than the current one.
	 */
	@Test
	public void testSetXGreater() {
		Ball test = new Ball(4, 0);
		test.declareVertex(50, 100);
		test.declareVertex(75, 100);
		test.declareVertex(75, 10);
		test.declareVertex(50, 10);
		test.setX(60);
		assertEquals(60, test.vertices.get(0)[0], 0.0001);
		assertEquals(85, test.vertices.get(1)[0], 0.0001);
		assertEquals(85, test.vertices.get(2)[0], 0.0001);
		assertEquals(60, test.vertices.get(3)[0], 0.0001);
	}
	
	/**
	 * Tests setX() for a greater x than the current one.
	 */
	@Test
	public void testSetXSmaller() {
		Ball test = new Ball(4, 0);
		test.declareVertex(50, 100);
		test.declareVertex(75, 100);
		test.declareVertex(75, 10);
		test.declareVertex(50, 10);
		test.setX(30);
		assertEquals(30, test.vertices.get(0)[0], 0.0001);
		assertEquals(55, test.vertices.get(1)[0], 0.0001);
		assertEquals(55, test.vertices.get(2)[0], 0.0001);
		assertEquals(30, test.vertices.get(3)[0], 0.0001);
	}
	
	/**
	 * Tests setY() for a greater y than the current one.
	 */
	@Test
	public void testSetYGreater() {
		Ball test = new Ball(4, 0);
		test.declareVertex(50, 100);
		test.declareVertex(75, 100);
		test.declareVertex(75, 10);
		test.declareVertex(50, 10);
		test.setY(20);
		assertEquals(110, test.vertices.get(0)[1], 0.0001);
		assertEquals(110, test.vertices.get(1)[1], 0.0001);
		assertEquals(20, test.vertices.get(2)[1], 0.0001);
		assertEquals(20, test.vertices.get(3)[1], 0.0001);
	}
	
	/**
	 * Tests setY() for a greater y than the current one.
	 */
	@Test
	public void testSetYSmaller() {
		Ball test = new Ball(4, 0);
		test.declareVertex(50, 100);
		test.declareVertex(75, 100);
		test.declareVertex(75, 10);
		test.declareVertex(50, 10);
		test.setY(5);
		assertEquals(95, test.vertices.get(0)[1], 0.0001);
		assertEquals(95, test.vertices.get(1)[1], 0.0001);
		assertEquals(5, test.vertices.get(2)[1], 0.0001);
		assertEquals(5, test.vertices.get(3)[1], 0.0001);
	}
	
	@Test
	public void testIntersectsTrue() {
		Ball test = new Ball(4, 0);
		test.declareVertex(50, 100);
		test.declareVertex(75, 100);
		test.declareVertex(75, 10);
		test.declareVertex(50, 10);
		Ball test2 = new Ball(3, 0);
		test2.declareVertex(40, 100);
		test2.declareVertex(80, 80);
		test2.declareVertex(40, 5);
		assertEquals(true, test.intersects(test2));
	}
	
	@Test
	public void testIntersectsFalse() {
		Ball test = new Ball(4, 0);
		test.declareVertex(50, 100);
		test.declareVertex(75, 100);
		test.declareVertex(75, 90);
		test.declareVertex(50, 90);
		Ball test2 = new Ball(3, 0);
		test2.declareVertex(40, 99);
		test2.declareVertex(50, 85);
		test2.declareVertex(40, 85);
		assertEquals(false, test.intersects(test2));
	}
}
