package algGeom.lib3D;

import Util.BasicGeom;
import Util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;



public class PointCloud3d {
    
    ArrayList<Vect3d> points;

    public PointCloud3d(){
        points = new ArrayList<Vect3d>();
    }

    public PointCloud3d(int size){
        points = new ArrayList<Vect3d>();
        if(size > 0){
           
            Random r = new Random (40);

            double x, y, z;
            int pos;
            for(int i=0;i<size;i++){
                pos = r.nextBoolean() ? 1 : -1;
                x = r.nextDouble() * pos * BasicGeom.RANGE;
                pos = r.nextBoolean() ? 1 : -1;
                y = r.nextDouble() * pos * BasicGeom.RANGE;
                pos = r.nextBoolean() ? 1 : -1;
                z = r.nextDouble() * pos * BasicGeom.RANGE;

                Vect3d p = new Vect3d (x,y,z);
                points.add(p);
            }
        }
    }

    public int size(){
        return points.size();
    }

    public void clear (){
    	points.clear();
    }
    
//    public PointCloud (Mesh m){
//        // constructor a partir de una mesh
//    }
    
    public PointCloud3d(String path) throws IOException{
        
        points = new ArrayList<Vect3d>();
        
        File file = new File(path);
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            String[] coord = line.split(" ");
            points.add(new Vect3d(Double.parseDouble(coord[0]),
                                        Double.parseDouble(coord[1]),
                                        Double.parseDouble(coord[2])));
        }
        br.close();
        fr.close();
    }


    public void save(String nombre) throws IOException{
       FileWriter fw = new FileWriter(nombre);
        BufferedWriter bw = new BufferedWriter(fw);
        for(int i = 0; i < points.size(); i++){
            bw.write(points.get(i).getX() + " " +
                    points.get(i).getY() + " " +
                    points.get(i).getZ());
        }
        bw.close();
        fw.close();
    }


    public Vect3d getPoint(int pos){
        if((pos >= 0)&&(pos<points.size())){
            return points.get(pos);
        }
        return null;
    }
    
    public void addPoint (Vect3d p){
        points.add(p);
    }    
   
    AABB getAABB(){
        Vect3d  min = new Vect3d (points.get(0));
        Vect3d  max = new Vect3d (points.get(0));
        for(int i = 1; i < points.size(); i++){
            if(points.get(i).getX() < min.x){
                min.x = points.get(i).getX();
            } else {
                if(max.x < points.get(i).getX()){
                    max.x = points.get(i).getX();
                }
            }
            if(points.get(i).getY() < min.y){
                min.y = points.get(i).getY();
            } else {
                if(max.y < points.get(i).getY()){
                    max.y = points.get(i).getY();
                }
            }
            if(points.get(i).getZ() < min.z){
                min.z = points.get(i).getZ();
            } else {
                if(max.z < points.get(i).getZ()){
                    max.z = points.get(i).getZ();
                }
            }
        }
        return new AABB(min,max);
    }
}
