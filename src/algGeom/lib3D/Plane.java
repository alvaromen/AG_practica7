package algGeom.lib3D;

import Util.*;

enum intersectionType {
    POINT, SEGMENT, COPLANAR
}

public class Plane {

    static public class IntersectionTriangle {

        public intersectionType type;
        public Vect3d p;
        public Segment3d s;
    }

    static public class IntersectionLine {

        public intersectionType type;
        public Vect3d p;
    }

    Vect3d a, b, c; //tres puntos cualquiera del plano  

    /**
     *
     * @param p en pi = p+u * lambda + v * mu -> r en los puntos (R,S,T)
     * @param u en pi = p+u * lambda + v * mu -> d en los puntos (R,S,T)
     * @param v en pi = p+u * lambda + v * mu -> t en los puntos (R,S,T)
     * @param arePoints = false, then params are p+u * lambda + v * mu, otherwise are points (R,S,T)
     */
    public Plane(Vect3d p, Vect3d u, Vect3d v, boolean arePoints) {
        if (!arePoints) { //are vectors: pi =  p+u * lambda + v * mu 
            a = p;
            b = u.add(a);
            c = v.add(a);
        } else { // are 3 points in the plane 
            a = p;
            b = u;
            c = v;
        }
    }

    /**
     *
     * @return  A in AX+BY+CZ+D = 0;
     *
     */
    public double getA() {

        return (BasicGeom.determinant2x2(a.getY() - b.getY(), a.getZ() - b.getZ(),
                                         c.getY() - b.getY(), c.getZ() - b.getZ()));
    }

    /**
     *
     * @return  B in AX+BY+CZ+D = 0;
     *
     */
    public double getB() {

        return (BasicGeom.determinant2x2(a.getZ() - b.getZ(), a.getX() - b.getX(),
                                         c.getZ() - b.getZ(), c.getX() - b.getX()));
    }

    /**
     *
     * @return  C in AX+BY+CZ+D = 0;
     *
     */
    public double getC() {
        return (BasicGeom.determinant2x2(a.getX() - b.getX(), a.getY() - b.getY(),
                                         c.getX() - b.getX(), c.getY() - b.getY()));
    }

    /**
     *
     * @return  D in AX+BY+CZ+D = 0;
     *
     */
    public double getD() {

        return (-1) * (getA() * a.getX() + getB() * a.getY() + getC() * a.getZ());
    }

    /**
     *
     * @return the normal vector of (A,B,C) in Ax+By+Cz+D = 0
     */
    public Vect3d getNormal() {
        double A = getA();
        double B = getB();
        double C = getC();
        Vect3d normal = new Vect3d(A, B, C);
        return normal.scalarMul(1 / normal.module());
    }

    /**
     * @return  the point of the parametric function (plano=p+u*lambda+v*mu) 
     */
    public Vect3d getPointParametric(double lambda, double mu) {
        Vect3d u = b.sub(a),
                v = c.sub(a);

        return a.add(u.scalarMul(lambda)).add(v.scalarMul(mu));
    }

    /**
     *  @return  Distance between a plane/point
     */
    public double distance(Vect3d p, Vect3d q) {
       Vect3d n = new Vect3d(getA(), getB(), getC());
       double lambda = -(n.dot(p) + getD());
       q = p.add(n.scalarMul(lambda)); //getting the closest point
       Vect3d distance = n.scalarMul(lambda);
       double x = distance.getX(),
               y = distance.getY(),
               z = distance.getZ();
       return Math.sqrt(x*x + y*y + z*z);
    }

    /**
     *  @return  true if p is in the plane 
     */
    public boolean coplanar(Vect3d p) {
        Vect3d q = null;
        return BasicGeom.equal(distance(p, q), 0); // if distance is 0, then the point is in
    }

    /**
     *  @return  the point of intersection of this and pa and pb  
     */
    public boolean intersect (Plane pa, Plane pb, Vect3d q){
    	double det = BasicGeom.determinant3x3(
                getA(), getB(), getC(),
                pa.getA(), pa.getB(), pa.getC(),
                pb.getA(), pb.getB(), pb.getC());
        if(BasicGeom.equal(det, 0)){
            return false;
        }
        double detX = BasicGeom.determinant3x3(
                    getD(), getB(), getC(),
                    pa.getD(), pa.getB(), pa.getC(),
                    pb.getD(), pb.getB(), pb.getC()),
                detY = BasicGeom.determinant3x3(
                    getA(), getD(), getC(),
                    pa.getA(), pa.getD(), pa.getC(),
                    pb.getA(), pb.getD(), pb.getC()),
                detZ = BasicGeom.determinant3x3(
                    getA(), getB(), getD(),
                    pa.getA(), pa.getB(), pa.getD(),
                    pb.getA(), pb.getB(), pb.getD());
        double x = -(detX/det),
                y = -(detY/det),
                z = -(detZ/det);
        q.setVert(x, y, z);
        return true;
    }
    
    
    /**
     *  @return  the point of intersection of this and the line3d p  
     */
    public boolean intersect (Line3d p, Vect3d q){
        Vect3d n = new Vect3d(getA(), getB(), getC());
        Vect3d v = p.dest.sub(p.orig);       
        if(BasicGeom.equal(n.dot(v), 0)){
            return false;
        }
        double lambda = (-(n.dot(p.orig)+getD()))/(n.dot(v));
        Vect3d a = p.getPoint(lambda);
        q.setVert(a.x, a.y, a.z);
        return true;
    }
    

    /**
     * shows the plane values 
     */
    public void out() {
        System.out.print("Plane->a: ");
        System.out.println(a);
        System.out.print("Plane->b: ");
        System.out.println(b);
        System.out.print("Plane->c: ");
        System.out.println(c);
    }

}
