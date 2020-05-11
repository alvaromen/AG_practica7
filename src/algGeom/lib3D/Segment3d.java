package algGeom.lib3D;

public class Segment3d extends Edge3D {

    public Segment3d(Vect3d o, Vect3d d) {
        super(o, d);
    }

    /**
     * Show values of the segment
     */
    public void out() {
        System.out.print("Segment->Origin: ");
        orig.out();
        System.out.print("Segment->Destination: ");
        dest.out();
    }

}
