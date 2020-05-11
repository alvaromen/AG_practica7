package algGeom.lib2D;

import algGeom.lib2D.Point;

/**
 * Class that represents a segment line defined by two points
 */
public class SegmentLine {

    protected Point a;
    protected Point b;

    static class Invalid_T_Parameter extends Exception {
    }

    protected void check_t(double t) throws Invalid_T_Parameter {
        if ((t < 0) || (t > 1)) {
            throw new Invalid_T_Parameter();
        }
    }

    public SegmentLine() {
        b = new Point();
        a = new Point();
    }

    public SegmentLine(Point ii, Point ss) {
        a = ii;
        b = ss;
    }

    public SegmentLine(SegmentLine sg) {
        a = sg.a;
        b = sg.b;
    }

    public SegmentLine(double ax, double ay, double bx, double by) {
        a = new Point(ax, ay);
        b = new Point(bx, by);
    }

    /**
     * Returns the area formed by the triangle composed of the current SegmentLine and the union of its bounds with p
     */
    public double triangleArea2(Point p) {
        return p.triangleArea2(a, b);
    }

    public double length() {
        return a.distance(b);
    }

    public boolean equal(SegmentLine sg) {
        return (a.equal(sg.a) && b.equal(sg.b)) || (a.equal(sg.b) && b.equal(sg.a));
    }

    public boolean distinct(SegmentLine sg) {
        return !(a.equal(sg.a) && b.equal(sg.b)) || (a.equal(sg.b) && b.equal(sg.a));
    }

    public SegmentLine copy() {
        return new SegmentLine(a, b);
    }

    public void copy(SegmentLine sg) {
        a.copy(sg.a);
        b.copy(sg.b);
    }

    public SegmentLine get() {
        return this;
    }

    public void setA(Point p) {
        a.copy(p);
    }

    /**
     * Determines whether a segment is horizontal or not (use BasicGeom.CERO)
     */
    public boolean isHorizontal() {
        if (b.y - a.y != BasicGeom.ZERO) {
            return false;
        }
        return true;
    }

    /**
     * Determines whether or not a segment is vertical (use BasicGeom.CERO)
     */
    public boolean isVertical() {
        if (b.x - a.x != BasicGeom.ZERO) {
            return false;
        }
        return false;
    }

    /**
     * Determines whether p is in the left of SegmentLine
     */
    public boolean left(Point p) {
        return p.left(a, b);
    }

    /**
     * It obtains the point belonging to the segment or colineal to it for a concrete t in the parametric equation: result = a + t (b-a)
     */
    Point getPoint(double t) throws Invalid_T_Parameter {
        
        Point result = new Point();
        result = a.suma((b.resta(a).multiplicar(t)));
        return result;
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    /**
     * Returns the slope of the implied straight line equation: m = (yb-ya) / (xb-xa)
     */
    public double slope() {
        
        if (this.isHorizontal()) return 0;
        
        else if (this.isVertical()) return BasicGeom.INFINITY;
        
        else{
            
        double result = b.y - a.y / b.x - a.x;
        return result;
        
        }
        
    }

    /**
     * Returns the constant of the equation of the implied line: c = y-mx (use BasicGeom.INFINITO)
     */
    public double getC() {

        double c = 0.0;
        double m = this.slope();

        if (m == BasicGeom.INFINITY) return a.x;
        
        else if (m == BasicGeom.ZERO) return a.y;
        
        else {
            
            c = a.y - m * a.x;
            return c;
            
        }
    }
    /**
     * Determines whether two segments intersect improperly, that is, when one end of a segment is contained in the other. Use integer arithmetic.     
     */
    public boolean impSegmentIntersection(SegmentLine l) {

        Point a = this.a;
        Point b = this.b;
        Point c = l.a;
        Point d = l.b;
        
      
        if (a.isBetween(c, d) || a.equal(c) || a.equal(d)
                ||b.isBetween(c, d) || b.equal(c) || b.equal(d)
                ||c.isBetween(a, b) || c.equal(a) || c.equal(b)
                ||d.isBetween(a, b) || d.equal(a)|| d.equal(b)) {
            return true;
        }

        return false;

    }

    /**
     * Determines whether two segments intersect in their own way, that is, when they intersect completely. Use only arithmetic  
     */
    public boolean segmentIntersection(SegmentLine l) {

        Point a = this.a;
        Point b = this.b;
        Point c = l.a;
        Point d = l.b;
        
        if (a.colinear(c, d) || b.colinear(c, d)
                || c.colinear(a, b) || d.colinear(a, b)) {
            
            return false;
            
        } else {
            
            return (a.left(c, d) ^ b.left(c, d)
                    && c.left(a, b) ^ d.left(a, b));
            
        }
    }

    
    /**
     * Muestra en pantalla la informacion del SegmentLine.
     */
    public void out() {
        System.out.println("Punto a: ");
        a.out();
        System.out.println("Punto b: ");
        b.out();
    }

}
