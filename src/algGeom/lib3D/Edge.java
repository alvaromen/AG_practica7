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
public class Edge {

    private Vect3d a; // vertice origen
    private Vect3d b; // vertice destino
    private Facet facet; // facet incidente
//    private Edge next; // siguiente eje
//    private Edge prev; // eje anterior
    private Edge twin; // eje común de la cara adyacente

    public Edge(Vect3d a, Vect3d b, Facet facet) {

        this.a = a;
        this.b = b;
        this.facet = facet;
    }
    
    public Edge(Vect3d a, Vect3d b, Facet facet, Edge twin) {

        this.a = a;
        this.b = b;
        this.facet = facet;
        this.twin = twin;
    }

    public Edge(Edge orig){
        
        this.a = orig.a;
        this.b = orig.b;
        this.facet = orig.facet;
        this.twin = orig.twin;
    }
    /**
     * @return the a
     */
    public Vect3d getA() {
        return a;
    }

    /**
     * @param a the a to set
     */
    public void setA(Vect3d a) {
        this.a = a;
    }

    /**
     * @return the b
     */
    public Vect3d getB() {
        return b;
    }

    /**
     * @param b the b to set
     */
    public void setB(Vect3d b) {
        this.b = b;
    }

    /**
     * @return the facet
     */
    public Facet getFacet() {
        return facet;
    }

    /**
     * @param facet the facet to set
     */
    public void setFacet(Facet facet) {
        this.facet = facet;
    }

    /**
     * @return the twin
     */
    public Edge getTwin() {
        return twin;
    }

    /**
     * @param twin the twin to set
     */
    public void setTwin(Edge twin) {
        this.twin = twin;
    }
    
    @Override
    public boolean equals(Object ot){
        
        if(ot instanceof Edge){ //si es un eje con lo que se compara
        
            //Mira si dos ejes estan puestos en la misma posicion en el espacio
            //No mira si esos dos ejes son identicos
            boolean aBool = false, bBool = false;
            Edge o = (Edge)ot;
            if((o.a.x == this.a.x) &&
               (o.a.y == this.a.y) &&
               (o.a.z == this.a.z)){
                aBool = true;
            }
            if((o.b.x == this.b.x) &&
               (o.b.y == this.b.y) &&
               (o.b.z == this.b.z)){
                bBool = true;
            }
            
            if(aBool && bBool){
                return true;
            }
            
            aBool = false; bBool = false;
            if((o.a.x == this.b.x) &&
               (o.a.y == this.b.y) &&
               (o.a.z == this.b.z)){
                aBool = true;
            }
            if((o.b.x == this.a.x) &&
               (o.b.y == this.a.y) &&
               (o.b.z == this.a.z)){
                bBool = true;
            }
            
            return aBool && bBool;
            
        }else{ //si es un triangulo con lo que se compara
            Triangle3d o = (Triangle3d)ot;

            Vect3d oa = o.a;
            Vect3d ob = o.b;
            Vect3d oc = o.c;
            boolean aBool = false, bBool = false, cBool = false;

            if((oa.x == this.getFacet().a.x) &&
                    (oa.y== this.getFacet().a.y) &&
                    (oa.z== this.getFacet().a.z)){
                aBool = true;
            }
            if((ob.x == this.getFacet().b.x) &&
                    (ob.y == this.getFacet().b.y) &&
                    (ob.z == this.getFacet().b.z)){
                bBool = true;
            }
            if(oc.x == this.getFacet().c.x &&
                    (oc.y== this.getFacet().c.y) &&
                    oc.z== this.getFacet().c.z){
                cBool = true;
            }

            return aBool && bBool && cBool;
        }
//        if(o instanceof Edge){
//            Edge e = (Edge) o;
//            return (this.a.equals(e.a)&& this.b.equals(e.b)) || (this.b.equals(e.a)&& this.a.equals(e.b));
//        } else if(o instanceof Triangle3d){
//            Triangle3d t = (Triangle3d) o;
//            return ((t.getA().x == this.getFacet().a.x) && (t.getA().y== this.getFacet().a.y) && (t.getA().z== this.getFacet().a.z)) &&
//                    ((t.getB().x == this.getFacet().b.x) && (t.getB().y == this.getFacet().b.y) && (t.getB().z == this.getFacet().b.z)) &&
//                    ((t.getC().x == this.getFacet().c.x && (t.getC().y== this.getFacet().c.y) && t.getC().z== this.getFacet().c.z));
//        } else return false;
    }
}
