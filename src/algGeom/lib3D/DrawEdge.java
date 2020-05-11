/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom.lib3D;

import Util.*;
import javax.media.opengl.GL;

/**
 *
 * @author alvmo
 */
public class DrawEdge  extends Draw{
    
    Edge adge;
    public DrawEdge(Edge e){
        this.adge = e; 
    }

    @Override
    public void drawObject(GL g) {
        g.glBegin(GL.GL_LINES);
            g.glVertex3d(adge.getA().x, adge.getA().y, adge.getA().z);
            g.glVertex3d(adge.getB().x, adge.getB().y, adge.getB().z);
        g.glEnd();
        
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);
    }
    
}
