package algGeom.lib2D;

import Util.Draw;
import javax.media.opengl.*;

/**
 * Class for drawing a Segment
 */
public class DrawSegment extends Draw {

    SegmentLine vs;

    public DrawSegment(SegmentLine s) {
        vs = s;
    }

    public SegmentLine getSegment() {
        return vs;
    }

    @Override
    public void drawObject(GL g) {

        // screen coordinates
        float ax = convCoordX(vs.getA().getX());
        float ay = convCoordX(vs.getA().getY());
        float bx = convCoordX(vs.getB().getX());
        float by = convCoordX(vs.getB().getY());

        g.glBegin(GL.GL_LINES);
        g.glVertex2d(ax, ay);
        g.glVertex2d(bx, by);
        g.glEnd();

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        
        g.glColor3f(R, G, B);
        g.glLineWidth(3.0f);
        drawObject(g);

    }

}
