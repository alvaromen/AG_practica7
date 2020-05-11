package algGeom.lib2D;

import Util.Draw;
import javax.media.opengl.*;

/**
 * Class for drawing the cooridnate axis
 */
public class DrawAxis extends Draw {

    public DrawAxis() {

    }

    @Override
    public void drawObject(GL g) {

        // X axis segments 
        for (int i = -BasicGeom.RANGE; i <= BasicGeom.RANGE; i += 10) {
            SegmentLine sl = new SegmentLine(i, -1.5, i, 1.5);
            DrawSegment dsl = new DrawSegment(sl);
            dsl.drawObjectC(g, 1f, 0f, 0f);
        }

        // Y axis segments  
        for (int i = -BasicGeom.RANGE; i <= BasicGeom.RANGE; i += 10) {
            SegmentLine sl = new SegmentLine(-1.5, i, 1.5, i);
            DrawSegment dsl = new DrawSegment(sl);
            dsl.drawObjectC(g, 0f, 1f, 0f);
        }

        // Draw X axis
        RayLine x = new RayLine(new Point(-BasicGeom.RANGE, 0), new Point(0, 0));
        DrawRay dx_red = new DrawRay(x);
        dx_red.drawObjectC(g, 1f, 0f, 0f);

        // Draw Y axis
        RayLine y = new RayLine(new Point(0, -BasicGeom.RANGE), new Point(0, 0));
        DrawRay dy_green = new DrawRay(y);
        dy_green.drawObjectC(g, 0f, 1f, 0f);

        // Origin
        Point p = new Point(0, 0);
        DrawPoint dp = new DrawPoint(p);
        dp.drawObjectC(g, 1f, 1f, 1f);

    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        throw new UnsupportedOperationException();
    }

}
