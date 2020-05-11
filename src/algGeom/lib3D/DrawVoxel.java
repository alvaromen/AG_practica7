/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates * and open the template in the editor.

 */
package algGeom.lib3D;

import Util.Draw;
import javax.media.opengl.*;

/**
 *
 * @author alvaro
 */
public class DrawVoxel extends Draw {

    Voxel voxel;
    
    public DrawVoxel(Voxel voxel){
        this.voxel = voxel;
    }
    
    public Voxel getVoxel(){
        return voxel;
    }
    
    @Override
    public void drawObject(GL g) {
        //g.glLineWidth(5.0f);
        g.glBegin(GL.GL_LINES);
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMin().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMin().getY(), (float) voxel.getMin().getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINES);
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMin().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMax().getY(), (float) voxel.getMin().getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINES);
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMax().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMax().getY(), (float) voxel.getMin().getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINES);
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMax().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMin().getY(), (float) voxel.getMin().getZ());
        g.glEnd();
        
        
        g.glBegin(GL.GL_LINES);
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMin().getY(), (float) voxel.getMax().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMin().getY(), (float) voxel.getMax().getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINES);
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMin().getY(), (float) voxel.getMax().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMax().getY(), (float) voxel.getMax().getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINES);
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMax().getY(), (float) voxel.getMax().getZ());
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMax().getY(), (float) voxel.getMax().getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINES);
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMax().getY(), (float) voxel.getMax().getZ());
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMin().getY(), (float) voxel.getMax().getZ());
        g.glEnd();
        
        
        g.glBegin(GL.GL_LINES);
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMin().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMin().getY(), (float) voxel.getMax().getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINES);
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMax().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMax().getY(), (float) voxel.getMax().getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINES);
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMin().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMin().getY(), (float) voxel.getMax().getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINES);
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMax().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMax().getY(), (float) voxel.getMax().getZ());
        g.glEnd();
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);
    }
    
}
