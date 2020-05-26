/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom.lib3D;

import Util.BasicGeom;

public class Vect3d {

    double x, y, z;

    public Vect3d(double aa, double bb, double cc) {
        x = aa;
        y = bb;
        z = cc;
    }

    public Vect3d(Vect3d v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public Vect3d() {
        x = 0;
        y = 0;
        z = 0;
    }
    
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double[] getVert() {
        double[] vt = {x, y, z};
        return vt;
    }

    public void setVert(double aa, double bb, double cc) {
        x = aa;
        y = bb;
        z = cc;
    }

    /**
     * result = this - b
     */
    public Vect3d sub(Vect3d b) {
        return new Vect3d(x - b.getX(), y - b.getY(), z - b.getZ());

    }

    /**
     * result = this + b
     */
    public Vect3d add(Vect3d b) {
        return new Vect3d(x + b.getX(), y + b.getY(), z + b.getZ());

    }

    /**
     * product of a vector a number (scalar) result = this * valorEscalar
     */
    public Vect3d scalarMul(double val) {
        return new Vect3d(x * val, y * val, z * val);
    }

    /**
     * dot product  result = this.dot(b)
     */
    public double dot(Vect3d v) {
        return (x * v.getX() + y * v.getY() + z * v.getZ());
    }

    /**
     * devuelve this X b (cross product)
     */
    public Vect3d xProduct(Vect3d b) {
        return new Vect3d(y * b.getZ() - z * b.getY(),
                z * b.getX() - x * b.getZ(),
                x * b.getY() - y * b.getX());
    }

    /**
     * returns the vector length 
     */
    public double module() {
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    /**
     * returns the distance between two points in the space 
     */
    
    
    public double distance(Vect3d p) {
        double xx = x - p.x,
                yy = y - p.y,
                zz = z - p.z;
        return Math.sqrt(xx*xx+yy*yy+zz*zz); 
    }

    public boolean collinear(Vect3d a, Vect3d b) {
        Triangle3d tr = new Triangle3d(a, b, this);
        
        return BasicGeom.equal(tr.area(), BasicGeom.ZERO);
    }
    
    /**
     * Show values in the screen 
     */
    public void out() {
        System.out.print("Coordinate x: ");
        System.out.println(x);
        System.out.print("Coordinate y: ");
        System.out.println(y);
        System.out.print("Coordinate z: ");
        System.out.println(z);
    }
    
    @Override
    public String toString(){
        return "( " + x + ", " + y + ", " + z + " )";
    }

}
