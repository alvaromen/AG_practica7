package algGeom.lib2D;

import static java.lang.Math.*;

/**
 * Class that represents a 2D point
 */

enum PointClassification {
    LEFT, RIGHT, FORWARD, BACKWARD, BETWEEN, ORIGIN, DEST
};

public class Point {


    protected double x;
    protected double y;

    public Point() {
        x = 0;
        y = 0;
    }

    public Point(double xx, double yy) {
        x = xx;
        y = yy;
    }

    public Point(Point p) {
        x = p.x;
        y = p.y;
    }

    /**
     * Constructor from an alpha angle (radians) and the vector module rp.
     * If polar == true, it must be builtup with the polar coordinates
     */
    public Point(double alpha, double rp, boolean polar) {
        if (polar) {
            
            x = rp * cos(alpha);
            y = rp * sin(alpha);
           
        } else {
            x = alpha;
            y = rp;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Return the alpha angle (radians) (note: consider the point quadrant)
     */
    public double getAlpha() {
        double alpha = Math.atan(y/x);
        //Para el segundo y tercer cuadrante hay que sumar PI
        if(x<0){
            alpha += PI;
        }
        //Para el cuarto cuadrante hay que sumar 2 * PI
        if(x>0 && y<0){
            alpha = (2 * PI) + alpha;
        }
        return alpha;
    }
  
    /**
     * Return the vector module rp (note: consider the point quadrant)
     */
    public double getModule() {
        return Math.sqrt(x*x+y*y);
    }


    public Point resta(Point punto){
    
        
        double x = this.x - punto.x;
        double y = this.y - punto.y;
        
        Point resultado = new Point(x,y);
        return resultado;
    }
    
    public Point suma(Point punto){
    
        
        double x = this.x + punto.x;
        double y = this.y + punto.y;
        
        Point resultado = new Point(x,y);
        return resultado;
    }
    
    public Point multiplicar(double t){
    
        
        double x = this.x * t;
        double y = this.y * t;
        
        Point resultado = new Point(x,y);
        return resultado;
    }
    /**
     * Determines the relative position of a point (this) with respect to other two given as a parameter 
     * (which can form a segment)    /**
     * Determines the relative position of a point (this) with respect to other two given as a parameter 
     * (which can form a segment)
     */
    
    public PointClassification classify (Point p0, Point p1) {
        
       Point p2 = this;
       Point a = p1.resta(p0);
       Point b = p2.resta(p0);
       double sa = a.x * b.y - b.x * a.y;
       
        if (sa > 0.0) return PointClassification.LEFT;
        if (sa < 0.0) return PointClassification.RIGHT; //resto de casos son colineales
        if ((a.x*b.x < 0.0) || (a.y*b.y < 0.0))  return PointClassification.BACKWARD;
        if (a.getModule() < b.getModule())  return PointClassification.FORWARD;
        if (p0.equal(p2)) return PointClassification.ORIGIN;
        if (p0.equal(p2)) return PointClassification.DEST;
        
       return PointClassification.BETWEEN;
    }

    boolean equal(Point pt) {
        return (BasicGeom.equal(x, pt.x) && BasicGeom.equal(y, pt.y));

    }

    /**
     * Distance between two points
     */
    public double distance(Point p) {
        double xr = x - p.getX();
        double yr = y - p.getY();
        return Math.sqrt(xr*xr+yr*yr);
    }

    /**
     * Calculate the double area of the triangle formed by (this, a, b)
     */
    public double triangleArea2(Point a, Point b) {
        return (this.x * a.y - this.y * a.x + a.x * b.y - a.y * b.x +b.x * this.y - b.y * this.x);
    }

    public boolean distinct(Point p) {
        return (Math.abs(x - p.x) > BasicGeom.ZERO || Math.abs(y - p.y) > BasicGeom.ZERO);
    }


    public Point copy() {
        return new Point(x, y);
    }

    public void copy(Point p) {
        x = p.x;
        y = p.y;
    }


    public Point get() {
        return this;
    }


    public void set(double xx, double yy) {
        x = xx;
        y = yy;
    }


    public void setX(double xx) {
        x = xx;
    }

    public void setY(double yy) {
        y = yy;
    }

    /**
     * It must used the method classify
     */
    public boolean left(Point a, Point b) {
        return classify(a, b) == PointClassification.LEFT;
    }

    /**
     * It must used the method classify
     */
    public boolean right(Point a, Point b) {
         return classify(a, b) == PointClassification.RIGHT;
    }

    /**
     * It must used the method classify
     */
    public boolean colinear(Point a, Point b) {
        PointClassification resultado = classify(a, b);
        return (resultado != PointClassification.LEFT) && (resultado != PointClassification.RIGHT);
    }

    /**
     * It must used the method classify
     */
    public boolean leftAbove(Point a, Point b) {
        PointClassification resultado = classify(a, b);
        return (resultado == PointClassification.LEFT) || (resultado != PointClassification.RIGHT);
    }

    /**
     * It must used the method classify
     */
    public boolean rightAbove(Point a, Point b) {
        PointClassification resultado = classify(a, b);
        return (resultado == PointClassification.RIGHT) || (resultado != PointClassification.LEFT);
    }
    
    /**
     * It must used the method classify
     */
    public boolean isBetween(Point a, Point b) {
        return classify(a, b) == PointClassification.BETWEEN;
    }
    
    /**
     * It must used the method classify
     */
    public boolean forward (Point a, Point b) {
        return classify(a, b) == PointClassification.FORWARD;
    }
    
    /**
     * It must used the method classify
     */
    public boolean backward (Point a, Point b) {
        return classify(a, b) == PointClassification.BACKWARD;
    }

    
    /**
     * Calculate the slope between two points (this and a)
     */
    public double slope(Point p) {
        if(BasicGeom.equal(x-p.getX(), 0)){
            return BasicGeom.INFINITY;
        }
        return ((y - p.getY())/(x - p.getX()));
    }


    /**
     * Muestra en pantalla los valores de las coordenadas del Point.
     */
    public void out() {
        System.out.print("Coordinate x: ");
        System.out.println(x);
        System.out.print("Coordinate y: ");
        System.out.println(y);
    }

}
