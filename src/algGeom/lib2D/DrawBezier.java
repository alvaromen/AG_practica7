package algGeom.lib2D;

import Util.Draw;
import javax.media.opengl.*;

public class DrawBezier extends Draw {

    Bezier vb;

    public DrawBezier(Bezier b) {
        vb = b;
    }

    int factorial(int n) {
        if (n <= 1) {
            return (1);
        } else {
            n = n * factorial(n - 1);
        }
        return n;
    }

    float binomial_coff(int n, int k) {
        float ans;
        ans = (float) (factorial(n) / (factorial(k) * factorial(n - k)));
        return ans;
    }

  
    Point drawBezierGeneralized(double t) {
        Point P = new Point();
        //P.asignax(0); P.asignay(0);   
        int clicks = vb.size();
        for (int i = 0; i < clicks; i++) {
            double xx = P.getX() + binomial_coff((clicks - 1), i) * Math.pow(t, i) * Math.pow((1 - t), (clicks - 1 - i)) * vb.getAt(i).getX();
            P.setX(xx);
            double yy = P.getY() + binomial_coff((clicks - 1), i) * Math.pow(t, i) * Math.pow((1 - t), (clicks - 1 - i)) * vb.getAt(i).getY();
            P.setY(yy);
        }
       
        return P;
    }

    void drawLine(GL g, Point p1, Point p2) {
        Point pp1 = new Point(convCoordX(p1.getX()), convCoordY(p1.getY()));
        Point pp2 = new Point(convCoordX(p2.getX()), convCoordY(p2.getY()));
        g.glBegin(GL.GL_LINES);
        g.glVertex2d(pp1.x, pp1.y);
        g.glVertex2d(pp2.x, pp2.y);
        g.glEnd();
        g.glFlush();
    }

    @Override
    public void drawObject(GL g) {

       float t = 0;
        Point p1, p2;
        
        while (t < 1) {

            p1 = new Point(drawBezierGeneralized(t));
            
            t += 0.02;
            
            p2 = new Point(drawBezierGeneralized(t));
            
            drawLine(g,p1,p2);           
        }
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {

        g.glColor3f(R, G, B);
        drawObject(g);
    }

}
