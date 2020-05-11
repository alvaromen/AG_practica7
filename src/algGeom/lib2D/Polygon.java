package algGeom.lib2D;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that represents a Polygon fromed by Vertex objects
 */
public class Polygon {

    protected int nVertexs;
    protected ArrayList<Vertex> Vertexs;

    /**
     * Default empty constructor
     */
    public Polygon() {
        Vertexs = new ArrayList<Vertex>();
        nVertexs = 0;
    }

    public Polygon(int nV) {
        Vertexs = new ArrayList<Vertex>(nV);
        nVertexs = nV;
    }

    public Polygon(Polygon pl) {
        Vertexs = new ArrayList<Vertex>(pl.Vertexs);
        nVertexs = pl.nVertexs;
    }

    public Polygon(ArrayList<Vertex> vert, int nV) {
        Vertexs = new ArrayList<Vertex>(vert);
        nVertexs = nV;
    }

    public Polygon copy() {
        Polygon nuevoPolygon = new Polygon(nVertexs);
        nuevoPolygon.Vertexs = new ArrayList<Vertex>(Vertexs);
        nuevoPolygon.nVertexs = nVertexs;
        return nuevoPolygon;
    }

    public void copy(Polygon pl) {
        Vertexs.clear();                           //Se limpia el vector.
        Vertexs = new ArrayList<Vertex>(pl.Vertexs);    //Se copy el vector.
        nVertexs = pl.nVertexs;
    }

    public void set(Vertex v, int pos) {
        if (pos >= 0 && pos < nVertexs) {
            Vertex antiguo = new Vertex((Vertex) Vertexs.get(pos));
            antiguo.setPolygon(null);
            antiguo.setPosition(-1);
            Vertexs.set(pos, (Vertex) v);
            v.setPolygon(this);
            v.setPosition(pos);
        }
    }

    /**
     * Adds the vertex in the last position
     */
    public void add(Vertex v) {
        Vertexs.add((Vertex) v);
        v.setPolygon(this);
        v.setPosition(nVertexs);
        nVertexs++;
    }

    /**
     * Adds the point in the last position
     */
    public void add(Point p) {
        Vertex v = new Vertex(p, this, nVertexs);
        Vertexs.add((Vertex) v);
        nVertexs++;
    }

    public Vertex getVertexAt(int pos) {
        if (pos >= 0 && pos < nVertexs) {
            return (Vertex) Vertexs.get(pos);
        } else {
            return null;
        }
    }

    public Vertex setVertexAt(int pos) {
        if (pos >= 0 && pos < nVertexs) {
            return new Vertex((Vertex) Vertexs.get(pos));
        } else {
            return null;
        }
    }

    public int vertexSize() {
        return nVertexs;
    }

    public SegmentLine getEdge(int i) {
        return new SegmentLine(getVertexAt(i), getVertexAt((i + 1) % nVertexs));
    }

    /**
     * Polygon builder from file
     */
    public Polygon(String nombre) throws IOException {
        FileReader fr = new FileReader(nombre);
        BufferedReader br = new BufferedReader(fr);
        String line;
        ArrayList<Point> puntos = new ArrayList<Point>();
        while((line = br.readLine()) != null){
            String[] coord = line.split(" ");
            puntos.add(new Point(Double.parseDouble(coord[0]), Double.parseDouble(coord[1])));
        }
        br.close();
        fr.close();
        generarPoligono(puntos);
    }

    /**
     * Saves the coordinates of the polygon in file with the same format as the constructor
     */
    public void save(String nombre) throws IOException {
        FileWriter fw = new FileWriter(nombre);
        BufferedWriter bw = new BufferedWriter(fw);
        for(int i = 0; i < Vertexs.size(); i++){
            bw.write(Vertexs.get(i).getX() + " " + Vertexs.get(i).getY());
        }
        bw.close();
        fw.close();
    }

    /**
     * Assuming that this is a convex polygon, indicate if the point p is inside the polygon
     */
    public boolean pointInCovexPolygon(Point pt) {
        for(int i = 1; i < Vertexs.size(); i++){
            PointClassification c = pt.classify(Vertexs.get(i-1), Vertexs.get(i));
            if(c == PointClassification.RIGHT){
                return false;
            }
        }
        return true;
    }

    public boolean convex() {
       for(int i = 0; i < Vertexs.size(); i++){
           if(Vertexs.get(i).concave()){
               return false;
           }
       }
        return true;
    }

    public boolean intersects(Line r, Vector interseccion) {
        //XXXXX
        return false;
    }

    public boolean intersects(RayLine r, Vector interseccion) {
        //XXXXX
        
        return false;
    }

    public boolean intersects(SegmentLine r, Vector interseccion) {
        //XXXXX
        return false;
    }

    public void out() {
        Vertex v;
        for (int i = 0; i < nVertexs; i++) {
            v = (Vertex) Vertexs.get(i);
            v.out();
        }
    }

    /**
     * Genera un poligono con los vertices en el orden correcto a partir de una lista de puntos desordenada
     */
    public void generarPoligono(ArrayList<Point> puntos){
        if(puntos.size()<=4){
            generarPoligono1(puntos);
        } else generarPoligono2(puntos);
    }
    
    /**
     * Funcion que ordena los puntos de un pologono para que se pinte de forma correcta
     * Solo sirve para poligonos de 3 o 4 vertices
     */
    private void generarPoligono1(ArrayList<Point> puntos){
        for(int i = 2; i<puntos.size(); i++){
            if(puntos.get(i).classify(puntos.get(i-2), puntos.get(i-1)) == PointClassification.RIGHT){
                Point v = puntos.get(i-1);
                puntos.set(i-1, puntos.get(i));
                puntos.set(i, v);
            }
        }
        for(int i = 0; i < puntos.size(); i++){
            add(puntos.get(i));
        }
    }
    
    /**
     * Funcion que ordena los puntos de un poligono para que se pinte de forma correcta
     * Mas general, pero menos eficiente para poligonos de 3 o 4 vertices
     */
    private void generarPoligono2(ArrayList<Point> puntos){
        int indYMenor = 0;
        double yMenor = puntos.get(0).getY();
        for(int i = 1; i<puntos.size(); i++){
            if(puntos.get(i).getY()<yMenor){
                yMenor = puntos.get(i).getY();
                indYMenor = i;
            }
        }
        ArrayList<Double> angulos = new ArrayList<Double>();
        for(int i = 0; i < puntos.size(); i++){
            Point p = puntos.get(i).resta(puntos.get(indYMenor));
            angulos.add(p.getAlpha());
        }
        int[] indices = new int[puntos.size()];
        for(int i = 0; i < indices.length; i++){
            indices[i]=i;
        }
        boolean ordenado;
        do{
            ordenado = true;
            for(int i = 1 ; i < angulos.size(); i++){
                if(angulos.get(indices[i])<angulos.get(indices[i-1])){
                    int aux = indices[i-1];
                    indices[i-1] = indices [i];
                    indices[i] = aux;
                    ordenado = false;
                }
            }  
        } while(!ordenado);
        for(int i = 0; i < puntos.size(); i++){
            add(puntos.get(i));
        }
    }
    
}
