package algGeom.lib3D;

import Util.BasicGeom;
import Util.*;

enum classifyLines {NON_INTERSECT, PARALLEL, INTERSECT, THESAME}

public class Line3d extends Edge3D {

    public Line3d(Vect3d o, Vect3d d) {
        super(o, d);
    }
    
    
    /**
     *
     * @return distance between line/line
     *
     */
    public double distance(Line3d l) {
       
        Vect3d v1 = new Vect3d(dest.sub(orig));
        Vect3d v2 = new Vect3d(l.dest.sub(l.orig));

        double distancia = Math.abs(orig.sub(l.orig).dot(v1.xProduct(v2))) / v1.xProduct(v2).module();

        return distancia;
    }

    /**
     *
     * @return  true if are parallel
     *
     */
    
    public boolean parallel (Line3d l){
        
        Vect3d aux = new Vect3d(this.getDirection().xProduct(l.getDirection()));
        
        if(BasicGeom.equal(aux.x, 0) && BasicGeom.equal(aux.y, 0) && BasicGeom.equal(aux.z, 0))  return true;
        
        return false;
    }

    /**
     *
     * @return true if they ara perpendicular
     *
     */
    public boolean perpendicular(Line3d l) {

        Vect3d v = new Vect3d(dest).sub(new Vect3d(orig));
        Vect3d u = new Vect3d(l.dest).sub(new Vect3d(l.orig));

        if (BasicGeom.equal(v.dot(u), 0)) {
            return true;
        }

        return false;
    }

    /**
     *
     * @return  the normal line in the point p 
     *
     */
    public Line3d normalLine(Vect3d p) {

        Vect3d v = new Vect3d(dest.sub(orig));
        Vect3d q, u;
        double landa = v.dot(p.sub(orig)) / v.dot(v);

        q = new Vect3d(orig.add(v.scalarMul(landa)));
        u = new Vect3d(p.sub(orig.add(v.scalarMul(landa))));

        return new Line3d(q, u);
    }


    
    public void out() {
        System.out.print("Line->Origin: ");
        orig.out();
        System.out.print("Line->Destination: ");
        dest.out();
    }

    
}
