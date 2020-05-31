package algGeom.lib3D;


import Util.*;
import javax.media.opengl.GL;
import Util.Draw;


public class DrawTriangleMesh extends Draw {

    TriangleMesh m;
    boolean drawTriangles;
    float size;

    public DrawTriangleMesh(TriangleMesh m) {
        this.m = m;
        drawTriangles = true;
        size = 1;
    }
    
    public DrawTriangleMesh(TriangleMesh m, boolean dt, float s) {
        this.m = m;
        drawTriangles = dt;
        size = s;
    }

    @Override
    public void drawObject(GL g) {
        for (int i = 0; i < m.getFacesSize(); i++) {
            Triangle3d t = new Triangle3d(m.getTriangle(i));
            DrawTriangle3d dt = new DrawTriangle3d(t, drawTriangles, size);
            dt.drawObject(g);
        }
        g.glEnd();

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);
    }
}
