package algGeom.lib2D;

import Util.Draw;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.*;

/**
 * Class for drawing a ray
 */
public class DrawRay extends Draw {

    RayLine rl;

    public DrawRay(RayLine l) {
        rl = l;
    }

    public RayLine getRay() {
        return rl;
    }

    @Override
    public void drawObject(GL g) {
        try {
            // screen coordiantes
            Point p = rl.getPoint(100);
            double ax = rl.getA().getX(), 
                    ay = rl.getA().getY(),
                    bx = p.getX(),
                    by = p.getY();
            ax = convCoordX(ax);
            ay = convCoordX(ay);
            bx = convCoordX(bx);
            by = convCoordX(by);
            g.glBegin(GL.GL_LINES);
            g.glVertex2d(ax, ay);
            g.glVertex2d(bx, by); //the fourth (w) component is zero!
            g.glEnd();
        } catch (SegmentLine.Invalid_T_Parameter ex) {
            Logger.getLogger(DrawRay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glLineWidth(3.0f);
        g.glColor3f(R, G, B);
        drawObject(g);

    }

}
