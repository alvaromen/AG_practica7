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
public class DrawChIncremental extends Draw{

    ChIncremental envolvente;
    
    public DrawChIncremental(ChIncremental chIncremental){
        envolvente=chIncremental;
    }
    
    @Override
    public void drawObject(GL gl) {
        for(int i = 0; i< envolvente.edges.size(); i++){
            DrawEdge de = new DrawEdge(envolvente.edges.get(i));
            de.drawObject(gl);
        }
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);
    }
    
}

