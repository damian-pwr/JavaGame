package org.spaceinvaders.classes;

/**
 * The Rect class represents a rectangular area
 */
public class Rect {
    Vector2D mins, maxs;

    /**
     * Constructs a Rect with the specified minimum and maximum coordinates
     * @param mins The minimum coordinates of the rectangle
     * @param maxs The maximum coordinates of the rectangle
     */
    public Rect(Vector2D mins, Vector2D maxs) {
        this.mins = mins.copy();
        this.maxs = maxs.copy();

        Vector2D.orderVectors(this.mins, this.maxs);
    }

    /**
     * Constructs a Rect with the specified coordinates
     * @param x1 X coordinate of the first point
     * @param y1 Y coordinate of the first point
     * @param x2 X coordinate of the second point
     * @param y2 Y coordinate of the second point
     */
    public Rect(double x1, double y1, double x2, double y2) {
        this.mins = new Vector2D(x1, y1);
        this.maxs = new Vector2D(x2, y2);

        Vector2D.orderVectors(this.mins, this.maxs);
    }

    /**
     * Constructs a Rect with the specified position and size
     * @param x X coordinate of the rectangle
     * @param y Y coordinate of the rectangle
     * @param width Width of the rectangle
     * @param height Height of the rectangle
     */
    public Rect(double x, double y, int width, int height) {
        this.mins = new Vector2D(x, y);
        this.maxs = new Vector2D(x + width, y + height);

        Vector2D.orderVectors(this.mins, this.maxs);
    }

    /**
     * Gets the x-coordinate of the rectangle's minimum point
     * @return The x-coordinate of the minimum point
     */
    public double getX() {
        return mins.getX();
    }

    /**
     * Gets the y-coordinate of the rectangle's minimum point
     * @return The y-coordinate of the minimum point
     */
    public double getY() {
        return mins.getY();
    }

    /**
     * Gets the width of the rectangle
     * @return The width of the rectangle
     */
    public double getWidth() {
        return maxs.getX() - mins.getX();
    }

    /**
     * Gets the height of the rectangle
     * @return The height of the rectangle
     */
    public double getHeight() {
        return maxs.getY() - mins.getY();
    }

    /**
     * Checks if rectangle intersects with other rectangle
     * @param other other rectangle
     * @return True if the rectangles intersect
     */
    public boolean intersects(Rect other) {
        return this.mins.getX() < other.maxs.getX() &&
                this.maxs.getX() > other.mins.getX() &&
                this.mins.getY() < other.maxs.getY() &&
                this.maxs.getY() > other.mins.getY();
    }
}
