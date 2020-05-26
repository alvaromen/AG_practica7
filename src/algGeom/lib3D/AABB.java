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
    
    public boolean testAABBAABB(AABB other){
        if((Math.abs(getCenter().getX() - other.getCenter().getX())) > (getExtent().getX() + other.getExtent().getX()))
            return false;
        if((Math.abs(getCenter().getY() - other.getCenter().getY())) > (getExtent().getY() + other.getExtent().getY()))
            return false;
        if((Math.abs(getCenter().getZ() - other.getCenter().getZ())) > (getExtent().getZ() + other.getExtent().getZ()))
            return false;
        
        return true;
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
    
    @Override
    public String toString(){
        return min + ", " + max; 
    }
}
