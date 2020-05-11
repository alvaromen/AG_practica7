package algGeom.lib2D;

/**
 * Class that represents a line in the plane
 */
public class Line extends SegmentLine {

    public Line(Point a, Point b) {
        super(a, b);
    }

    public Line(SegmentLine s) {
        a = s.a;
        b = s.b;
    }

    @Override
    public boolean segmentIntersection(SegmentLine l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean impSegmentIntersection(SegmentLine l) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void check_t(double t) throws Invalid_T_Parameter {
    }


    public double distance(final Vector p) {
        
        Vector t, q, v;
        t = new Vector(a);
        q = new Vector(b);
        v = new Vector(q.sub(t));

        double landa = v.dot(p.sub(t)) / v.dot(v);

        double distancia = p.sub(t.sub(v.scalarMult(landa))).getModule();

        return distancia;
    }

    public boolean parallel(Line l) {

        Vector v = new Vector(b.resta(a));
        Vector u = new Vector(l.b.resta(l.a));

        if (this.isVertical()) {
            if (l.isVertical()) {
                return true;
            } else {
                return false;
            }
        }

        if (this.isHorizontal()) {
            if (l.isHorizontal()) {
                return true;
            } else {
                return false;
            }
        }

        if (BasicGeom.equal(v.x/u.x, v.y/u.y)){
            return true;
        }  
        
        return false;
    }

    public boolean perpendicular(Line l){
    
        Vector v = new Vector(b).sub(new Vector(a));
        Vector u = new Vector(l.b).sub(new Vector(l.a));

        if (BasicGeom.equal(v.dot(u), 0)) {
            return true;
        }

        return false;
    }

    public Vector reflection(Vector p) {
        
        Vector t = new Vector(a);
        Vector v = new Vector(b).sub(t);
        Vector q;
        double e = v.scalarMult(2).dot(p.sub(t)) / v.dot(v);
        q = new Vector(t.scalarMult(2).add(v.scalarMult(e).sub(p)));

        return q;
    }

    public Vector normal(Vector p) {
        
        Vector t = new Vector(a);
        Vector v = new Vector(b).sub(t);
        Vector n;
        Vector u;
        double landa = v.dot(p.sub(t)) / v.dot(v);

        u = new Vector(p.sub(t.add(v.scalarMult(landa))));
        n = new Vector(p.add(u));

        return n;
    }

    @Override
    public void out() {
        System.out.println("Line->");
        System.out.println("Point a: ");
        a.out();
        System.out.println("Point b: ");
        b.out();
    }

}
