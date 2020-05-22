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
        maxSons = 8;
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
        double medZ = (maxZ + minZ) / 2;

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

    public boolean collision(Node other, ArrayList<AABB> aabbs) {
        boolean col = false;
        if(aabb.testAABBAABB(other.aabb)){
            if(isLeaf){
                if(other.isLeaf){
//                    System.out.println(level + " " + aabb);
//                    System.out.println(other.level + " " + other.aabb);
                    aabbs.add(aabb);
                    aabbs.add(other.aabb);
                    col = true;
                } else {
                    boolean repeat = true;
                    int i = 0;
                    do{
                        if(collision(other.sons[i], aabbs)){
//                            System.out.println(level + " " + aabb);
//                            System.out.println(other.level + " " + other.aabb);
                            aabbs.add(aabb);
                            aabbs.add(other.aabb);
                            col = true;
//                            repeat = false;
                        } else {
                            i++;
                        }
                    } while (repeat && i < other.maxSons);
//                    if(repeat){
//                        System.out.println("No coalisionan");
//                        col = false;
//                    }
                }
            } else {
                if(other.isLeaf){
                    boolean repeat = true;
                    int i = 0;
                    do{
                        if(sons[i].collision(other, aabbs)){
//                            System.out.println(level + " " + aabb);
//                            System.out.println(other.level + " " + other.aabb);
                            aabbs.add(aabb);
                            aabbs.add(other.aabb);
                            col = true;
//                            repeat = false;
                        } else {
                            i++;
                        }
                    } while (repeat && i < other.maxSons);
//                    if(repeat){
//                        col = false;
//                    }
                } else {
                    boolean repeat = true;
                    int i = 0;
                    do{
                        int j = 0;
                        do{
                            if(sons[i].collision(other.sons[j], aabbs)){
//                                System.out.println(level + " " + aabb);
//                                System.out.println(other.level + " " + other.aabb);
                                aabbs.add(aabb);
                                aabbs.add(other.aabb);
                                col = true;
//                                repeat = false;
                            } else {
                                j++;
                            }
                        } while(repeat && j < maxSons);
                        i++;
                    } while (repeat && i < other.maxSons);
//                    if(repeat){
//                        System.out.println("No coalisionan");
//                        col = false;
//                    }
                }
            }
        }
        return col;
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

    public boolean collision(Octree other, ArrayList<AABB> aabbs) {
        if(root.collision(other.root, aabbs)){
            return true;
        } else return false;
    }
    
    public String toString(){
        return meshString;
    }
}
