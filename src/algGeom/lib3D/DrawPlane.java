package algGeom.lib3D;

import Util.*;
import Util.Draw;
import javax.media.opengl.*;

public class DrawPlane extends Draw {

    Plane pl;

    public DrawPlane(Plane p) {
        pl = p;
    }

    public Plane getPlane() {
        return pl;
    }

    @Override
    public void drawObject(GL g) {
        Line3d l1 = new Line3d(pl.a, pl.b);
        Line3d l2 = new Line3d(pl.c, pl.b);
        Vect3d p1 = new Vect3d(l1.getPoint(100));
        Vect3d p2 = new Vect3d(l2.getPoint(-100));
        Vect3d p3 = new Vect3d(l1.getPoint(-100));
        Vect3d p4 = new Vect3d(l2.getPoint(100));
        g.glBegin(GL.GL_POLYGON);
        g.glVertex3d(p1.getX(), p1.getY(), p1.getZ());
        g.glVertex3d(p2.getX(), p2.getY(), p2.getZ());
        g.glVertex3d(p3.getX(), p3.getY(), p3.getZ());
        g.glVertex3d(p4.getX(), p4.getY(), p4.getZ());
        g.glEnd();
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);
    }

    public void drawObjectC(GL g, float R, float G, float B, float A) {
        g.glColor4f(R, G, B, A);
        drawObject(g);
    }

}
