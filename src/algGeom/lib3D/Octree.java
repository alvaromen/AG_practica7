/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom.lib3D;

import java.util.ArrayList;

/**
 *
 * @author alvaro
 */
class Node {

    private Node father;
    private ArrayList<Triangle3d> triangles;
    private AABB aabb;
    private int level;
    private Octree octree;
    private Node[] sons;
    private boolean sonsInstantiated;
    private boolean esHoja;
    private int maxSons;
    

    public Node(Node father, AABB aabb, int level, Octree octree) {
        this.father = father;
        this.aabb = aabb;
        this.level = level;
        this.octree = octree;
        this.father = father;
        for (int i = 0; i < 8; i++) {
            sons[i] = null;
        }
        triangles = new ArrayList<Triangle3d>();
        sonsInstantiated = false;
        esHoja = true;
        maxSons = 10;
    }

    public void insertTriangle(Triangle3d t) {
        if (level == octree.getMaxLevel()) {
            triangles.add(t);
        } else {
            if(esHoja){
                triangles.add(t);
                if(triangles.size() > maxSons){
                    if (!sonsInstantiated) { //it should instantiate anyway but lets check it
                        instantiateSons();
                        esHoja = false;
                    }
                }
            } else {
                for(int i = 0; i < sons.length; i++){
                    if(triangles.get(i).getAABB().testAABBAABB(sons[i].aabb)){
                        sons[i].insertTriangle(triangles.get(i));
                    }
                }
            }
        }
    }

    void instantiateSons() {
        
        double minX = aabb.getMin().getX();
        double minY = aabb.getMin().getY();
        double minZ = aabb.getMin().getZ();

        double maxX = aabb.getMax().getX();
        double maxY = aabb.getMax().getY();
        double maxZ = aabb.getMax().getZ();

        double medX = (maxX + minX) / 2;
        double medY = (maxY + minY) / 2;
        double medZ = (maxZ + maxZ) / 2;

        sons[6] = new Node(this,
                            new AABB(
                                    new Vect3d(minX, medY, medZ),
                                    new Vect3d(medX, maxY, maxZ)),
                            level+1, octree);
        
        sons[4] = new Node(this,
                            new AABB(
                                    new Vect3d(minX, medY, minZ),
                                    new Vect3d(medX, maxY, medZ)),
                            level+1, octree);
        
        sons[5] = new Node(this,
                            new AABB(
                                    new Vect3d(medX, medY, minZ),
                                    new Vect3d(maxX, maxY, medZ)),
                            level+1, octree);
        
        sons[7] = new Node(this,
                            new AABB(
                                    new Vect3d(medX, medY, medZ),
                                    new Vect3d(maxX, maxY, maxZ)),
                            level+1, octree);
        
        sons[2] = new Node(this,
                            new AABB(
                                    new Vect3d(minX, minY, medZ),
                                    new Vect3d(medX, medY, maxZ)),
                            level+1, octree);
        
        sons[0] = new Node(this,
                            new AABB(
                                    new Vect3d(minX, minY, minZ),
                                    new Vect3d(medX, medY, medZ)),
                            level+1, octree);
        
        sons[1] = new Node(this,
                            new AABB(
                                    new Vect3d(medX, minY, minZ),
                                    new Vect3d(maxX, medY, medZ)),
                            level+1, octree);
        
        sons[3] = new Node(this,
                            new AABB(
                                    new Vect3d(medX, minY, medZ),
                                    new Vect3d(maxX, medY, maxZ)),
                            level+1, octree);
        
        for(int i = 0; i < triangles.size(); i++){
           for(int j = 0; j < sons.length; j++){
               if(triangles.get(i).getAABB().testAABBAABB(sons[j].aabb)){
                   sons[j].insertTriangle(triangles.get(i));
               }
           }
        }
        
        triangles.clear();
        
        sonsInstantiated = true;
    }
}

public class Octree {

    private ArrayList<Triangle3d> triangles;
    private int maxLevel;
    private Node root;
    /*
    public Octree() {
        maxLevel = 4;
        nPoints = 0;
        vPoints = new ArrayList<Triangle3d>();
        root = new Node();
    }
    */
    public Octree(TriangleMesh mesh, int maxLevel) {
        this.maxLevel = maxLevel;
        triangles = mesh.getTriangles();
        AABB aabb = mesh.getAABB();
        root = new Node(null, aabb, 0, this);
        for (int i = 0; i < triangles.size(); i++) {
            root.insertTriangle(triangles.get(i));
        }
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
