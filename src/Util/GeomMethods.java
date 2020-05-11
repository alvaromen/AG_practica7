package Util;

import algGeom.lib2D.Point;
import algGeom.lib2D.PointCloud;
import algGeom.lib2D.Polygon;
import java.util.ArrayList;


public class GeomMethods {
    
    
    public static boolean get4Corners(ArrayList< Point > a, PointCloud cloud)
    {
       if(cloud.size() < 4) return false;
       double X_MAX, X_MIN, Y_MAX, Y_MIN;
       X_MAX = Y_MAX = Double.MIN_VALUE;
       X_MIN = Y_MIN = Double.MAX_VALUE;
       
       Point p;
       for(int i = 0; i < 4; i++) a.add(new Point());
       for (int i = 0; i < cloud.size(); i++)
       {
           p = cloud.getPoint(i);
           if(p.getX() > X_MAX){ a.set(0, p); X_MAX = p.getX(); }
           if(p.getX() < X_MIN){ a.set(2, p); X_MIN = p.getX(); }
           
           if(p.getY() > Y_MAX){ a.set(1, p); Y_MAX = p.getY(); }
           if(p.getY() < Y_MIN){ a.set(3, p); Y_MIN = p.getY(); }
       }
               
       return true;
    }

    public static Polygon create4Polygon(Point a, Point b, Point c, Point d) {
        Polygon pol = new Polygon();

        Point[] point = new Point[4];
        point[0] = a;
        point[1] = b;
        point[2] = c;
        point[3] = d;

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (i != j) {
                    int kC = 0, kI = -1;
                    for (int k = 0; k < 4; ++k) {
                        if (i != k && j != k && point[k].left(point[i], point[j])) {
                            ++kC;
                            kI = k;
                        }
                    }
                    if (kC == 1) {
                        pol.add(point[j]);
                        pol.add(point[kI]);
                        pol.add(point[i]);
                        pol.add(point[6 - i - j - kI]);
                        return pol;
                    }
                }
            }
        }

        return pol;
    }
}
