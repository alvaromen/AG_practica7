/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom.lib3D;

/**
 *
 * @author alvmo
 */
public class Facet extends Triangle3d {

    private Vect3d normal;
    private Edge ab;
    private Edge bc;
    private Edge ca;

    public Facet() {
    }

    public Facet(Vect3d a, Vect3d b, Vect3d c) {
        super(a, b, c);
        normal = this.normal();
    }

    public Facet(Vect3d normal, Edge ab, Edge bc, Edge ca) {

        super();
        
        Vect3d aux = ab.getA();
        Vect3d aux2 = ab.getB();
        Vect3d aux3 = bc.getA();
        
        if(aux3.equals(aux) || aux3.equals(aux2)){
            aux3 = bc.getB();
        }
        
        this.a = aux;
        this.b = aux2;
        this.c = aux3;
        this.normal = normal;
        this.ab = ab;
        this.bc = bc;
        this.ca = ca;
    }
    
    public Facet(Facet cara){
    
        super(cara.a,cara.b,cara.c);
        normal = cara.normal;
        this.ab = cara.ab;
        this.bc = cara.bc;
        this.ca = cara.ca;
    }

    /**
     * @return the ab
     */
    public Edge getAb() {
        return ab;
    }

    /**
     * @param ab the ab to set
     */
    public void setAb(Edge ab) {
        this.ab = ab;
    }

    /**
     * @return the bc
     */
    public Edge getBc() {
        return bc;
    }

    /**
     * @param bc the bc to set
     */
    public void setBc(Edge bc) {
        this.bc = bc;
    }

    /**
     * @return the ca
     */
    public Edge getCa() {
        return ca;
    }

    /**
     * @param ca the ca to set
     */
    public void setCa(Edge ca) {
        this.ca = ca;
    }

    /**
     * @return the normal
     */
    public Vect3d getNormal() {
        return normal;
    }

    /**
     * @param normal the normal to set
     */
    public void setNormal(Vect3d normal) {
        this.normal = normal;
    }

}
