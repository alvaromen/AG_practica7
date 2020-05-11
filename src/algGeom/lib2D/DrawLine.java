package algGeom.lib2D;

import Util.Draw;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.*;

/**
 * Class for drawing a line
 */
public class DrawLine extends Draw {

    Line vl;

    public DrawLine(Line l) {
        vl = l;
    }

    public Line getLine() {
        return vl;
    }

    @Override
    public void drawObject(GL g) {

        try {
            // screen coordiantes
            Point p1 = vl.getPoint(100);
            Point p2 = vl.getPoint(-100);
            double ax = convCoordX(p1.getX()),
                    ay = convCoordX(p1.getY()),
                    bx = convCoordX(p2.getX()),
                    by = convCoordX(p2.getY());
            g.glBegin(GL.GL_LINES);
            g.glVertex2d(ax, ay);
            g.glVertex2d(bx, by); //the fourth (w) component is zero!
            g.glEnd();
        } catch (SegmentLine.Invalid_T_Parameter ex) {
            Logger.getLogger(DrawLine.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {

        g.glLineWidth(1.5f);
        g.glColor3f(R, G, B);
        drawObject(g);
    }

}
