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
public class DrawVoxelBlack extends Draw {

    Voxel voxel;
    
    public DrawVoxelBlack(Voxel voxel){
        this.voxel = voxel;
    }
    
    public Voxel getVoxel(){
        return voxel;
    }
    
    @Override
    public void drawObject(GL g) {
        //face 0
        g.glBegin(GL.GL_POLYGON);
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMin().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMin().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMax().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMax().getY(), (float) voxel.getMin().getZ());
        g.glEnd();
        
        //face 1
        g.glBegin(GL.GL_POLYGON);
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMin().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMin().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMin().getY(), (float) voxel.getMax().getZ());
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMin().getY(), (float) voxel.getMax().getZ());
        g.glEnd();
        
        //face 2
        g.glBegin(GL.GL_POLYGON);
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMin().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMin().getY(), (float) voxel.getMax().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMax().getY(), (float) voxel.getMax().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMax().getY(), (float) voxel.getMin().getZ());
        g.glEnd();
        
        //face 3
        g.glBegin(GL.GL_POLYGON);
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMin().getY(), (float) voxel.getMax().getZ());
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMin().getY(), (float) voxel.getMax().getZ());
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMax().getY(), (float) voxel.getMax().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMax().getY(), (float) voxel.getMax().getZ());
        g.glEnd();
        
        //face 4
        g.glBegin(GL.GL_POLYGON);
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMin().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMin().getY(), (float) voxel.getMax().getZ());
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMax().getY(), (float) voxel.getMax().getZ());
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMax().getY(), (float) voxel.getMin().getZ());
        g.glEnd();
        
        //face 5
        g.glBegin(GL.GL_POLYGON);
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMax().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMax().getY(), (float) voxel.getMin().getZ());
        g.glVertex3f((float) voxel.getMax().getX(), (float) voxel.getMax().getY(), (float) voxel.getMax().getZ());
        g.glVertex3f((float) voxel.getMin().getX(), (float) voxel.getMax().getY(), (float) voxel.getMax().getZ());
        g.glEnd();
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);
    }
    
}
