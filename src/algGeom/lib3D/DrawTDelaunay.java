/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom.lib3D;

import Util.Draw;
import javax.media.opengl.GL;

/**
 *
 * @author alvaro
 */
public class DrawTDelaunay extends Draw {

    TDelaunay delaunay;
    
    @Override
    public void drawObject(GL gl) {
        
//        delaunay.getTriangles().
//        for(int i = 0; i< envolvente.edges.size(); i++){
//            DrawEdge de = new DrawEdge(envolvente.edges.get(i));
//            de.drawObject(gl);
//        }
    }

    @Override
    public void drawObjectC(GL gl, float R, float G, float B) {
        gl.glColor3f(R, G, B);
        drawObject(gl);
    }

}
