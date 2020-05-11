package algGeom.lib3D;

import Util.*;
import Util.Draw;
import javax.media.opengl.*;

public class DrawAxis extends Draw {

    public DrawAxis() {

    }

    @Override
    public void drawObject(GL g) {

        double bound = 10000;

        // X Axis
        Segment3d x = new Segment3d(new Vect3d(-bound, 0, 0), new Vect3d(bound, 0, 0));
        DrawSegment3d dx = new DrawSegment3d(x);
        dx.drawObjectC(g, 1.0f, 0.0f, 0.0f);

        // Y Axis
        Segment3d y = new Segment3d(new Vect3d(0, -bound, 0), new Vect3d(0, bound, 0));
        DrawSegment3d dy = new DrawSegment3d(y);
        dy.drawObjectC(g, 0.0f, 1.0f, 0.0f);

        // Z Axis
        Segment3d z = new Segment3d(new Vect3d(0, 0, -bound), new Vect3d(0, 0, bound));
        DrawSegment3d dz = new DrawSegment3d(z);
        dz.drawObjectC(g, 0.0f, 0.0f, 1.0f);

        // Origin.
        Vect3d p = new Vect3d(0, 0, 0);
        DrawVect3d dp = new DrawVect3d(p);
        dp.drawObjectC(g, 0f, 0f, 0f);
        
        
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        throw new UnsupportedOperationException();
    }

}
