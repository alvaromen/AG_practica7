package algGeom.lib2D;

import java.util.*;

/**
 * Class that represents a Bezier curve
 */
public class Bezier {

    protected int nVertexs; // Number of points of the curve
    protected ArrayList<Vector> vVertex; // Vector of points

    public Bezier() {
        vVertex = new ArrayList<Vector>();
        nVertexs = 0;
    }

    /**
     * Constructs a curve with nV vertex; caution, points must be added to the curve
     */
    public Bezier(int nV) {
        vVertex = new ArrayList<Vector>(nV);
        nVertexs = nV;
    }

    public Bezier(Bezier b) {
        vVertex = new ArrayList<Vector>(b.vVertex);
        nVertexs = b.nVertexs;
    }

    /**
     * Construct a curve with a vector of nV points
     */
    public Bezier(ArrayList<Vector> vert, int nV) {
        vVertex = new ArrayList<Vector>(vert);
        nVertexs = nV;
    }

    /**
     * Returns a copy
     */
    public Bezier copy() {
        Bezier nuevoBezier = new Bezier(nVertexs);
        nuevoBezier.vVertex = new ArrayList<Vector>(vVertex);
        nuevoBezier.nVertexs = nVertexs;
        return nuevoBezier;
    }

    public void set(Bezier b) {
        vVertex.clear();                           
        vVertex = new ArrayList<Vector>(b.vVertex); 
        nVertexs = b.nVertexs;
    }

    public void add(Vector v) {
        vVertex.add(v);
        nVertexs++;
    }

    public void add(Point p) {
        Vector v = new Vector(p);
        vVertex.add(v);
        nVertexs++;
    }

    public Vector getAt(int pos) {
        if (pos >= 0 && pos < nVertexs) {
            return vVertex.get(pos);
        } else {
            return null;
        }
    }

    public Vector getCopyAt(int pos) {
        if (pos >= 0 && pos < nVertexs) {
            return new Vector(vVertex.get(pos));
        } else {
            return null;
        }
    }


    public int size() {
        return nVertexs;
    }

    /**
     * Returnsn the edge [i], [i+1] in counterclockwise
     */
    public SegmentLine getArista(int i) {
        return new SegmentLine(getAt(i), getAt((i + 1) % nVertexs));
    }

}
