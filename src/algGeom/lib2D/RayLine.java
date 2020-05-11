package algGeom.lib2D;


/**
 * Class that represents a ray in the plane
 */
public class RayLine extends SegmentLine{
    
    public RayLine (Point a, Point b){
    	super (a,b);
    }
	
    public RayLine (SegmentLine s){
        a = s.a;
        b = s.b;
    }
    
    @Override
    public boolean segmentIntersection (SegmentLine l) {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean impSegmentIntersection (SegmentLine l) {
        throw new UnsupportedOperationException();
    }
 
    /**
     * t paramenter should be > 0
     */
    @Override
    protected void check_t(double t) throws Invalid_T_Parameter {
        if (t < 0) throw new Invalid_T_Parameter();
    }
    
    
    //@Override
    public boolean intersects(RayLine r, Vector interseccion)
    {
        //XXXX
        return true;
    }
    
    //@Override
    public boolean intersects(SegmentLine r, Vector interseccion)
    {
        //XXXXX
        return true;
    }
 
    public void out () {
        System.out.println ("RayLine->");
        System.out.println ("Punto a: ");
        a.out ();
        System.out.println ("Punto b: ");
        b.out ();
    }
    
}
