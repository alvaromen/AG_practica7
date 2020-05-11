//package algGeom.lib3D;
//
//import Util.BasicGeom;
//
//public class AABB {
//
//    Vect3d min; //menor x,y,z
//    Vect3d max; //max x, y, z
//
//    public void setMin(Vect3d min) {
//        this.min = min;
//    }
//
//    public void setMax(Vect3d max) {
//        this.max = max;
//    }
//
//    public AABB(Vect3d min, Vect3d max) {
//        this.min = min;
//        this.max = max;
//    }
//
//    /**
//     * get the extension vector 
//     */
//    public Vect3d getExtent (){
//      double x = (max.x + min.x)/2,
//                y = (max.y + min.y)/2,
//                z = (max.z + min.z)/2;
//      return new Vect3d(max.x - x, max.y - y, max.z - z);
//    }
//    
//    /**
//     * get the cube center  
//     */
//    public Vect3d getCenter (){
//        double x = (max.x + min.x)/2,
//                y = (max.y + min.y)/2,
//                z = (max.z + min.z)/2;
//        return new Vect3d(x, y, z);
//    }
//
//    public Vect3d getMin() {
//        return min;
//    }
//
//    public Vect3d getMax() {
//        return max;
//    }
//
//    /*======================== X-tests ========================*/
//    private boolean AXISTEST_X01(double a, double b, double fa, double fb, Vect3d v0, Vect3d v2) {
//
//        double p0 = a * v0.getY() - b * v0.getZ();
//        double p2 = a * v2.getY() - b * v2.getZ();
//
//        double min = 0, max = 0;
//
//        if (p0 < p2) {
//            min = p0;
//            max = p2;
//        } else {
//            min = p2;
//            max = p0;
//        }
//
//        double rad = fa * getExtent().getY() + fb * getExtent().getZ();
//
//        if (min > rad || max < -rad) {
//            return false;
//        }
//
//        return true;
//    }
//
//    private boolean AXISTEST_X2(double a, double b, double fa, double fb, Vect3d v0, Vect3d v1) {
//        double p0 = a * v0.getY() - b * v0.getZ();
//        double p1 = a * v1.getY() - b * v1.getZ();
//
//        double min = 0, max = 0;
//
//        if (p0 < p1) {
//            min = p0;
//            max = p1;
//        } else {
//            min = p1;
//            max = p0;
//        }
//
//        double rad = fa * getExtent().getY() + fb * getExtent().getZ();
//
//        if (min > rad || max < -rad) {
//            return false;
//        }
//
//        return true;
//    }
//
//    /*======================== Y-tests ========================*/
//    private boolean AXISTEST_Y02(double a, double b, double fa, double fb, Vect3d v0, Vect3d v2) {
//
//        double p0 = -a * v0.getX() + b * v0.getZ();
//        double p2 = -a * v2.getX() + b * v2.getZ();
//
//        double min = 0, max = 0;
//
//        if (p0 < p2) {
//            min = p0;
//            max = p2;
//        } else {
//            min = p2;
//            max = p0;
//        }
//
//        double rad = fa * getExtent().getX() + fb * getExtent().getZ();
//
//        if (min > rad || max < -rad) {
//            return false;
//        }
//
//        return true;
//    }
//
//    private boolean AXISTEST_Y1(double a, double b, double fa, double fb, Vect3d v0, Vect3d v1) {
//
//        double p0 = -a * v0.getX() + b * v0.getZ();
//        double p1 = -a * v1.getX() + b * v1.getZ();
//
//        double min = 0, max = 0;
//
//        if (p0 < p1) {
//            min = p0;
//            max = p1;
//        } else {
//            min = p1;
//            max = p0;
//        }
//
//        double rad = fa * getExtent().getX() + fb * this.getExtent().getZ();
//
//        if (min > rad || max < -rad) {
//            return false;
//        }
//
//        return true;
//    }
//
//    /*======================== Z-tests ========================*/
//
//    private boolean AXISTEST_Z12(double a, double b, double fa, double fb, Vect3d v1, Vect3d v2) {
//
//        double p1 = a * v1.getX() - b * v1.getY();
//        double p2 = a * v2.getX() - b * v2.getY();
//
//        double min = 0, max = 0;
//
//        if (p1 < p2) {
//            min = p1;
//            max = p2;
//        } else {
//            min = p2;
//            max = p1;
//        }
//
//        double rad = fa * getExtent().getX() + fb * getExtent().getY();
//
//        if (min > rad || max < -rad) {
//            return false;
//        }
//
//        return true;
//    }
//
//    private boolean AXISTEST_Z0(double a, double b, double fa, double fb, Vect3d v0, Vect3d v1) {
//
//        double p0 = a * v0.getX() - b * v0.getY();
//        double p1 = a * v1.getX() - b * v1.getY();
//
//        double min = 0, max = 0;
//
//        if (p0 < p1) {
//            min = p0;
//            max = p1;
//        } else {
//            min = p1;
//            max = p0;
//        }
//
//        double rad = fa * getExtent().getX() + fb * getExtent().getY();
//
//        if (min > rad || max < -rad) {
//            return false;
//        }
//
//        return true;
//    }
//
//    private void FINDMINMAX(double x0, double x1, double x2, double min, double max) {
//
//        min = max = x0;
//
//        if (x1 < min) {
//            min = x1;
//        }
//        if (x1 > max) {
//            max = x1;
//        }
//        if (x2 < min) {
//            min = x2;
//        }
//        if (x2 > max) {
//            max = x2;
//        }
//    }
//
//    private boolean planeBoxOverlap(Vect3d normal, double d, Vect3d maxbox) {
//
//        int q;
//        Vect3d vmin = new Vect3d();
//        Vect3d vmax = new Vect3d();
//
//        if (normal.getX() > algGeom.lib2D.BasicGeom.ZERO) {
//            vmin.setX(-maxbox.getX());
//            vmax.setX(maxbox.getX());
//        } else {
//            vmin.setX(maxbox.getX());
//            vmax.setX(-maxbox.getX());
//        }
//
//        if (normal.getY() > algGeom.lib2D.BasicGeom.ZERO) {
//            vmin.setY(-maxbox.getY());
//            vmax.setY(maxbox.getY());
//        } else {
//            vmin.setY(maxbox.getY());
//            vmax.setY(-maxbox.getY());
//        }
//
//        if (normal.getZ() > algGeom.lib2D.BasicGeom.ZERO) {
//            vmin.setZ(-maxbox.getZ());
//            vmax.setZ(maxbox.getZ());
//        } else {
//            vmin.setZ(maxbox.getZ());
//            vmax.setZ(-maxbox.getZ());
//        }
//
//        if (normal.dot(vmin) + d > algGeom.lib2D.BasicGeom.ZERO) {
//            return false;
//        }
//        if (normal.dot(vmax) + d >= 0.0) {
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean aabb_tri(Triangle3d t) {
//
//        /*    use separating axis theorem to test overlap between triangle and box */
// /*    need to test for overlap in these directions: */
// /*    1) the {x,y,z}-directions (actually, since we use the AABB of the triangle */
// /*       we do not even need to test these) */
// /*    2) normal of the triangle */
// /*    3) crossproduct(edge from tri, {x,y,z}-directin) */
// /*       this gives 3x3=9 more tests */
//        Vect3d v0, v1, v2;
//        double minimum = 0, maximum = 0, d, p0, p1, p2, rad;
//        double fex = 0, fey = 0, fez = 0;
//        Vect3d e0, e1, e2;
//
//        /* This is the fastest branch on Sun */
// /* move everything so that the boxcenter is in (0,0,0) */
//        v0 = t.getA().sub(this.getCenter());
//        v1 = t.getB().sub(this.getCenter());
//        v2 = t.getC().sub(this.getCenter());
//
//
//        /* compute triangle edges */
//        e0 = v1.sub(v0);
//        /* tri edge 0 */
//        e1 = v2.sub(v1);
//        /* tri edge 1 */
//        e2 = v0.sub(v2);
//        /* tri edge 2 */
//        
//        Vect3d normal = e0.xProduct(e1);
//
// /* Bullet 3:  */
// /*  test the 9 tests first (this was faster) */
//        fex = Math.abs(e0.getX());
//        fey = Math.abs(e0.getY());
//        fez = Math.abs(e0.getZ());
//        if (!AXISTEST_X01(e0.getZ(), e0.getY(), fez, fey, v0, v2)) {
//            return false;
//        }
////        System.out.println("AXISTEST_X01 pasado");
//        if (!AXISTEST_Y02(e0.getZ(), e0.getX(), fez, fex, v0, v2)) {
//            return false;
//        }
////        System.out.println("AXISTEST_Y02 pasado");
//        if (!AXISTEST_Z12(e0.getY(), e0.getX(), fey, fex, v0, v2)) {
//            return false;
//        }
////        System.out.println("AXISTEST_Z12 pasado");
//
//        fex = Math.abs(e1.getX());
//        fey = Math.abs(e1.getY());
//        fez = Math.abs(e1.getZ());
//        if (!AXISTEST_X01(e1.getZ(), e1.getY(), fez, fey, v0, v2)) {
//            return false;
//        }
////        System.out.println("AXISTEST_X01 pasado");
//        if (!AXISTEST_Y02(e1.getZ(), e1.getX(), fez, fex, v0, v2)) {
//            return false;
//        }
////        System.out.println("AXISTEST_Y02 pasado");
//        if (!AXISTEST_Z0(e1.getY(), e1.getX(), fey, fex, v0, v1)) {
//            return false;
//        }
////        System.out.println("AXISTEST_Z0 pasado");
//
//        fex = Math.abs(e2.getX());
//        fey = Math.abs(e2.getY());
//        fez = Math.abs(e2.getZ());
//        if (!AXISTEST_X2(e2.getZ(), e2.getY(), fez, fey, v0, v1)) {
//            return false;
//        }
////        System.out.println("AXISTEST_X2 pasado");
//        if (!AXISTEST_Y1(e2.getZ(), e2.getX(), fez, fex, v0, v1)) {
//            return false;
//        }
////        System.out.println("AXISTEST_Y1 pasado");
//        if (!AXISTEST_Z12(e2.getY(), e2.getX(), fey, fex, v1, v2)) {
//            return false;
//        }
////        System.out.println("AXISTEST_Z12 pasado");
//
//        /* Bullet 1: */
// /*  first test overlap in the {x,y,z}-directions */
// /*  find min, max of the triangle each direction, and test for overlap in */
// /*  that direction -- this is equivalent to testing a minimal AABB around */
// /*  the triangle against the AABB */
//
// /* test in X-direction */
//        FINDMINMAX(v0.getX(), v1.getX(), v2.getX(), minimum, maximum);
//        if (minimum > getExtent().getX() || maximum < -getExtent().getX()) {
//            return false;
//        }
//
//        /* test in Y-direction */
//        FINDMINMAX(v0.getY(), v1.getY(), v2.getY(), minimum, maximum);
//        if (minimum > getExtent().getY() || maximum < -getExtent().getY()) {
//            return false;
//        }
//
//        /* test in Z-direction */
//        FINDMINMAX(v0.getZ(), v1.getZ(), v2.getZ(), minimum, maximum);
//        if (minimum > getExtent().getZ() || maximum < -getExtent().getZ()) {
//            return false;
//        }
//
//        /* Bullet 2: */
// /*  test if the box intersects the plane of the triangle */
// /*  compute plane equation of triangle: normal*x+d=0 */
//        d = -((float) normal.dot(v0));
//        /* plane eq: normal.x+d=0 */
//        if (!planeBoxOverlap(normal, d, getExtent())) {
//            return false;
//        }
//
//        return true;
//    }
//}
package algGeom.lib3D;

import com.sun.javafx.geom.Vec3d;

enum PlaneTrianglePosition {
    INTERSECT, NON_INTERSECT, INSIDE
};

public class AABB {

    Vect3d min; //menor x,y,z
    Vect3d max; //max x, y, z

    public void setMin(Vect3d min) {
        this.min = min;
    }

    public void setMax(Vect3d max) {
        this.max = max;
    }

    public AABB(Vect3d min, Vect3d max) {
        this.min = min;
        this.max = max;
    }

    public Vect3d getExtent() {
        Vect3d a = new Vect3d((max.x - min.x) / 2.0d, (max.y - min.y) / 2.0d, (max.z - min.z) / 2.0d);
        return a;
    }

    public Vect3d getCenter() {
        return min.add(getExtent());
    }

    public Vect3d getMin() {
        return min;
    }

    public Vect3d getMax() {
        return max;
    }

    public boolean aabb_tri(Triangle3d tri) {
        // use separating axis theorem to test overlap between triangle and box
        // need to test for overlap in these directions:
        //
        // 1) the {x,y,z}-directions (actually, since we use the AABB of the
        // triangle
        // we do not even need to test these)
        // 2) normal of the triangle
        // 3) crossproduct(edge from tri, {x,y,z}-directin)
        // this gives 3x3=9 more tests
        Vect3d v0, v1, v2;
        Vect3d normal, e0, e1, e2, f;

        // move everything so that the boxcenter is in (0,0,0)
        Vect3d boxCenter = getCenter();
        v0 = tri.a.sub(boxCenter);
        v1 = tri.b.sub(boxCenter);
        v2 = tri.c.sub(boxCenter);

        // compute triangle edges
        e0 = v1.sub(v0);
        e1 = v2.sub(v1);
        e2 = v0.sub(v2);

        // test the 9 tests first (this was faster)
        Vect3d extent = getExtent();
        f = new Vect3d(Math.abs(e0.getX()), Math.abs(e0.getY()), Math.abs(e0.getZ()));
        if (testAxis(e0.z, -e0.y, f.z, f.y, v0.y, v0.z, v2.y, v2.z, extent.y,
                extent.z)) {
            return false;
        }
        if (testAxis(-e0.z, e0.x, f.z, f.x, v0.x, v0.z, v2.x, v2.z, extent.x,
                extent.z)) {
            return false;
        }
        if (testAxis(e0.y, -e0.x, f.y, f.x, v1.x, v1.y, v2.x, v2.y, extent.x,
                extent.y)) {
            return false;
        }

        f = new Vect3d(Math.abs(e1.getX()), Math.abs(e1.getY()), Math.abs(e1.getZ()));
        if (testAxis(e1.z, -e1.y, f.z, f.y, v0.y, v0.z, v2.y, v2.z, extent.y,
                extent.z)) {
            return false;
        }
        if (testAxis(-e1.z, e1.x, f.z, f.x, v0.x, v0.z, v2.x, v2.z, extent.x,
                extent.z)) {
            return false;
        }
        if (testAxis(e1.y, -e1.x, f.y, f.x, v0.x, v0.y, v1.x, v1.y, extent.x,
                extent.y)) {
            return false;
        }

        f = new Vect3d(Math.abs(e2.getX()), Math.abs(e2.getY()), Math.abs(e2.getZ()));
        if (testAxis(e2.z, -e2.y, f.z, f.y, v0.y, v0.z, v1.y, v1.z, extent.y,
                extent.z)) {
            return false;
        }
        if (testAxis(-e2.z, e2.x, f.z, f.x, v0.x, v0.z, v1.x, v1.z, extent.x,
                extent.z)) {
            return false;
        }
        if (testAxis(e2.y, -e2.x, f.y, f.x, v1.x, v1.y, v2.x, v2.y, extent.x,
                extent.y)) {
            return false;
        }

        // first test overlap in the {x,y,z}-directions
        // find min, max of the triangle each direction, and test for overlap in
        // that direction -- this is equivalent to testing a minimal AABB around
        // the triangle against the AABB
        // test in X-direction
        double mini = 0, maxi = 0;
        findMinMax(v0.x, v1.x, v2.x, mini, maxi);

        if (mini > extent.x
                || maxi < -extent.x) {
            return false;
        }

        // test in Y-direction
        findMinMax(v0.y, v1.y, v2.y, mini, maxi);
        if (mini > extent.y
                || maxi < -extent.y) {
            return false;
        }

        // test in Z-direction
        findMinMax(v0.z, v1.z, v2.z, mini, maxi);
        if (mini > extent.z
                || maxi < -extent.z) {
            return false;
        }

        // test if the box intersects the plane of the triangle
        // compute plane equation of triangle: normal*x+d=0
        normal = e0.xProduct(e1);
        double d = -normal.dot(v0);
        if (!planeBoxOverlap(normal, d, extent)) {
            return false;
        }

        return true;
    }

    private PlaneTrianglePosition intersect(Triangle3d t) {
        // test in X-direction
        double mini = 0, maxi = 0;
        findMinMax(t.a.x, t.b.x, t.c.x, mini, maxi);
        if (mini < min.x
                || maxi > max.x) {
            return PlaneTrianglePosition.INTERSECT;
        }
        findMinMax(t.a.y, t.b.y, t.c.y, mini, maxi);
        if (mini < min.y
                || maxi > max.y) {
            return PlaneTrianglePosition.INTERSECT;
        }
        findMinMax(t.a.z, t.b.z, t.c.z, mini, maxi);
        if (mini < min.z
                || maxi > max.z) {
            return PlaneTrianglePosition.INTERSECT;
        }

        return PlaneTrianglePosition.INSIDE;
    }

    private boolean planeBoxOverlap(Vect3d normal, double d, Vect3d maxbox) {
        Vect3d vmin = new Vect3d();
        Vect3d vmax = new Vect3d();

        if (normal.x > 0.0f) {
            vmin.x = -maxbox.x;
            vmax.x = maxbox.x;
        } else {
            vmin.x = maxbox.x;
            vmax.x = -maxbox.x;
        }

        if (normal.y > 0.0f) {
            vmin.y = -maxbox.y;
            vmax.y = maxbox.y;
        } else {
            vmin.y = maxbox.y;
            vmax.y = -maxbox.y;
        }

        if (normal.z > 0.0f) {
            vmin.z = -maxbox.z;
            vmax.z = maxbox.z;
        } else {
            vmin.z = maxbox.z;
            vmax.z = -maxbox.z;
        }
        if (normal.dot(vmin) + d > 0.0f) {
            return false;
        }
        if (normal.dot(vmax) + d >= 0.0f) {
            return true;
        }
        return false;
    }

    private boolean testAxis(double a, double b, double fa, double fb, double va, double vb, double wa, double wb, double ea, double eb) {
        double p0 = a * va  + b * vb;
        double p2 = a * wa + b * wb;
        double min, max;
        if (p0 < p2) {
            min = p0;
            max = p2;
        } else {
            min = p2;
            max = p0;
        }
        double rad = fa * ea + fb * eb;
        return (min > rad || max < -rad);
    }

    private void findMinMax(double x0, double x1, double x2, double min, double max) {

        min = max = x0;

        if (x1 < min) {
            min = x1;
        }
        if (x1 > max) {
            max = x1;
        }
        if (x2 < min) {
            min = x2;
        }
        if (x2 > max) {
            max = x2;
        }
    }

}
