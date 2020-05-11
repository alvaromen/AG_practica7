/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom.lib3D;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.triangulate.DelaunayTriangulationBuilder;
import com.vividsolutions.jts.triangulate.IncrementalDelaunayTriangulator;
import com.vividsolutions.jts.triangulate.quadedge.Vertex;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Coordinate;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author alvaro
 */
public class TDelaunay {

    private final static GeometryFactory geomFact = new GeometryFactory();
    private ArrayList<Geometry> triangles;
    private DelaunayTriangulationBuilder builder;

    public TDelaunay() {
        builder = new DelaunayTriangulationBuilder();
    }

    public TDelaunay(String path) throws IOException {
        ArrayList<Coordinate> points = new ArrayList<Coordinate>();
        //Leemos los puntos del fichero
        File file = new File(path);
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] coord = line.split(" ");
            points.add(new Coordinate(Double.parseDouble(coord[0]),
                    Double.parseDouble(coord[1]),
                    Double.parseDouble(coord[2])));
        }
        br.close();
        fr.close();

        //Se triangulan los puntos del fichero por Delaunay
        builder = new DelaunayTriangulationBuilder();
        builder.setSites(points);

        //Extraemos los triágunlos de la geometría
        Geometry g = builder.getTriangles(geomFact);
        triangles = new ArrayList<Geometry>(g.getNumGeometries());

        //Llenamos la lista de triángulos
        for (int i = 0; i < g.getNumGeometries(); i++) {
            triangles.add(g.getGeometryN(i));
        }
    }

    public TDelaunay(PointCloud3d cloud) {
        //Se triangulan los puntos del fichero por Delaunay
        builder = new DelaunayTriangulationBuilder();
        List pts = new ArrayList();

        for (int i = 0; i < cloud.size(); i++) {

            pts.add(new Coordinate(cloud.getPoint(i).x, 
                    cloud.getPoint(i).y, 
                    cloud.getPoint(i).z));
        }
        builder.setSites(pts);

        //Extraemos los triágunlos de la geometría
        Geometry g = builder.getTriangles(geomFact);
        triangles = new ArrayList<Geometry>(g.getNumGeometries());

        //Llenamos la lista de triángulos
        for (int i = 0; i < g.getNumGeometries(); i++) {
            triangles.add(g.getGeometryN(i));
        }
    }

    public void addPoint(Vertex p) {
        IncrementalDelaunayTriangulator idt = new IncrementalDelaunayTriangulator(builder.getSubdivision());
        idt.insertSite(p);
    }

    /**
     * @return the triangles
     */
    public ArrayList<Geometry> getTriangles() {
        return triangles;
    }
}
