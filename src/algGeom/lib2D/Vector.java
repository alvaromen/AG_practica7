package algGeom.lib2D;

/**
 * Class that represents a 2D vector
 */
public class Vector extends Point {

    public Vector() {
        super(0, 0);
    }

    public Vector(double xx, double yy) {
        super(xx, yy);
    }

    public Vector(Point p) {
        super(p.x, p.y);
    }

    public Vector(Vector v) {
        super(v.x, v.y);
    }

    /**
     * c = this+b (sum of vectors)
     */
    public Vector add(Vector b) {
        return new Vector(x + b.getX(), y + b.getY());
    }

    /**
     * c= a-b (substraction of vectors)
     */
    public Vector sub(Vector b) {
        return new Vector(x - b.getX(), y - b.getY());
    }

    /**
     * c= a . b (scalar product)
     */
    public double dot(Vector b) {
        return (x * b.getX() + y * b.getY());
    }

    /**
     * c= t . a (vector product by a scalar)
     */
    public Vector scalarMult(double t) {
        return new Vector(x * t, y * t);
    }

    @Override
    public Vector copy() {
        return new Vector(x, y);
    }

    public void copy(Vector v) {
        x = v.x;
        y = v.y;
    }

    @Override
    public Vector get() {
        return this;
    }

    public Point getPoint() {
        return this;
    }

    public Line lineEquidistant(Point p){
        Point point = new Point((x+p.x)/2,(y+p.y)/2);
        Vector v = new Vector(-(p.y-y), (p.x-x));
        return new Line(point, point.suma(v));
    }
}
