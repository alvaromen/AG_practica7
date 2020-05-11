package algGeom.lib3D;

import Util.*;
import Util.Draw;
import javax.media.opengl.GL;

public class DrawLine3d extends Draw{
    Line3d line;

    public DrawLine3d(Line3d e) {
        line = e;
    }

    public Line3d getLine() {
        return line;
    }

    @Override
    public void drawObject(GL g) {
        
            Vect3d v = new Vect3d(line.getPoint(100));
            Vect3d u = new Vect3d(line.getPoint(-100));

            g.glBegin(GL.GL_LINES);
            g.glVertex3d(v.getX(), v.getY(), v.getZ());
            g.glVertex3d(u.getX(), u.getY(), u.getZ()); //the fourth (w) component is zero!

            g.glEnd();     

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);

    }
}
