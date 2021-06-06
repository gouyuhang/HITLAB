/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public class TurtleSoup {

	/**
	 * Draw a square.
	 * 
	 * @param turtle     the turtle context
	 * @param sideLength length of each side
	 */
	public static void drawSquare(Turtle turtle, int sideLength) {
		turtle.forward(sideLength);
		turtle.turn(90.00);
		turtle.forward(sideLength);
		turtle.turn(90.00);
		turtle.forward(sideLength);
		turtle.turn(90.00);
		turtle.forward(sideLength);
		// throw new RuntimeException("implement me!");
	}

	/**
	 * Determine inside angles of a regular polygon.
	 * 
	 * There is a simple formula for calculating the inside angles of a polygon; you
	 * should derive it and use it here.
	 * 
	 * @param sides number of sides, where sides must be > 2
	 * @return angle in degrees, where 0 <= angle < 360
	 */
	public static double calculateRegularPolygonAngle(int sides) {
		double polyAngle = 180 - (360.0 / sides);
		return polyAngle;
		// throw new RuntimeException("implement me!");
	}

	/**
	 * Determine number of sides given the size of interior angles of a regular
	 * polygon.
	 * 
	 * There is a simple formula for this; you should derive it and use it here.
	 * Make sure you *properly round* the answer before you return it (see
	 * java.lang.Math). HINT: it is easier if you think about the exterior angles.
	 * 
	 * @param angle size of interior angles in degrees, where 0 < angle < 180
	 * @return the integer number of sides
	 */
	public static int calculatePolygonSidesFromAngle(double angle) {
		double a = 360.00 / (180.00 - angle);
		int b = (int) Math.round(a);
		return b;
		// throw new RuntimeException("implement me!");
	}

	/**
	 * Given the number of sides, draw a regular polygon.
	 * 
	 * (0,0) is the lower-left corner of the polygon; use only right-hand turns to
	 * draw.
	 * 
	 * @param turtle     the turtle context
	 * @param sides      number of sides of the polygon to draw
	 * @param sideLength length of each side
	 */
	public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
		for (int i = 1; i <= sides; i++) {
			turtle.forward(sideLength);
			turtle.turn(360 / sides);
		}
		// throw new RuntimeException("implement me!");
	}

	/**
	 * Given the current direction, current location, and a target location,
	 * calculate the Bearing towards the target point.
	 * 
	 * The return value is the angle input to turn() that would point the turtle in
	 * the direction of the target point (targetX,targetY), given that the turtle is
	 * already at the point (currentX,currentY) and is facing at angle
	 * currentBearing. The angle must be expressed in degrees, where 0 <= angle <
	 * 360.
	 *
	 * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
	 * 
	 * @param currentBearing current direction as clockwise from north
	 * @param currentX       current location x-coordinate
	 * @param currentY       current location y-coordinate
	 * @param targetX        target point x-coordinate
	 * @param targetY        target point y-coordinate
	 * @return adjustment to Bearing (right turn amount) to get to target point,
	 *         must be 0 <= angle < 360
	 */
	public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY, int targetX,
			int targetY) {
		double degree = Math.toDegrees(Math.atan2(targetY - currentY, targetX - currentX));
		degree = 90 - degree - currentBearing;
		if (degree < 0) {
			degree += 360;
		}
		return degree;
		// throw new RuntimeException("implement me!");
	}

	/**
	 * Given a sequence of points, calculate the Bearing adjustments needed to get
	 * from each point to the next.
	 * 
	 * Assumes that the turtle starts at the first point given, facing up (i.e. 0
	 * degrees). For each subsequent point, assumes that the turtle is still facing
	 * in the direction it was facing when it moved to the previous point. You
	 * should use calculateBearingToPoint() to implement this function.
	 * 
	 * @param xCoords list of x-coordinates (must be same length as yCoords)
	 * @param yCoords list of y-coordinates (must be same length as xCoords)
	 * @return list of Bearing adjustments between points, of size 0 if (# of
	 *         points) == 0, otherwise of size (# of points) - 1
	 */
	public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
		List<Double> resultList = new ArrayList<>();
		double currentBearing = 0.0;
		double degree = 0.0;
		if (xCoords.size() == 1 || xCoords.size() == 0) {
			return resultList;
		}
		for (int i = 0; i < xCoords.size() - 1; ++i) {
			degree = calculateBearingToPoint(currentBearing, xCoords.get(i), yCoords.get(i), xCoords.get(i + 1),
					yCoords.get(i + 1));
			resultList.add(degree);
			currentBearing = degree;
		}
		return resultList;
		// throw new RuntimeException("implement me!");
	}

	/**
	 * Given a set of points, compute the convex hull, the smallest convex set that
	 * contains all the points in a set of input points. The gift-wrapping algorithm
	 * is one simple approach to this problem, and there are other algorithms too.
	 * 
	 * @param points a set of points with xCoords and yCoords. It might be empty,
	 *               contain only 1 point, two points or more.
	 * @return minimal subset of the input points that form the vertices of the
	 *         perimeter of the convex hull
	 */
	public static Set<Point> convexHull(Set<Point> points) {

		if (points.size() < 3) {
			return points;
		}
		Set<Point> covexHull = new HashSet<Point>();
		List list = new ArrayList(points);
		int l = 0;
		for (int i = 1; i < points.size(); i++) {
			if (((Point) list.get(i)).x() < ((Point) list.get(l)).x()) {
				l = i;
			}
		}
		int p = l, q;
		do {
			covexHull.add((Point) list.get(p));
			q = (p + 1) % (points.size());
			for (int i = 0; i < points.size(); i++) {
				double val = (((Point) list.get(i)).y() - ((Point) list.get(p)).y())
						* (((Point) list.get(q)).x() - ((Point) list.get(i)).x())
						- (((Point) list.get(i)).x() - ((Point) list.get(p)).x())
								* (((Point) list.get(q)).y() - ((Point) list.get(i)).y());
				if (val < 0.0) {
					q = i;
				}
			}
			p = q;
		} while (p != l);
		return covexHull;

		// throw new RuntimeException("implement me!");
	}

	/**
	 * Draw your personal, custom art.
	 * 
	 * Many interesting images can be drawn using the simple implementation of a
	 * turtle. For this function, draw something interesting; the complexity can be
	 * as little or as much as you want.
	 * 
	 * @param turtle the turtle context
	 */
	public static void drawPersonalArt(Turtle turtle) {

		for (int i = 0; i <= 360; i += 10) {
			drawRegularPolygon(turtle, 180, 4 + 2 * (i % 5));
			turtle.turn(i);
			switch ((i / 10) % 10) {
			case 0:
				turtle.color(PenColor.BLACK);
				break;
			case 1:
				turtle.color(PenColor.GRAY);
				break;
			case 2:
				turtle.color(PenColor.RED);
				break;
			case 3:
				turtle.color(PenColor.PINK);
				break;
			case 4:
				turtle.color(PenColor.ORANGE);
				break;
			case 5:
				turtle.color(PenColor.YELLOW);
				break;
			case 6:
				turtle.color(PenColor.GREEN);
				break;
			case 7:
				turtle.color(PenColor.CYAN);
				break;
			case 8:
				turtle.color(PenColor.BLUE);
				break;
			case 9:
				turtle.color(PenColor.MAGENTA);
				break;
			}
		}
		// throw new RuntimeException("implement me!");
	}

	/**
	 * Main method.
	 * 
	 * This is the method that runs when you run "java TurtleSoup".
	 * 
	 * @param args unused
	 */
	public static void main(String args[]) {
		DrawableTurtle turtle = new DrawableTurtle();

		// drawSquare(turtle, 40);
		// drawRegularPolygon(turtle,6,40);
		drawPersonalArt(turtle);

		// draw the window
		turtle.draw();

	}

}
