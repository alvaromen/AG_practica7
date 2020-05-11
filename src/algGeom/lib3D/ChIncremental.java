/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom.lib3D;

import algGeom.lib2D.BasicGeom;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alvmo
 */
public class ChIncremental {

    /**
     * lista de caras recién creadas
     */
    protected List<Facet> created;
    /**
     * lista de ejes
     */
    protected List<Edge> edges;

    private PointCloud3d cloud;

    public ChIncremental(PointCloud3d cloud) {

        this.cloud = cloud;
    }

    public void setCloud(PointCloud3d cloud) {
        this.cloud = cloud;
    }

    public PointCloud3d getCloud() {
        return cloud;
    }

    public boolean envolvente() {

        created = new ArrayList<Facet>();
        edges = new ArrayList<Edge>();

        ArrayList<ArrayList<Facet>> conflicts = new ArrayList<ArrayList<Facet>>();

        List<Edge> EH = new ArrayList<Edge>();
        List<Integer> V = new ArrayList<Integer>();

        //PASO 1: Creamos el primer tetraedro
        //1.Cogemos los 4 primeros puntos
        Vect3d a = cloud.getPoint(0);
        Vect3d b = cloud.getPoint(1);
        Vect3d c = cloud.getPoint(2);
        Vect3d d = cloud.getPoint(3);

        //2.Creamos el tetraedro
        //1.2
        Facet abc = new Facet(a, b, c);
        if (Util.BasicGeom.equal(0, abc.area())) {
            System.out.println("Area 0. Buscando nueva nube de puntos.");
            return false;
        }

        //1.3
        if (abc.classify(d) == PointPosition.COPLANAR) {
            System.out.println("Area 0. Buscando nueva nube de puntos.");
            return false;
        }

        Facet bdc = new Facet(b, d, c);
        Facet acd = new Facet(a, c, d);
        Facet adb = new Facet(a, d, b);

        //1.4.a Encontramos el punto inicial del tetraedro
        double x = (a.x + b.x + c.x + d.x) / 4;
        double y = (a.y + b.y + c.y + d.y) / 4;
        double z = (a.z + b.z + c.z + d.z) / 4;

        Vect3d centro = new Vect3d(x, y, z);

        //1.4.b Si las normales miran hacia dentro tenemos que permutar los vértices
        if (abc.normal().dot(abc.a.sub(centro)) > 0) {

            abc = new Facet(a, b, c);
            abc.setNormal(abc.normal());
        } else {

            abc.setNormal(abc.normal());
        }
        if (bdc.normal().dot(bdc.a.sub(centro)) > 0) {

            bdc = new Facet(b, c, d);
            bdc.setNormal(bdc.normal());
        } else {

            bdc.setNormal(bdc.normal());
        }
        if (acd.normal().dot(acd.a.sub(centro)) > 0) {

            acd = new Facet(a, d, c);
            acd.setNormal(acd.normal());
        } else {

            acd.setNormal(acd.normal());
        }
        if (adb.normal().dot(adb.a.sub(centro)) > 0) {

            adb = new Facet(a, b, d);
            adb.setNormal(adb.normal());
        } else {

            adb.setNormal(adb.normal());
        }

        Edge abc_ab = new Edge(a, b, abc);
        Edge abc_bc = new Edge(b, c, abc);
        Edge abc_ca = new Edge(c, a, abc);

        Edge bdc_bd = new Edge(b, d, bdc);
        Edge bdc_bc = new Edge(d, c, bdc);
        Edge bdc_cb = new Edge(c, b, bdc);

        Edge acd_ac = new Edge(a, c, acd);
        Edge acd_cd = new Edge(c, d, acd);
        Edge acd_da = new Edge(d, a, acd);

        Edge adb_ad = new Edge(a, d, adb);
        Edge adb_db = new Edge(d, b, adb);
        Edge adb_ba = new Edge(b, a, adb);

        //1.5
        abc.setAb(abc_ab);
        abc.setBc(abc_bc);
        abc.setCa(abc_ca);

        bdc.setAb(bdc_bd);
        bdc.setBc(bdc_bc);
        bdc.setCa(bdc_cb);

        acd.setAb(acd_ac);
        acd.setBc(acd_cd);
        acd.setCa(acd_da);

        adb.setAb(adb_ad);
        adb.setBc(adb_db);
        adb.setCa(adb_ba);

        edges.add(abc_ab);
        edges.add(abc_bc);
        edges.add(abc_ca);
        edges.add(bdc_bd);
        edges.add(bdc_bc);
        edges.add(bdc_cb);
        edges.add(acd_ac);
        edges.add(acd_cd);
        edges.add(acd_da);
        edges.add(adb_ad);
        edges.add(adb_db);
        edges.add(adb_ba);

        created.add(abc);
        created.add(acd);
        created.add(adb);
        created.add(bdc);

        //Incluimos los twins
        abc_ab.setTwin(adb_ba);
        abc_bc.setTwin(bdc_cb);
        abc_ca.setTwin(acd_ac);
        bdc_bd.setTwin(adb_db);
        bdc_bc.setTwin(acd_cd);
        bdc_cb.setTwin(abc_bc);
        acd_ac.setTwin(abc_ca);
        acd_cd.setTwin(bdc_bc);
        acd_da.setTwin(adb_ad);
        adb_ad.setTwin(acd_da);
        adb_db.setTwin(bdc_bd);
        adb_ba.setTwin(abc_ab);

        //6 y 7
        for (int i = 0; i < cloud.size(); i++) {
            conflicts.add(new ArrayList<Facet>());
        }
        for (int i = 4; i < cloud.size(); i++) {
            for (int j = 0; j < created.size(); j++) {
                //Comprobamos que el punto mire a la cara
                if (created.get(j).getNormal().dot(created.get(j).a.sub(cloud.getPoint(i))) > 0) {
                    conflicts.get(i).add(created.get(j));
                }
            }
        }

        //PASO 2: Procesar los puntos restantes y añadir soluciones parciales
        for (int i = 3; i < cloud.size(); i++) {
            if (!conflicts.get(i).isEmpty()) {
                //a
                for (int j = 0; j < conflicts.get(i).size(); j++) {
                    int k = 0;
                    while (k < edges.size()) {
                        if (edges.get(k).equals(conflicts.get(i).get(j))) {
                            edges.remove(k);
                        } else {
                            k++;
                        }
                    }
                    created.remove(conflicts.get(i).get(j));
                }

                //b
                for (int j = 0; j < conflicts.get(i).size(); j++) {

                    Edge eje1 = conflicts.get(i).get(j).getAb();
                    if (eje1.getTwin().getFacet().getNormal().dot(eje1.getTwin().getFacet().a.sub(cloud.getPoint(i))) < 0) {
                        EH.add(eje1);
                    }

                    Edge eje2 = conflicts.get(i).get(j).getBc();
                    if (eje2.getTwin().getFacet().getNormal().dot(eje2.getTwin().getFacet().a.sub(cloud.getPoint(i))) < 0) {
                        EH.add(eje2);
                    }
                    
                    Edge eje3 = conflicts.get(i).get(j).getCa();
                    if (eje3.getTwin().getFacet().getNormal().dot(eje3.getTwin().getFacet().a.sub(cloud.getPoint(i))) < 0) {
                        EH.add(eje3);
                    }
                }

                //c
                for (int k = 0; k < EH.size(); k++) {

                    Facet fj = new Facet(new Vect3d(EH.get(k).getA()), new Vect3d(EH.get(k).getB()), new Vect3d(cloud.getPoint(i)));
                    Edge ab = new Edge(new Vect3d(EH.get(k).getA()), new Vect3d(EH.get(k).getB()), fj, EH.get(k).getTwin());
                    Edge bc = new Edge(new Vect3d(EH.get(k).getB()), new Vect3d(cloud.getPoint(i)), fj);
                    Edge ca = new Edge(new Vect3d(cloud.getPoint(i)), new Vect3d(EH.get(k).getA()), fj);
                    fj.setAb(ab);
                    fj.setBc(bc);
                    fj.setCa(ca);

                    //Hay que poner bien la normal reordenando los puntos
                    if (fj.normal().dot(fj.a.sub(centro)) < 0) {
                        fj.setNormal(fj.normal());
                    } else {
                        Vect3d aux = new Vect3d(fj.b);
                        fj.setB(fj.c);
                        fj.setC(aux);
                        fj.setNormal(fj.normal());
                    }

                    edges.add(ab);
                    edges.add(bc);
                    edges.add(ca);
                    created.add(fj);

                    EH.get(k).getTwin().setTwin(ab);

                    //Comprobar que no sean coplanares (y hacer otra nube?)
                    if (PointPosition.COPLANAR == fj.classify(EH.get(k).getTwin().getFacet().getC())
                            && PointPosition.COPLANAR == fj.classify(EH.get(k).getTwin().getFacet().getB())
                            && PointPosition.COPLANAR == fj.classify(EH.get(k).getTwin().getFacet().getA())) {
                        System.out.println("Caras coplanares. Buscando nueva nube de puntos.");
                        return false;
                    }
                    //4
                    for (int l = i + 1; l < conflicts.size(); l++) {
                        if (fj.normal().dot(fj.a.sub(cloud.getPoint(l))) > 0) {
                            conflicts.get(l).add(fj);
                        }
                    }
                }

                for (int j = 0; j < edges.size(); j++) {
                    if (edges.get(j).getTwin() == null) {
                        for (int k = 0; k < edges.size(); k++) {
                            Edge aux = edges.get(j);
                            Edge aux2 = edges.get(k);
                            if (j != k && aux.equals(aux2)) {
                                edges.get(j).setTwin(edges.get(k));
                            }
                        }
                    }
                }

                //d
                EH.clear();
                V.clear();

                for (int j = i + 1; j < conflicts.size(); j++) {
                    for (int k = 0; k < conflicts.get(i).size(); k++) {
                        conflicts.get(j).remove(conflicts.get(i).get(k));
                    }
                }

                //e
                conflicts.get(i).clear();

            }

        }
        System.out.println("Numero de ejes " + edges.size());
        return true;
    }

}
