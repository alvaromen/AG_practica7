package algGeom.lib2D;

import java.io.BufferedReader;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

class SaveIOException extends Exception {

    private static final long serialVersionUID = 1L;
}

class ReadIOException extends Exception {

    private static final long serialVersionUID = 2L;
}

/**
 * Class that represents a point cloud
 */
public class PointCloud {

    ArrayList<Point> nubepuntos;

    public PointCloud() {
        nubepuntos = new ArrayList<Point>();
    }

    /**
     * Constructor of the point cloud of random form giving the total number of points.
     */
    public PointCloud(int tam) {
        nubepuntos = new ArrayList<Point>();
        Random random = new Random();
        random.setSeed(13920192);
        for(int i = 0; i < tam; i++){            
            nubepuntos.add(new Point(
                    random.nextDouble()*((random.nextInt(2)<=0.5) ? 100 : -100),
                    random.nextDouble()*((random.nextInt(2)<=0.5) ? 100 : -100))
                    );
        }
    }

    /**
     * Constructor of the point cloud from the coordinates of points stored in file
     */
    public PointCloud(String nombre) throws IOException {
        FileReader fr = new FileReader(nombre);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            String[] coord = line.split(" ");
            nubepuntos.add(new Point(Double.parseDouble(coord[0]),
                                        Double.parseDouble(coord[1])));
        }
        br.close();
        fr.close();
    }

    public void addPoint(Point p) {
        nubepuntos.add(p);
    }

    public int size() {
        return nubepuntos.size();
    }

    /**
     * Saves the cloud of points in file with the same format used by the constructor
     */
    public void save(String nombre) throws IOException {
        FileWriter fw = new FileWriter(nombre);
        BufferedWriter bw = new BufferedWriter(fw);
        for(int i = 0; i < nubepuntos.size(); i++){
            bw.write(nubepuntos.get(i).getX() + " " + nubepuntos.get(i).getY());
        }
        bw.close();
        fw.close();
    }
    
    public Point getPoint(int pos) {
        if ((pos >= 0) && (pos < nubepuntos.size())) {
            return nubepuntos.get(pos);
        }
        return null;
    }
    
    /**
     * Returns the more central existing point in the cloud
     */
    public Point centralPoint() {
        double[][] distances = new double[nubepuntos.size()][nubepuntos.size()+1];
        for(int i = 0; i < nubepuntos.size(); i++){
            double sum = 0;
            for(int j = 0; j < nubepuntos.size(); j++){
                double distance = nubepuntos.get(i).distance(nubepuntos.get(j));
                distances[i][j] = distance;
                sum += distance;
            }
            distances[i][distances.length - 1] = sum;
        }
        
        int iMenor = 0;
        for(int i = 1; i < distances.length; i++){
            if(distances[i][distances.length - 1] < distances[iMenor][distances.length - 1]){
                iMenor = i;
            }
        }
        
        return new Point(nubepuntos.get(iMenor));
    }

}
