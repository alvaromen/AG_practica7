package algGeom.lib2D;

import Util.Draw;
import javax.media.opengl.*;

public class DrawPointCloud extends Draw {

    PointCloud pc;

    
    public DrawPointCloud(PointCloud p) {
        pc = p;
    }

    public PointCloud getPointCloud() {
        return pc;
    }

    @Override
    public void drawObject(GL g) {
        for(int i = 0; i < pc.size(); i++){
            // screen coordinates
            float x = convCoordX(pc.getPoint(i).getX());
            float y = convCoordY(pc.getPoint(i).getY());

            g.glPointSize(10.0f);

            g.glBegin(GL.GL_POINTS);
            g.glVertex2d(x, y);
            g.glEnd();
        }
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {

      g.glColor3f(R, G, B);
      this.drawObject(g);
    }
    

}
