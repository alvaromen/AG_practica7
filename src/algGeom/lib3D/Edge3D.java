package algGeom.lib3D;

public class Edge3D {

    Vect3d orig;
    Vect3d dest;

    public Edge3D() {
        orig = new Vect3d();
        dest = new Vect3d();
    }

    public Edge3D(Vect3d o, Vect3d d) {
        orig = new Vect3d(o);
        dest = new Vect3d(d);
    }

    public Vect3d getPoint(double t) {
        return new Vect3d(orig.add((dest.sub(orig).scalarMul(t))));

    }
    
    public Vect3d getOrigin() {
        return orig;
    }
    
    public Vect3d getDestination() {
        return dest;
    }
    
    public Vect3d getDirection() {
        return dest.sub(orig);
    }

    public void out() {
        System.out.print("Edge->Origin: ");
        orig.out();
        System.out.print("Edge3d->Destination: ");
        dest.out();
    }
}
