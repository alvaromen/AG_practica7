package algGeom.lib3D;

import Util.*;
import javax.media.opengl.*;
import Util.Draw;

public class DrawTriangle3d extends Draw {

    Triangle3d tr;
    Vect3d n;

    /**
     * Creates a new instance of VisuPunto
     */
    public DrawTriangle3d(Triangle3d t) {
        tr = t;
        n = tr.normal();
    }

    @Override
    public void drawObject(GL g) {

        g.glColor3f(1,0,0);
        g.glBegin(GL.GL_TRIANGLES);
        g.glNormal3f((float) n.x, (float) n.y, (float) n.z);
        g.glVertex3d((float) tr.a.x, (float) tr.a.y, (float) tr.a.z);
        g.glVertex3d((float) tr.b.x, (float) tr.b.y, (float) tr.b.z);
        g.glVertex3d((float) tr.c.x, (float) tr.c.y, (float) tr.c.z);

        g.glEnd();
        
        g.glColor3f(0,0,1);
        g.glBegin(GL.GL_LINES);
        g.glVertex3d( tr.a.x, tr.a.y, tr.a.z);
        g.glVertex3d( tr.b.x, tr.b.y, tr.b.z);
        g.glVertex3d( tr.c.x, tr.c.y, tr.c.z);
        g.glVertex3d( tr.a.x, tr.a.y, tr.a.z);
        
        
        g.glEnd();

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);
    }

}
