package algGeom.lib3D;

import Util.*;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public final class TriangleMesh {

    ArrayList<Face> faces;
    ArrayList<Vect3d> vertices;
    boolean setNormal;

    public TriangleMesh(String filename) throws IOException {
        faces = new ArrayList<Face>();
        vertices = new ArrayList<Vect3d>();
        setNormal = false;
        String obj = "obj";

        File file;
        FileReader fr;
        BufferedReader reader;

        file = new File(filename);
        fr = new FileReader(file);
        reader = new BufferedReader(fr);

        String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        if (!extension.equals(obj)) {
            System.out.println("Only obj model sare supported ");
        } else {
            loadobject(reader);
            setNormals();
            setNormal = true;
        }
    }

    public ArrayList<Triangle3d> getTriangles() {
        ArrayList<Triangle3d> triangles = new ArrayList<Triangle3d>();
        for (int i = 0; i < faces.size(); i++) {
            Face f = faces.get(i);
            Vect3d a = vertices.get(f.v1 - 1);
            Vect3d b = vertices.get(f.v2 - 1);
            Vect3d c = vertices.get(f.v3 - 1);
            triangles.add(new Triangle3d(a, b, c));
        }
        return triangles;
    }

    public double[] getVerticesTriangles() {
        double[] tri = new double[faces.size() * 9];
        for (int i = 0; i < faces.size(); i++) {
            Face f = faces.get(i);
            Vect3d a = vertices.get(f.v1 - 1);
            tri[i * 9] = a.x;
            tri[i * 9 + 1] = a.y;
            tri[i * 9 + 2] = a.z;
            Vect3d b = vertices.get(f.v2 - 1);
            tri[i * 9 + 3] = b.x;
            tri[i * 9 + 4] = b.y;
            tri[i * 9 + 5] = b.z;
            Vect3d c = vertices.get(f.v3 - 1);
            tri[i * 9 + 6] = c.x;
            tri[i * 9 + 7] = c.y;
            tri[i * 9 + 8] = c.z;
        }
        return tri;
    }

    public double[] getVertices() {
        double[] vertex = new double[3 * vertices.size()];
        int j = 0;
        for (int i = 0; i < vertices.size(); i++) {
            vertex[j++] = vertices.get(i).x;
            vertex[j++] = vertices.get(i).y;
            vertex[j++] = vertices.get(i).z;

        }
        return vertex;
    }


    public int[] getIndiceFaces() {
        int[] faces = new int[3 * this.faces.size()];
        int j = 0;
        for (int i = 0; i < this.faces.size(); i++) {
            faces[j++] = this.faces.get(i).v1;
            faces[j++] = this.faces.get(i).v2;
            faces[j++] = this.faces.get(i).v3;
        }
        return faces;
    }

    public Triangle3d getTriangle(int i) {
        Face f = faces.get(i);
        Vect3d a = vertices.get(f.v1 - 1);
        Vect3d b = vertices.get(f.v2 - 1);
        Vect3d c = vertices.get(f.v3 - 1);
        return new Triangle3d(a, b, c);
    }


    public Vect3d getVertice(int i) {
        return new Vect3d(vertices.get(i).getX(), vertices.get(i).getY(), vertices.get(i).getZ());
    }


    public void setNormals() {
        for (int i = 0; i < faces.size(); i++) {
            Face f = faces.get(i);
            Vect3d a = vertices.get(f.v1 - 1);
            Vect3d b = vertices.get(f.v2 - 1);
            Vect3d c = vertices.get(f.v3 - 1);
            Triangle3d t = new Triangle3d(a, b, c);
            f.setNormal(t.normal());
        }
        setNormal = true;
    }

    public double[] getNormals() {
        double[] nor = new double[3 * faces.size()];
        if (!setNormal) {
            setNormals();
        }
        for (int i = 0; i < faces.size(); i++) {
            Face f = faces.get(i);
            Vect3d b = f.getNormal();
            nor[i * 3] = b.x;
            nor[i * 3 + 1] = b.y;
            nor[i * 3 + 2] = b.z;
        }
        return nor;
    }

    
    /**
     * carga el modelo de la variable fichero
     *
     * @param br variable fichero
     */
    public void loadobject(BufferedReader br) {
        String line = "";

        try {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                line = line.replaceAll("  ", " ");
                if (line.length() > 0) {
                    if (line.startsWith("v ")) {
                        float[] vert = read3Floats(line);
                        Vect3d point = new Vect3d(vert[0], vert[1], vert[2]);
                        vertices.add(point);
                    } else if (line.startsWith("vt")) {

                        continue;

                    } else if (line.startsWith("vn")) {

                        continue;
                    } else if (line.startsWith("f ")) {
                        int[] faces = read3Integer(line);
                        this.faces.add(new Face(faces));
                    } else if (line.startsWith("g ")) {
                        continue;
                    } else if (line.startsWith("usemtl")) {
                        continue;
                    } else if (line.startsWith("mtllib")) {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("GL_OBJ_Reader.loadObject() failed at line: " + line);
        }

        System.out.println("Obj loader: vertices " + vertices.size()
                + " faces " + faces.size());

    }

    public int getFacesSize() {
        return faces.size();
    }

    public int getVerticesSize() {
        return vertices.size();
    }

    private int[] read3Integer(String line) {
        try {
            StringTokenizer st = new StringTokenizer(line, " ");
            st.nextToken();
            if (st.countTokens() == 2) {
                return new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 0};
            } else {
                return new int[]{Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken())};
            }
        } catch (NumberFormatException e) {
            System.out.println("GL_OBJ_Reader.read3Floats(): error on line '" + line + "', " + e);
            return null;
        }
    }

    private float[] read3Floats(String line) {
        try {
            StringTokenizer st = new StringTokenizer(line, " ");
            st.nextToken();
            if (st.countTokens() == 2) {
                return new float[]{Float.parseFloat(st.nextToken()),
                    Float.parseFloat(st.nextToken()),
                    0};
            } else {
                return new float[]{Float.parseFloat(st.nextToken()),
                    Float.parseFloat(st.nextToken()),
                    Float.parseFloat(st.nextToken())};
            }
        } catch (NumberFormatException e) {
            System.out.println("GL_OBJ_Reader.read3Floats(): error on line '" + line + "', " + e);
            return null;
        }
    }

    public AABB getAABB() {
        Vect3d  min = new Vect3d (getVertice(0));
        Vect3d  max = new Vect3d (getVertice(0));
        for(int i = 1; i < getVerticesSize(); i++){
            Vect3d v = getVertice(i);
            if(v.getX() < min.x){
                min.x = v.getX();
            } else {
                if(max.x < v.getX()){
                    max.x = v.getX();
                }
            }
            if(v.getY() < min.y){
                min.y = v.getY();
            } else {
                if(max.y < v.getY()){
                    max.y = v.getY();
                }   
            }
            if(v.getZ() < min.z){
                min.z = v.getZ();
            } else {
                if(max.z < v.getZ()){
                    max.z = v.getZ();
                }   
            }
        }
        return new AABB(min,max);
    }
    
    public boolean rayTraversalExh(Ray3d r, Vect3d p, Triangle3d t){
        ArrayList<Triangle3d> triangles = getTriangles();
        double minDistance = BasicGeom.INFINITY;
        for(int i = 0; i < triangles.size(); i++){
            Vect3d q = new Vect3d();
            if(triangles.get(i).ray_tri(r, q)){
                double distance = r.getOrigin().distance(q);
                if(distance < minDistance){
                    minDistance = distance;
                    t.set(triangles.get(i).getA(), triangles.get(i).getB(), triangles.get(i).getC());
                    p = q;
                }
            }
        }
        return minDistance != BasicGeom.INFINITY;
    }
    
    public boolean rayTraversalExh(Ray3d r, ArrayList<Vect3d> p, ArrayList<Triangle3d> t){
        ArrayList<Triangle3d> triangles = getTriangles();
        boolean intersect = false;
        for(int i = 0; i < triangles.size(); i++){
            Vect3d q = new Vect3d();
            if(triangles.get(i).ray_tri(r, q)){
                t.add(new Triangle3d(triangles.get(i)));
                p.add(new Vect3d(q));
                intersect = true;
            }
        }
        return intersect;
    }

    public boolean pointIntoMesh(Vect3d p) {

        Ray3d r1 = new Ray3d(p, new Vect3d(100, 0, 0));
        Ray3d r2 = new Ray3d(p, new Vect3d(0, 100, 0));

        ArrayList<Vect3d> puntos1 = new ArrayList<Vect3d>();
        ArrayList<Triangle3d> triangulos1 = new ArrayList<Triangle3d>();

        ArrayList<Vect3d> puntos2 = new ArrayList<Vect3d>();
        ArrayList<Triangle3d> triangulos2 = new ArrayList<Triangle3d>();

        rayTraversalExh(r1, puntos1, triangulos1);
        rayTraversalExh(r1, puntos1, triangulos1);

        if (triangulos1.size()%2 != 0 && triangulos2.size()%2 != 0) {

            return true;
            
        } else {

            Ray3d r3 = new Ray3d(p, new Vect3d(0, 100, 0));

            ArrayList<Vect3d> puntos3 = new ArrayList<Vect3d>();
            ArrayList<Triangle3d> triangulos3 = new ArrayList<Triangle3d>();
            
            rayTraversalExh(r1, puntos3, triangulos3);
            
            if(triangulos3.size()%2 != 0){
                
                return true;
                

            }
        }

        return false;
    }
    
    public void translate(Vect3d translation){
        for(int i = 0; i < vertices.size(); i++){
//            System.out.println("AAAAAA " + vertices.get(i).getX() + " " + vertices.get(i).getY() + " " + vertices.get(i).getZ());
            Vect3d nv = vertices.get(i).add(translation);
            vertices.get(i).setVert(nv.getX(), nv.getY(), nv.getZ());
//            System.out.println("BBBBBB " + vertices.get(i).getX() + " " + vertices.get(i).getY() + " " + vertices.get(i).getZ());
        }
    }
}
