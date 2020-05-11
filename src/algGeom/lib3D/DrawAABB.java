package algGeom.lib3D;

import Util.Draw;
import javax.media.opengl.*;

public class DrawAABB extends Draw {

    AABB box;

    public DrawAABB(AABB box) {
        this.box = box;
    }

    public AABB getBox() {
        return box;
    }

    @Override
    public void drawObject(GL g) {
        //face 0
        g.glBegin(GL.GL_LINE_STRIP);
        g.glVertex3d(box.min.getX(), box.min.getY(), box.min.getZ());
        g.glVertex3d(box.min.getX(), box.min.getY(), box.max.getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINE_STRIP);
        g.glVertex3d(box.min.getX(), box.min.getY(), box.max.getZ());
        g.glVertex3d(box.max.getX(), box.min.getY(), box.max.getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINE_STRIP);
        g.glVertex3d(box.max.getX(), box.min.getY(), box.max.getZ());
        g.glVertex3d(box.max.getX(), box.min.getY(), box.min.getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINE_STRIP);
        g.glVertex3d(box.max.getX(), box.min.getY(), box.min.getZ());
        g.glVertex3d(box.min.getX(), box.min.getY(), box.min.getZ());
        
        //face 1        
        g.glEnd();
        g.glBegin(GL.GL_LINE_STRIP);
        g.glVertex3d(box.min.getX(), box.min.getY(), box.min.getZ());
        g.glVertex3d(box.min.getX(), box.max.getY(), box.min.getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINE_STRIP);
        g.glVertex3d(box.min.getX(), box.max.getY(), box.min.getZ());
        g.glVertex3d(box.min.getX(), box.max.getY(), box.max.getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINE_STRIP);
        g.glVertex3d(box.min.getX(), box.max.getY(), box.max.getZ());
        g.glVertex3d(box.min.getX(), box.min.getY(), box.max.getZ());
        g.glEnd();
        
        //face 2
        g.glBegin(GL.GL_LINE_STRIP);
        g.glVertex3d(box.min.getX(), box.max.getY(), box.min.getZ());
        g.glVertex3d(box.max.getX(), box.max.getY(), box.min.getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINE_STRIP);
        g.glVertex3d(box.max.getX(), box.max.getY(), box.min.getZ());
        g.glVertex3d(box.max.getX(), box.min.getY(), box.min.getZ());
        g.glEnd();
        
        //face 3
        g.glBegin(GL.GL_LINE_STRIP);
        g.glVertex3d(box.max.getX(), box.max.getY(), box.min.getZ());
        g.glVertex3d(box.max.getX(), box.max.getY(), box.max.getZ());
        g.glEnd();
        g.glBegin(GL.GL_LINE_STRIP);
        g.glVertex3d(box.max.getX(), box.max.getY(), box.max.getZ());
        g.glVertex3d(box.max.getX(), box.min.getY(), box.max.getZ());
        g.glEnd();
        
        //face 4 and 5
        g.glBegin(GL.GL_LINE_STRIP);
        g.glVertex3d(box.min.getX(), box.max.getY(), box.max.getZ());
        g.glVertex3d(box.max.getX(), box.max.getY(), box.max.getZ());
        g.glEnd();
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);
    }
    
    
    public void drawObjectC(GL g, float R, float G, float B, float A) {
        g.glColor4f(R, G, B, A);
        drawObject(g);
    }

}