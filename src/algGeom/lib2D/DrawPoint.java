package algGeom.lib2D;

import Util.Draw;
import javax.media.opengl.*;

/**
 * Class for drawing a Point
 */
public class DrawPoint extends Draw {

    Point vp;

    public DrawPoint(Point p) {
        vp = p;
    }

    public Point getPoint() {
        return vp;
    }

    @Override
    public void drawObject(GL g) {

        // screen coordinates
        float x = convCoordX(vp.getX());
        float y = convCoordY(vp.getY());

        g.glPointSize(5.0f);

        g.glBegin(GL.GL_POINTS);
        g.glVertex2d(x, y);
        g.glEnd();

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {

        g.glColor3f(R, G, B);
        drawObject(g);

    }
}
