/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom.lib3D;

import algGeom.lib2D.BasicGeom;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javafx.util.Pair;

/**
 *
 * @author alvmo
 */
public class VoxelModel {

    Voxel[][][] malla;
    Vect3d min; //menor x,y,z
    Vect3d max; //max x, y, z
    int nVoxelX, nVoxelY, nVoxelZ;
    TriangleMesh mesh;

    double tamaVoxel;

    public VoxelModel(TriangleMesh mesh, double tamaVoxel) {

        this.mesh = mesh;
        this.tamaVoxel = tamaVoxel;
        min = mesh.getAABB().min;
        max = mesh.getAABB().max;

        Vect3d v = max.sub(min);
        nVoxelX = (int) (v.getX() / tamaVoxel) + 1;
        nVoxelY = (int) (v.getY() / tamaVoxel) + 1;
        nVoxelZ = (int) (v.getZ() / tamaVoxel) + 1;

        malla = new Voxel[nVoxelX][nVoxelY][nVoxelZ];

        Vect3d aMin = new Vect3d(min);
        Vect3d aMax = new Vect3d(min.add(new Vect3d(tamaVoxel, tamaVoxel, tamaVoxel)));

        int n = 0;

        for (int i = 0; i < malla.length; i++) {
            aMin.y = min.y;
            aMax.y = min.y + tamaVoxel;
            for (int j = 0; j < malla[i].length; j++) {
                aMin.z = min.z;
                aMax.z = min.z + tamaVoxel;
                for (int k = 0; k < malla[i][j].length; k++) {
                    Vect3d aaMax = new Vect3d(aMax);
                    Vect3d aaMin = new Vect3d(aMin);
                    malla[i][j][k] = new Voxel(aaMin, aaMax, Voxel.Colour.NP);
                    aMin.z += tamaVoxel;
                    aMax.z += tamaVoxel;
                }
                aMin.y += tamaVoxel;
                aMax.y += tamaVoxel;
            }
            aMin.x += tamaVoxel;
            aMax.x += tamaVoxel;
        }

        voxelizar();
    }

    public VoxelModel(String filename, int tamavoxel) throws IOException {
        TriangleMesh mesh = new TriangleMesh(filename);

        this.tamaVoxel = tamaVoxel;
        min = mesh.getAABB().min;
        max = mesh.getAABB().max;

        Vect3d v = max.sub(min);
        nVoxelX = (int) (v.getX() / tamaVoxel) + 1;
        nVoxelY = (int) (v.getY() / tamaVoxel) + 1;
        nVoxelZ = (int) (v.getZ() / tamaVoxel) + 1;

        malla = new Voxel[nVoxelX][nVoxelY][nVoxelZ];

        Vect3d aMin = new Vect3d(min);
        Vect3d aMax = new Vect3d(min.add(new Vect3d(tamaVoxel, tamaVoxel, tamaVoxel)));

        int n = 0;

        for (int i = 0; i < malla.length; i++) {
            aMin.y = min.y;
            aMax.y = min.y + tamaVoxel;
            for (int j = 0; j < malla[i].length; j++) {
                aMin.z = min.z;
                aMax.z = min.z + tamaVoxel;
                for (int k = 0; k < malla[i][j].length; k++) {
                    Vect3d aaMax = new Vect3d(aMax);
                    Vect3d aaMin = new Vect3d(aMin);
                    malla[i][j][k] = new Voxel(aaMin, aaMax, Voxel.Colour.NP);
                    for (int l = 0; l < mesh.getFacesSize(); l++) {
                        if (malla[i][j][k].aabb_tri(mesh.getTriangle(l))) {
                            malla[i][j][k].setColour(Voxel.Colour.grey);
                            n++;
                        }
                    }
                    aMin.z += tamaVoxel;
                    aMax.z += tamaVoxel;
                }
                aMin.y += tamaVoxel;
                aMax.y += tamaVoxel;
            }
            aMin.x += tamaVoxel;
            aMax.x += tamaVoxel;
        }

        voxelizar();
    }

    private static Comparator<Pair<Vect3d, Triangle3d>> comparator = new Comparator<Pair<Vect3d, Triangle3d>>() {
        @Override
        public int compare(Pair<Vect3d, Triangle3d> t, Pair<Vect3d, Triangle3d> t1) {
            return (t.getKey().getY() < t1.getKey().getY() ? 1
                    : (t.getKey().getY() > t1.getKey().getY() ? -1 : 0));
        }
    };

    private void voxelizar() {

        //Detección de GRISES
        long start = System.currentTimeMillis();

        //Estructuras de datos
        ArrayList<Triangle3d> T = new ArrayList<Triangle3d>();
        ArrayList<Triangle3d> Z = new ArrayList<Triangle3d>();
        ArrayList<Pair<Vect3d, Triangle3d>> Q = new ArrayList<Pair<Vect3d, Triangle3d>>();
        HashMap<Triangle3d, Integer> C = new HashMap<Triangle3d, Integer>();

        ArrayList<Triangle3d> triangles = mesh.getTriangles();

        for (int i = 0; i < triangles.size(); i++) {
            Q.add(new Pair(triangles.get(i).getA(), triangles.get(i)));
            Q.add(new Pair(triangles.get(i).getB(), triangles.get(i)));
            Q.add(new Pair(triangles.get(i).getC(), triangles.get(i)));
            C.put(triangles.get(i), 0);
        }

        Q.sort(comparator);

//        System.out.println(min.x + " " + min.y + " " + min.z);
//        System.out.println(max.x + " " + max.y + " " + max.z);
        //Comenzamos el algoritmo
        int i = 0;
        Triangle3d tri = new Triangle3d();
        Vect3d mini = new Vect3d();
        Vect3d maxi = new Vect3d();
        Voxel malo = new Voxel(mini, maxi);
        for (int j = malla[0].length - 1; j >= 0; j--) {
            ArrayList<Voxel> voxeles = new ArrayList<Voxel>();
            for (int a = 0; a < malla.length; a++) {
                for (int k = 0; k < malla[a][j].length; k++) {
                    voxeles.add(malla[a][j][k]);
                }
            }
            while (i < Q.size() && Q.get(i).getKey().getY() >= voxeles.get(j).getMin().getY()
                    && Q.get(i).getKey().getY() <= voxeles.get(j).getMax().getY()) {
                Triangle3d triangle = Q.get(i).getValue();
                if (C.get(triangle) == 0) {
                    T.add(triangle);
                    C.replace(triangle, 1);
                } else {
                    if (C.get(triangle) == 1) {
                        C.replace(triangle, 2);
                    } else {
                        if (C.get(triangle) == 2) {
                            Z.add(triangle);
                        }
                    }
                }
                i++;
            }

            for (int l = 0; l < voxeles.size(); l++) {
                for (int m = 0; m < T.size(); m++) {
                    if (voxeles.get(l).aabb_tri(T.get(m))) {
                        voxeles.get(l).setColour(Voxel.Colour.grey);
                        if (j == malla[0].length - 2) {
                            if (voxeles.get(l).getCenter().x > -3) {
                                if (voxeles.get(l).getCenter().z < -56) {
//                                    System.out.println(voxeles.get(l).getCenter().x + " " + voxeles.get(l).getCenter().y + " " + voxeles.get(l).getCenter().z);
                                    voxeles.get(l).setColour(Voxel.Colour.NP);
                                    T.remove(m);
//voxeles.get(l).setColour(Voxel.Colour.yellow);
//                                    voxeles.get(l).aabb_tri(T.get(m));
//                                    malo = voxeles.get(l);
                                }
                            }
                        }
                    }
                }

            }
        }
        for (int g = 0; g < T.size(); g++) {
            Boolean si = malo.aabb_tri(T.get(g));
            if (si) {
                tri = T.get(g);
            }
        }

        T.removeAll(Z);
        Z.clear();

        System.out.println("Vertice 1");
        System.out.println(tri.getA().x);
        System.out.println(tri.getA().y);
        System.out.println(tri.getA().z);
        System.out.println("Vertice 2");
        System.out.println(tri.getB().x);
        System.out.println(tri.getB().y);
        System.out.println(tri.getB().z);
        System.out.println("Vertice 3");
        System.out.println(tri.getC().x);
        System.out.println(tri.getC().y);
        System.out.println(tri.getC().z);
        System.out.println("Min voxel");
        System.out.println(malo.min.getX());
        System.out.println(malo.min.getY());
        System.out.println(malo.min.getZ());
        System.out.println("Max voxel");
        System.out.println(malo.max.getX());
        System.out.println(malo.max.getY());
        System.out.println(malo.max.getZ());

        long end = System.currentTimeMillis();
        long time = end - start;
        if (time < 1000) {
            System.out.println("Ha tardado en voxelizar el modelo y encontrar los grises: " + time + " milisegundos");
        } else {
            time = time / 1000;
            System.out.println("Ha tardado en voxelizar el modelo y encontrar los grises: " + time + " segundos");
        }

        //Detección de NEGROS
        start = System.currentTimeMillis();

        //Comprobamos si el centro de la malla está contenido en el modelo. Si no lo está buscaremos un voxel negro.
        int a = nVoxelX / 2;
        int b = nVoxelY / 2;
        int c = nVoxelZ / 2;
        Voxel voxelNegro = malla[a][b][c];

//        System.out.println("El voxel del centro es " + voxelNegro.getColour());
        Boolean encontrado = mesh.pointIntoMesh(voxelNegro.getCenter()) && voxelNegro.getColour() == Voxel.Colour.NP;

        while (!encontrado) {

            for (int a1 = 0; a1 < malla.length & !encontrado; a1++) {
                for (int a2 = 0; a2 < malla[a1].length & !encontrado; a2++) {
                    for (int a3 = 0; a3 < malla[a1][a2].length & !encontrado; a3++) {
                        voxelNegro = malla[a1][a2][a3];
                        encontrado = mesh.pointIntoMesh(voxelNegro.getCenter()) && voxelNegro.getColour() == Voxel.Colour.NP;
                        if (encontrado) {
                            a = a1;
                            b = a2;
                            c = a3;
                            //System.out.println("Encontrado. El voxel " + a + " " + b + " " + c + " es " + voxelNegro.getColour());
                        }
                    }
                }
            }

        }

        if (encontrado) {

            LinkedList<Voxel> cola = new LinkedList<Voxel>();

            cola.addFirst(voxelNegro);
//            System.out.println("Empezaremos por el voxel " + a + " " + b + " " + c + ".");

            ArrayList<Voxel> ady = new ArrayList<Voxel>(); //Creamos una lita de adyacentes

            while (!cola.isEmpty()) {

                Voxel v = cola.removeFirst();
//                System.out.println("El voxel a poner negro es " + v.getColour());
                v.setColour(Voxel.Colour.black); //Ponemos color negro al elemento actual

                a = (int) ((v.getCenter().getX() - min.getX()) / ((max.getX() - min.getX()) / (nVoxelX - 1)));
                b = (int) ((v.getCenter().getY() - min.getY()) / ((max.getY() - min.getY()) / (nVoxelY - 1)));
                c = (int) ((v.getCenter().getZ() - min.getZ()) / ((max.getZ() - min.getZ()) / (nVoxelZ - 1)));

//                System.out.println(malla[a][b][c].getColour());
                //Encontramos los adyacentes
                if ((a - 1) >= 0) {
                    ady.add(malla[a - 1][b][c]);
                }
                if ((b - 1) >= 0) {
                    ady.add(malla[a][b - 1][c]);
                }
                if ((c - 1) >= 0) {
                    ady.add(malla[a][b][c - 1]);
                }
                if ((a + 1) < malla.length) {
                    ady.add(malla[a + 1][b][c]);
                }
                if ((b + 1) < malla[a].length) {
                    ady.add(malla[a][b + 1][c]);
                }
                if ((c + 1) < malla[a][b].length) {
                    ady.add(malla[a][b][c + 1]);
                }

                //Comprobamos el color de los adyacentes, y si es NP se añade a la pila
                for (int aux = 0; aux < ady.size(); aux++) {
                    if (ady.get(aux).getColour() == Voxel.Colour.NP) {
                        cola.addFirst(ady.get(aux));
//                        System.out.println("El voxel metido es " + pila.peek().getColour());
                    }
                }

                ady.clear(); //Vaciamos la lista de adyacentes
            }
        }

        end = System.currentTimeMillis();
        time = end - start;
        if (time < 1000) {
            System.out.println("Ha tardado en voxelizar el modelo y encontrar los negros: " + time + " milisegundos");
        } else {
            time = time / 1000;
            System.out.println("Ha tardado en voxelizar el modelo y encontrar los negros: " + time + " segundos");
        }
    }

    public Voxel obtenerCasilla(double x, double y, double z) {
        int i = (int) ((x - min.getX()) / ((max.getX() - min.getX()) / (nVoxelX - 1)));
        int j = (int) ((y - min.getY()) / ((max.getY() - min.getY()) / (nVoxelY - 1)));
        int k = (int) ((z - min.getZ()) / ((max.getZ() - min.getZ()) / (nVoxelZ - 1)));
        Voxel c = malla[i][j][k];
        return c;
    }

    public boolean puntoEnMesh(Vect3d p) {
        return obtenerCasilla(p.x, p.y, p.z).getColour() == Voxel.Colour.black;
    }
}
