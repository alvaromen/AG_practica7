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
    private boolean isLeaf;
    private int maxSons;

    public Node(Node father, AABB aabb, int level, Octree octree) {
        this.father = father;
        this.aabb = aabb;
        this.level = level;
        this.octree = octree;
        this.father = father;
        triangles = new ArrayList<Triangle3d>();
        sonsInstantiated = false;
        isLeaf = true;
        maxSons = 10;
    }

    public void insertTriangle(Triangle3d t) {
        if (level == octree.getMaxLevel()) { // if it is the last level, add it directly
            triangles.add(t);
        } else {
            if (isLeaf) { // if it is a leaf, add it
                triangles.add(t);
                if (triangles.size() > maxSons) { // if it has more triangles than the maximum, split into his sons
                    if (!sonsInstantiated) { // it should instantiate anyway but lets check it
                        instantiateSons();
                        isLeaf = false; // now it has sons, so it is not a leaf
                    }
                }
            } else { //if it is not a leaf, then add in the appropiate sons
                for (int i = 0; i < sons.length; i++) {
                    if (t.getAABB().testAABBAABB(sons[i].aabb)) {
                        sons[i].insertTriangle(t);
                    }
                }
            }
        }
    }

    void instantiateSons() {
        sons = new Node[8];

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
                level + 1, octree);

        sons[4] = new Node(this,
                new AABB(
                        new Vect3d(minX, medY, minZ),
                        new Vect3d(medX, maxY, medZ)),
                level + 1, octree);

        sons[5] = new Node(this,
                new AABB(
                        new Vect3d(medX, medY, minZ),
                        new Vect3d(maxX, maxY, medZ)),
                level + 1, octree);

        sons[7] = new Node(this,
                new AABB(
                        new Vect3d(medX, medY, medZ),
                        new Vect3d(maxX, maxY, maxZ)),
                level + 1, octree);

        sons[2] = new Node(this,
                new AABB(
                        new Vect3d(minX, minY, medZ),
                        new Vect3d(medX, medY, maxZ)),
                level + 1, octree);

        sons[0] = new Node(this,
                new AABB(
                        new Vect3d(minX, minY, minZ),
                        new Vect3d(medX, medY, medZ)),
                level + 1, octree);

        sons[1] = new Node(this,
                new AABB(
                        new Vect3d(medX, minY, minZ),
                        new Vect3d(maxX, medY, medZ)),
                level + 1, octree);

        sons[3] = new Node(this,
                new AABB(
                        new Vect3d(medX, minY, medZ),
                        new Vect3d(maxX, medY, maxZ)),
                level + 1, octree);

        for (int i = 0; i < triangles.size(); i++) {
            for (int j = 0; j < sons.length; j++) {
                if (triangles.get(i).getAABB().testAABBAABB(sons[j].aabb)) {
                    sons[j].insertTriangle(triangles.get(i));
                }
            }
        }

        triangles.clear();

        sonsInstantiated = true;
    }

    public boolean collision(Node other, String path) {
        if (isLeaf) {
            if (other.isLeaf) {
                if(aabb.testAABBAABB(other.aabb)){ //if both of them are leaves, then return true if they collide
                    System.out.println(toString() + " " + other.toString());
                    return true;
                }
                /*
                // if we want more precission we should check the triangles
                for(int i = 0; i < triangles.size(); i++){
                    for(int j = 0; j < other.triangles.size(); j++){
                        triangles.get(i).
                    }
                }
                */
            } else { // if we are not in a leaf of the other octree, we have to keep going down
                for (int i = 0; i < other.sons.length; i++) {
                    if (aabb.testAABBAABB(other.sons[i].aabb)) {
                        if(collision(sons[i], path)){
                                System.out.println(toString() + " " + other.toString());
                            return true;
                        }
                    }
                }
            }
        } else {
            if (other.isLeaf) { // if we are in a leaf in the other octree, we have to keep going down in the first octree
                for (int i = 0; i < sons.length; i++) {
                    if (other.aabb.testAABBAABB(sons[i].aabb)) {
                        if(sons[i].collision(other, path)){
                                System.out.println(toString() + " " + other.toString());
                            return true;
                        }
                    }
                }
            } else { // if both of them are not a leaf, we have to keep going down in both octrees
                for(int i = 0; i < sons.length; i++){
                    for(int j = 0; j < other.sons.length; j++){
                        if(sons[i].aabb.testAABBAABB(other.sons[j].aabb)){
                            if(sons[i].collision(other.sons[j], path)){
                                System.out.println(toString() + " " + other.toString());
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        return octree + " " + level + " " + aabb;
    }
}

public class Octree {

    private ArrayList<Triangle3d> triangles;
    private int maxLevel;
    private Node root;
    private String meshString;

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
        meshString = mesh.toString();
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public boolean collision(Octree other) {
        String s = new String();
        if(root.collision(other.root, s)){
            System.out.println(s);
            return true;
        } else return false;
    }
    
    public String toString(){
        return meshString;
    }
}
