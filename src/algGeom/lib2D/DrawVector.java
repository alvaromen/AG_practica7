package algGeom.lib2D;

import Util.Draw;
import javax.media.opengl.GL;

public class DrawVector extends Draw {

    private Vector vector;

    public DrawVector(Vector v) {
        vector = v;
    }
    
    @Override
    public void drawObject(GL g) {
       
        SegmentLine vec = new SegmentLine(new Point(0, 0), vector);
        DrawSegment dvec = new DrawSegment(vec);
        dvec.drawObject(g);
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);
    }
}
