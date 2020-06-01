package algGeom.lib3D;

import Util.BasicGeom;

enum PointPosition {
    POSITIVE, NEGATIVE, COPLANAR
};

enum PointTrianglePosition {
    PARALELL, COLLINEAR, INTERSECTS, NO_INTERSECTS
};

public class Triangle3d {

    /**
     * a triangle is defined by three points
     */
    protected Vect3d a, b, c;

    /**
     * default constructor with values (0,0,0)
     */
    public Triangle3d() {
        a = new Vect3d();
        b = new Vect3d();
        c = new Vect3d();
    }

    /**
     * constructor from their coordinates
     */
    public Triangle3d(double ax, double ay, double az,
            double bx, double by, double bz,
            double cx, double cy, double cz) {
        a = new Vect3d(ax, ay, az);
        b = new Vect3d(bx, by, bz);
        c = new Vect3d(cx, cy, cz);
    }

    /**
     * copy constructor
     */
    public Triangle3d(Triangle3d t) {
        a = new Vect3d(t.a);
        b = new Vect3d(t.b);
        c = new Vect3d(t.c);
    }

    /**
     * constructor given three points
     */
    public Triangle3d(Vect3d va, Vect3d vb, Vect3d vc) {
        a = new Vect3d(va);
        b = new Vect3d(vb);
        c = new Vect3d(vc);
    }

    /**
     * set new values
     */
    public void set(Vect3d va, Vect3d vb, Vect3d vc) {
        a = va;
        b = vb;
        c = vc;
    }

    /**
     * get a
     */
    public Vect3d getA() {
        return a;
    }

    /**
     * get b
     */
    public Vect3d getB() {
        return b;
    }

    /**
     * get c
     */
    public Vect3d getC() {
        return c;
    }

    /**
     * get the vertex i
     */
    public Vect3d getPoint(int i) {
        return (i == 0 ? a : (i == 1 ? b : c));
    }

    /**
     * get the vertex the set of vertices
     */
    public Vect3d[] getPoints() {
        Vect3d[] vt = {a, b, c};
        return vt;
    }

    /**
     * get a copy
     */
    public Triangle3d copy() {
        return new Triangle3d(a, b, c);
    }

    /**
     * set new value to a
     */
    public void setA(Vect3d pa) {
        a = pa;
    }

    /**
     * set new value to b
     */
    public void setB(Vect3d pb) {
        b = pb;
    }

    /**
     * set new value to c
     */
    public void setC(Vect3d pc) {
        c = pc;
    }

    /**
     * get the normal vector
     */
    public Vect3d normal() {
        Vect3d v1 = new Vect3d(b.sub(a));
        Vect3d v2 = new Vect3d(c.sub(a));
        Vect3d n = new Vect3d(v1.xProduct(v2));
        double longi = n.module();

        return (n.scalarMul(1.0 / longi));
    }

    /**
     * show the vertices
     */
    public void out() {
        System.out.println("Triangle3d: (" + a + "-" + b + "-" + c + ")");
    }

    public double area() {
        Vect3d ab = b.sub(a);
        Vect3d ac = c.sub(a);
        return (ab.xProduct(ac)).module()/2;
    }

    public PointPosition classify(Vect3d p) {
        Vect3d v = new Vect3d(p.sub(a));
        double len = v.module();
        if (BasicGeom.equal(len, 0.0)) {
            return PointPosition.COPLANAR;
        }
        v = v.scalarMul(1.0 / len);
        double d = v.dot(normal());
        if (d > BasicGeom.ZERO) {
            return PointPosition.POSITIVE;
        } else if (d < -BasicGeom.ZERO) {
            return PointPosition.NEGATIVE;
        } else {
            return PointPosition.COPLANAR;
        }
    }

    public AABB getAABB(){
        double xMin = a.getX();
        double xMax = a.getX();
        double yMin = a.getY();
        double yMax = a.getY();
        double zMin = a.getZ();
        double zMax = a.getZ();

        if(b.getX() < xMin){
            if(c.getX() < b.getX()){
                xMin = c.getX();
            } else xMin = b.getX();
        }
        if(xMax < b.getX()){
            if(b.getX() < c.getX()){
                xMax = c.getX();
            } else xMax = b.getX();
        }

        if(b.getY() < yMin){
            if(c.getY() < b.getY()){
                yMin = c.getY();
            } else yMin = b.getY();
        }
        if(yMax < b.getY()){
            if(b.getY() < c.getY()){
                yMax = c.getY();
            } else yMax = b.getY();
        }

        if(b.getZ() < zMin){
            if(c.getZ() < b.getZ()){
                zMin = c.getZ();
            } else zMin = b.getZ();
        }
        if(zMax < b.getZ()){
            if(b.getZ() < c.getZ()){
                zMax = c.getZ();
            } else zMax = b.getZ();
        }

        return new AABB(new Vect3d(xMin, yMin, zMin), new Vect3d(xMax, yMax, zMax));
    }

    public boolean ray_tri(Ray3d r, Vect3d p) {
        Vect3d dirVector = r.dest.sub(r.orig);
        Vect3d vertex0 = a;
        Vect3d vertex1 = b;
        Vect3d vertex2 = c;
        Vect3d edge1 = new Vect3d(vertex1.sub(vertex0));
        Vect3d edge2 = new Vect3d(vertex2.sub(vertex0));
        Vect3d h = new Vect3d(dirVector.xProduct(edge2));
        Vect3d s = new Vect3d(r.orig.sub(vertex0));
        Vect3d q = new Vect3d(s.xProduct(edge1));
        double a, f, u, v;

        a = edge1.dot(h);
        if (BasicGeom.equal(a, BasicGeom.ZERO)) {
            return false;    // This ray is parallel to this triangle.
        }
        f = 1.0 / a;
        u = f * (s.dot(h));
        if (u < 0.0 || u > 1.0) {
            return false;
        }

        v = f * dirVector.dot(q);
        if (v < 0.0 || u + v > 1.0) {
            return false;
        }
        // At this stage we can compute t to find out where the intersection point is on the line.
        double m = f * edge2.dot(q);
        if (m > BasicGeom.ZERO) // ray intersection
        {
            //"m" es nuestra t en la línea. Falta sacar el punto de intersección con esa t.

            Vect3d result = dirVector.scalarMul(m).add(r.orig);
            p.setX(result.x);p.setY(result.y);p.setZ(result.z);
            return true;

        } else // This means that there is a line intersection but not a ray intersection.
        {
            return false;
        }
    }

    public boolean intersectionTriTri(Triangle3d other){
//        double[] V0 = a.getCoordinates();
//        double[] V1 = (a.add(b)).getCoordinates();
//        double[] V2 = (a.add(c)).getCoordinates();
//        double[] U0 = other.a.getCoordinates();
//        double[] U1 = other.a.add(other.b).getCoordinates();
//        double[] U2 = other.a.add(other.c).getCoordinates();
//        return NoDivTriTriIsect(V0, V1, V2, U0, U1, U2);
        return intersects(a, b, c, other.a, other.b, other.c);
    }
    
    int vertexSign(Vect3d a, Vect3d b, Vect3d c, Vect3d d)
    {
        Vect3d ba = b.sub(a);

        Vect3d ca = c.sub(a);

        Vect3d da = d.sub(a);

        double det = ba.getX() * ca.getY() * da.getZ() + ba.getY() * ca.getZ() * da.getX() + ca.getX() * da.getY() * ba.getZ() -
        da.getX() * ca.getY() * ba.getZ() - ca.getX() * ba.getY() * da.getZ() - da.getY() * ca.getZ() * ba.getX();

        return (det < 0 ? -1 :(det > 0 ? + 1 : 0));
    }

    boolean intersects_segment(Vect3d p1, Vect3d p2, Vect3d p3, Vect3d a, Vect3d b)
    {
        int s = vertexSign (a, p3, p2, p1);

        return (s!= 0 &&
            s == vertexSign (b, p1, p2, p3) &&
            s == vertexSign (p1, a, b, p3) &&
            s == vertexSign (p2, p3, b, a) &&
            s == vertexSign (a, p1, b, p2));
    }


    // v es el primer triángulo y p es el segundo triángulo.

    boolean intersects(Vect3d v1, Vect3d v2, Vect3d v3, Vect3d p1, Vect3d p2, Vect3d p3)
    {
        return (intersects_segment (v1,v2,v3,p1, p2) ||
            intersects_segment (v1,v2,v3,p2,p3) ||
            intersects_segment (v1,v2,v3,p3,p1) ||
            intersects_segment (p1,p2,p3,v1,v2 ) ||
            intersects_segment (p1,p2,p3,v2,v3 ) ||
            intersects_segment (p1,p2,p3,v3,v1 ));
    }


    private static final boolean USE_EPSILON_TEST = true;
    private static final double EPSILON = 0.000001;

    /* some macros */
    private double[] CROSS(double[] v1, double[] v2){
        double[] dest = new double[3];
        dest[0] = v1[1]*v2[2]-v1[2]*v2[1];
        dest[1] = v1[2]*v2[0]-v1[0]*v2[2];
        dest[2] = v1[0]*v2[1]-v1[1]*v2[0];
        return dest;
    }

    private double DOT(double[] v1, double[] v2){
        return v1[0]*v2[0]+v1[1]*v2[1]+v1[2]*v2[2];
    }

    private double[] SUB(double[] v1, double[] v2){
        double[] dest = new double[3];
        dest[0]=v1[0]-v2[0];
        dest[1]=v1[1]-v2[1];
        dest[2]=v1[2]-v2[2];
        return dest;
    }

    /* this edge to edge test is based on Franlin Antonio's gem:
       "Faster Line Segment Intersection", in Graphics Gems III,
       pp. 199-202 */
    private boolean EDGE_EDGE_TEST(double[] V0, double[] U0, double[] U1, double Ax, double Ay){
        double Bx=U0[0]-U1[0];
        double By=U0[1]-U1[1];
        double Cx=V0[0]-U0[0];
        double Cy=V0[1]-U0[1];
        double f=Ay*Bx-Ax*By;
        double d=By*Cx-Bx*Cy;
        if((f>0 && d>=0 && d<=f) || (f<0 && d<=0 && d>=f)){
            double e=Ax*Cy-Ay*Cx;
            if(f>0)
            {
              if(e>=0 && e<=f) return true;
            }
            else
            {
              if(e<=0 && e>=f) return true;
            }
        }
        return false;
    }

    private boolean EDGE_AGAINST_TRI_EDGES(double[] V0, double[] V1, double[] U0, double[] U1, double[] U2){
        double Ax,Ay,Bx,By,Cx,Cy,e,d,f;
        Ax=V1[0]-V0[0];
        Ay=V1[1]-V0[1];
        /* test edge U0,U1 against V0,V1 */
        if(EDGE_EDGE_TEST(V0,U0,U1,Ax,Ay)){
            return true;
        }
        /* test edge U1,U2 against V0,V1 */
        if(EDGE_EDGE_TEST(V0,U1,U2,Ax,Ay)){
            return true;
        }
        /* test edge U2,U1 against V0,V1 */
        if(EDGE_EDGE_TEST(V0,U2,U0,Ax,Ay)){
            return true;
        }

        return false;
    }

    private boolean POINT_IN_TRI(double[] V0, double[] U0, double[] U1, double[] U2){
        double aa,bb,cc,d0,d1,d2;
        /* is T1 completly inside T2? */
        /* check if V0 is inside tri(U0,U1,U2) */
        aa=U1[1]-U0[1];
        bb=-(U1[0]-U0[0]);
        cc=-aa*U0[0]-bb*U0[1];
        d0=aa*V0[0]+bb*V0[1]+cc;

        aa=U2[1]-U1[1];
        bb=-(U2[0]-U1[0]);
        cc=-aa*U1[0]-bb*U1[1];
        d1=aa*V0[0]+bb*V0[1]+cc;

        aa=U0[1]-U2[1];
        bb=-(U0[0]-U2[0]);
        cc=-aa*U2[0]-bb*U2[1];
        d2=aa*V0[0]+bb*V0[1]+cc;
        if(d0*d1>0.0)
        {
          if(d0*d2>0.0) return true;
        }
        return false;
    }

    private boolean coplanar_tri_tri(double[] N, double[] V0, double[] V1, double[] V2,
                         double[] U0, double[] U1, double[] U2){
       double[] A = new double[3];
       short i0,i1;
       /* first project onto an axis-aligned plane, that maximizes the area */
       /* of the triangles, compute indices: i0,i1. */
       A[0]=Math.abs(N[0]);
       A[1]=Math.abs(N[1]);
       A[2]=Math.abs(N[2]);
       if(A[0]>A[1])
       {
          if(A[0]>A[2])
          {
              i0=1;      /* A[0] is greatest */
              i1=2;
          }
          else
          {
              i0=0;      /* A[2] is greatest */
              i1=1;
          }
       }
       else   /* A[0]<=A[1] */
       {
          if(A[2]>A[1])
          {
              i0=0;      /* A[2] is greatest */
              i1=1;
          }
          else
          {
              i0=0;      /* A[1] is greatest */
              i1=2;
          }
        }

        /* test all edges of triangle 1 against the edges of triangle 2 */
        if(EDGE_AGAINST_TRI_EDGES(V0,V1,U0,U1,U2)){
            return true;
        }
        if(EDGE_AGAINST_TRI_EDGES(V1,V2,U0,U1,U2)){
            return true;
        }
        if(EDGE_AGAINST_TRI_EDGES(V2,V0,U0,U1,U2)){
            return true;
        }

        /* finally, test if tri1 is totally contained in tri2 or vice versa */
        if(POINT_IN_TRI(V0,U0,U1,U2)){
            return true;
        }
        if(POINT_IN_TRI(U0,V0,V1,V2)){
            return true;
        }

        return false;
    }



    private boolean NEWCOMPUTE_INTERVALS(double VV0, double VV1, double VV2, double D0, double D1, double D2, double D0D1, double D0D2, double[] ABC, double[] X, double[] N1, double[] V0, double[] V1, double[] V2, double[] U0, double[] U1, double[] U2){
        if(D0D1>0.0)
        {
            /* here we know that D0D2<=0.0 */
            /* that is D0, D1 are on the same side, D2 on the other or on the plane */
            ABC[0]=VV2; ABC[1]=(VV0-VV2)*D2; ABC[2]=(VV1-VV2)*D2; X[0]=D2-D0; X[1]=D2-D1;
        }
        else if(D0D2>0.0)
        {
            /* here we know that d0d1<=0.0 */
            ABC[0]=VV1; ABC[1]=(VV0-VV1)*D1; ABC[2]=(VV2-VV1)*D1; X[0]=D1-D0; X[1]=D1-D2;
        }
        else if(D1*D2>0.0 || D0!=0.0)
        {
            /* here we know that d0d1<=0.0 or that D0!=0.0 */
            ABC[0]=VV0; ABC[1]=(VV1-VV0)*D0; ABC[2]=(VV2-VV0)*D0; X[0]=D0-D1; X[1]=D0-D2;
        }
        else if(D1!=0.0)
        {
            ABC[0]=VV1; ABC[1]=(VV0-VV1)*D1; ABC[2]=(VV2-VV1)*D1; X[0]=D1-D0; X[1]=D1-D2;
        }
        else if(D2!=0.0)
        {
            ABC[0]=VV2; ABC[1]=(VV0-VV2)*D2; ABC[2]=(VV1-VV2)*D2; X[0]=D2-D0; X[1]=D2-D1;
        }
        else
        {
            /* triangles are coplanar */
            return coplanar_tri_tri(N1,V0,V1,V2,U0,U1,U2);
        }
        return false;
    }

    private boolean NoDivTriTriIsect(double[] V0, double[] V1, double[] V2,
                         double[] U0, double[] U1,double[] U2){
        double[] E1 = new double[3], E2 = new double[3];
        double[] N1 = new double[3], N2 = new double[3];
        double d1,d2;
        double du0,du1,du2,dv0,dv1,dv2;
        double[] D = new double[3];
        double[] isect1 = new double[2], isect2 = new double[2];
        double du0du1, du0du2, dv0dv1,dv0dv2;
        short index;
        double vp0,vp1,vp2;
        double up0,up1,up2;
        double bb,cc,max;

        /* compute plane equation of triangle(V0,V1,V2) */
        E1 = SUB(V1,V0);
        E2 = SUB(V2,V0);
        N1 = CROSS(E1,E2);
        d1=-DOT(N1,V0);
        /* plane equation 1: N1.X+d1=0 */

        /* put U0,U1,U2 into plane equation 1 to compute signed distances to the plane*/
        du0=DOT(N1,U0)+d1;
        du1=DOT(N1,U1)+d1;
        du2=DOT(N1,U2)+d1;

        /* coplanarity robustness check */
        if(USE_EPSILON_TEST){
            if(Math.abs(du0)<EPSILON) du0=0.0;
            if(Math.abs(du1)<EPSILON) du1=0.0;
            if(Math.abs(du2)<EPSILON) du2=0.0;
        }
        du0du1=du0*du1;
        du0du2=du0*du2;

        if(du0du1>0.0f && du0du2>0.0f) /* same sign on all of them + not equal 0 ? */
          return false;                    /* no intersection occurs */

        /* compute plane of triangle (U0,U1,U2) */
        E1=SUB(U1,U0);
        E2=SUB(U2,U0);
        N2=CROSS(E1,E2);
        d2=-DOT(N2,U0);
        /* plane equation 2: N2.X+d2=0 */

        /* put V0,V1,V2 into plane equation 2 */
        dv0=DOT(N2,V0)+d2;
        dv1=DOT(N2,V1)+d2;
        dv2=DOT(N2,V2)+d2;

        if(USE_EPSILON_TEST){
            if(Math.abs(dv0)<EPSILON) dv0=0.0;
            if(Math.abs(dv1)<EPSILON) dv1=0.0;
            if(Math.abs(dv2)<EPSILON) dv2=0.0;
        }

        dv0dv1=dv0*dv1;
        dv0dv2=dv0*dv2;

        if(dv0dv1>0.0f && dv0dv2>0.0f) /* same sign on all of them + not equal 0 ? */
            return false; /* no intersection occurs */

        /* compute direction of intersection line */
        D = CROSS(N1,N2);

        /* compute and index to the largest component of D */
        max=(double)Math.abs(D[0]);
        index=0;
        bb=(double)Math.abs(D[1]);
        cc=(double)Math.abs(D[2]);
        if(bb>max){
            max=bb;
            index=1;
        }
        if(cc>max){
            max=cc;
            index=2;
        }

        /* this is the simplified projection onto L*/
        vp0=V0[index];
        vp1=V1[index];
        vp2=V2[index];

        up0=U0[index];
        up1=U1[index];
        up2=U2[index];

        /* compute interval for triangle 1 */
        double[] abc = new double[3];
        double[] x = new double[2];
        if(NEWCOMPUTE_INTERVALS(vp0,vp1,vp2,dv0,dv1,dv2,dv0dv1,dv0dv2,abc,x,N1,V0,V1,V2,U0,U1,U2)){
            return true;
        }

        /* compute interval for triangle 2 */
        double[] def = new double[3];
        double[] y = new double[2];
        if(NEWCOMPUTE_INTERVALS(up0,up1,up2,du0,du1,du2,du0du1,du0du2,def,y,N1,V0,V1,V2,U0,U1,U2)){
            return true;
        }

        double xx,yy,xxyy,tmp;
        xx=x[0]*x[1];
        yy=y[0]*y[1];
        xxyy=xx*yy;

        tmp=abc[0]*xxyy;
        isect1[0]=tmp+abc[1]*x[1]*yy;
        isect1[1]=tmp+abc[2]*x[0]*yy;

        tmp=def[0]*xxyy;
        isect2[0]=tmp+def[1]*xx*y[1];
        isect2[1]=tmp+def[2]*xx*y[0];
        
        if(isect1[0]>isect1[1])
        {
          double aux;
          aux=isect1[0];
          isect1[0]=isect1[1];
          isect1[1]=aux;
        }
        
        if(isect2[0]>isect2[1])
        {
          double aux;
          aux=isect2[0];
          isect2[0]=isect2[1];
          isect2[1]=aux;
        }

        if(isect1[1]<isect2[0] || isect2[1]<isect1[0]) return false;
        return true;
    }

    @Override
    public String toString(){
        return getA() + " " + getB() + " " + getC();
    }

}
