package org.spaceinvaders.classes;

/**
 * 2D Vector
 */
public class Vector2D {
    private double x;
    private double y;

    /**
     * Constructor that takes both X and Y
     * @param x X component
     * @param y Y component
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor
     * @param other <code>Vector2D</code> to copy
     */
    public Vector2D(Vector2D other) {
        this(other.x, other.y);
    }

    /**
     * Constructor that takes angle
     * @param angle angle of new vector
     */
    public Vector2D(double angle) {
        this(Math.cos(angle), Math.sin(angle));
    }

    /**
     * Creates vector with both components set to 0
     */
    public Vector2D() {
        this(0, 0);
    }

    /**
     * Returns X component of vector
     * @return X component
     */
    public double getX() {
        return x;
    }

    /**
     * Sets X component of vector
     * @param x X component
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns Y component of vector
     * @return Y component
     */
    public double getY() {
        return y;
    }

    /**
     * Sets Y component of vector
     * @param y Y component
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Returns length of vector
     * @return length of vector
     */
    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Returns angle of vector
     * @return angle of vector
     */
    public double angle() {
        return Math.atan2(y, x);
    }

    /**
     * Normalizes vector so its length is 1 unit
     */
    public void normalize() {
        final double len = length();
        x /= len;
        y /= len;
    }

    /**
     * Returns normalized vector with length of 1 unit
     * @return normalized vector
     */
    public Vector2D getNormalized() {
        final double len = length();
        return new Vector2D(x / len, y / len);
    }

    /**
     * Multiplies vector by scalar
     * @param d scalar
     */
    public void mul(double d) {
        this.x *= d;
        this.y *= d;
    }

    /**
     * Divides vector by scalar
     * @param d scalar
     */
    public void div(double d) {
        this.x /= d;
        this.y /= d;
    }

    /**
     * Adds vector to this vector
     * @param other second vector
     */
    public void add(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
    }

    /**
     * Subtracts vector to this vector
     * @param other second vector
     */
    public void sub(Vector2D other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    /**
     * Calculates distance between 2 vectors
     * @param other second vector
     * @return distance
     */
    public double distance(Vector2D other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    /**
     * Returns dot product of 2 vectors
     * @param other second vector
     * @return dot product
     */
    public double dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    /**
     * Returns cross product of 2 vectors
     * @param other second vector
     * @return cross product
     */
    public double cross(Vector2D other) {
        return this.x * other.y - this.y * other.x;
    }

    /**
     * Orders 2 vectors
     * @param v1 first vector, becomes minimal vector after this function
     * @param v2 second vector, becomes maximal vector after this function
     */
    public static void orderVectors(Vector2D v1, Vector2D v2) {
        if(v1.x > v2.x) {
            final double tmp = v1.x;
            v1.x = v2.x;
            v2.x = tmp;
        }

        if(v1.y > v2.y) {
            final double tmp = v1.y;
            v1.y = v2.y;
            v2.y = tmp;
        }
    }

    /**
     * Copies vector to a new object
     * @return Copied vector
     */
    public Vector2D copy() {
        return new Vector2D(this);
    }
}
