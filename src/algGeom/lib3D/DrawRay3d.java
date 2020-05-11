package algGeom.lib3D;

import Util.*;
import Util.Draw;
import javax.media.opengl.*;

public class DrawRay3d extends Draw {

    Ray3d vr;

    public DrawRay3d(Ray3d r) {
        vr = r;
    }

    public Ray3d getRay3d() {
        return vr;
    }

    @Override
    public void drawObject(GL g) {

       // screen coordiantes
            Vect3d v = new Vect3d(vr.getPoint(100));
            double ax = convCoordX(v.getX()),
                    ay = convCoordX(v.getY()),
                    az = convCoordX(v.getZ()),
                    bx = convCoordX(vr.orig.getX()),
                    by = convCoordX(vr.orig.getY()),
                    bz = convCoordX(vr.orig.getZ());
            g.glBegin(GL.GL_LINES);
            g.glVertex3d(ax, ay, az);
            g.glVertex3d(bx, by, az); //the fourth (w) component is zero!
            g.glEnd();
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);
    }
}
