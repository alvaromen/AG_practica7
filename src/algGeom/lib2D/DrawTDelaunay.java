/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom.lib2D;

import algGeom.lib3D.*;
import Util.Draw;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.media.opengl.GL;

/**
 *
 * @author alvaro
 */
public class DrawTDelaunay extends Draw {

    TDelaunay delaunay;

    public DrawTDelaunay(TDelaunay d) {

        delaunay = d;
    }

    @Override
    public void drawObject(GL gl) {

        ArrayList<Geometry> tri = delaunay.getTriangles();
        
        for (int i = 0; i < tri.size(); i++) {
            Coordinate[] coords = delaunay.getG().getGeometryN(i).getCoordinates();

            for (int j = 0; j < 3; j++) {
                SegmentLine s = new SegmentLine(coords[j].x, coords[j].y, coords[j + 1].x, coords[j + 1].y);
                DrawSegment ds = new DrawSegment(s);
                ds.drawObject(gl);
            }
        }
    }

    @Override
    public void drawObjectC(GL gl, float R, float G, float B) {
        gl.glColor3f(R, G, B);
        drawObject(gl);
    }


}
