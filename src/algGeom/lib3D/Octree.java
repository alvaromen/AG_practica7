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
    ArrayList<Vect3d> points;
    private AABB aabb;
    int level;
    Octree octree;
    Node[] sons;
    boolean sonsInstantiated;

    public Node(Node father, AABB aabb, int level, Octree octree) {
        this.father = father;
        this.aabb = aabb;
        this.level = level;
        this.octree = octree;
        this.father = father;
        for (int i = 0; i < 8; i++) {
            sons[i] = null;
        }
        points = new ArrayList<Vect3d>();
        sonsInstantiated = false;
    }

    public void insertPoint(Vect3d p) {
        if (level == octree.getMaxLevel()) {
            points.add(p);
        } else {
            if (!sonsInstantiated) {
                instantiateSons();
            }
            double coorX = p.getX();
            double coorY = p.getY();
            double coorZ = p.getZ();
            
            double minX = aabb.getMin().getX();
            double minY = aabb.getMin().getY();
            double minZ = aabb.getMin().getZ();
            
            double maxX = aabb.getMax().getX();
            double maxY = aabb.getMax().getY();
            double maxZ = aabb.getMax().getZ();
            
            double medX = (maxX + minX) / 2;
            double medY = (maxY + minY) / 2;
            double medZ = (maxZ + maxZ) / 2;
            
            if(coorY >= medY){
                if(coorX >= medX){
                    if(coorZ >= medZ){
                        sons[7].insertPoint(p);
                    } else {
                        sons[5].insertPoint(p);
                    }
                } else {
                    if(coorZ >= medZ){
                        sons[6].insertPoint(p);
                    } else {
                        sons[4].insertPoint(p);
                    }
                }
            } else {
                if(coorX >= medX){
                    if(coorZ >= medZ){
                        sons[3].insertPoint(p);
                    } else {
                        sons[1].insertPoint(p);
                    }
                } else {
                    if(coorZ >= medZ){
                        sons[2].insertPoint(p);
                    } else {
                        sons[0].insertPoint(p);
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
    }
}

public class Octree {

    private int nPoints;
    private ArrayList<Vect3d> vPoints;
    private int maxLevel;
    private Node root;

    /*
    public Octree() {
        maxLevel = 4;
        nPoints = 0;
        vPoints = new ArrayList<Vect3d>();
        root = new Node();
    }
    */
    public Octree(PointCloud3d pc, int maxLevel) {
        this.maxLevel = maxLevel;
        nPoints = pc.size();
        vPoints = new ArrayList<Vect3d>();
        AABB aabb = pc.getAABB();
        root = new Node(null, aabb, 0, this);
        for (int i = 0; i < nPoints; i++) {
            Vect3d p = pc.getPoint(i);
            vPoints.add(p);
            root.insertPoint(p);
        }
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
