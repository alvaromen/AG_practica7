/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaJTS;


import algGeom.lib3D.TDelaunay;
import java.util.ArrayList;
import java.util.List;


import com.vividsolutions.jts.triangulate.DelaunayTriangulationBuilder;
import com.vividsolutions.jts.triangulate.IncrementalDelaunayTriangulator;
import com.vividsolutions.jts.triangulate.quadedge.Vertex;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Coordinate;




/**
 * 
 * @author lidia
 */
public class ejemploDelaunay {

    
  final static GeometryFactory geomFact = new GeometryFactory();
	
  final static double SIDE_LEN = 1.0;
  final static double BASE_OFFSET = 1000;

  List severalPoints () {
      
    List pts = new ArrayList();
		
    pts.add(new Coordinate (3,5));
    pts.add(new Coordinate (0,0));
    pts.add(new Coordinate (1,2));
    pts.add(new Coordinate (5,5));
    pts.add(new Coordinate (8,2));
    pts.add(new Coordinate (4,-1));
    pts.add(new Coordinate (4,3));
                
            
    return pts;
}
        
  
  
  public void run() {
    
//     Se genera una lista de puntos   
    List pts = severalPoints();
    System.out.println("# pts: " + pts.size());
    
//     se hace la triangulación de Delaunay con esa lista de puntos 
    DelaunayTriangulationBuilder builder = new DelaunayTriangulationBuilder();
    builder.setSites(pts);
                
//     extraemos la geometría resultante como triángulos 
    Geometry g = builder.getTriangles(geomFact);            
    List<Geometry> triangles = new ArrayList<Geometry>(g.getNumGeometries());
                
//     se crea un lista de triángulos 
    for(int i = 0; i < g.getNumGeometries(); ++i) {
        triangles.add(g.getGeometryN(i));
    }
//        TDelaunay delaunay = new TDelaunay("");        
    // se muestra la lista de triángulos             
    for(int i = 0; i < triangles.size(); ++i) {
        System.out.println(triangles.get(i));
    }
                
    // se han creado 6 triángulos 
    System.out.println (" Número de geometrías: " + g.getNumGeometries() );
                
    // añadimos un nuevo punto a la triangulación anterior 
    IncrementalDelaunayTriangulator idt = new IncrementalDelaunayTriangulator(builder.getSubdivision());            
    idt.insertSite(new Vertex (9,0));
                
    // el número de triángulos de ha incrementado a 7 
    g = builder.getTriangles(geomFact);                
    System.out.println (" Número de geometrías: " + g.getNumGeometries() );
                
 }
	

        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       ejemploDelaunay test = new ejemploDelaunay();
       test.run();
    }
    
    
   
}

