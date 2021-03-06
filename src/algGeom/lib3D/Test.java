/**
 * Java Library for Geometric Algorithms subject
 *
 * @author Lidia Ortega, Alejandro Graciano
 * @version 1.0
 */
package algGeom.lib3D;

import Util.BasicGeom;
import Util.Draw;
import com.sun.opengl.util.Animator;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.triangulate.DelaunayTriangulationBuilder;
import com.vividsolutions.jts.triangulate.IncrementalDelaunayTriangulator;
import com.vividsolutions.jts.triangulate.quadedge.Vertex;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.triangulate.DelaunayTriangulationBuilder;
import com.vividsolutions.jts.triangulate.IncrementalDelaunayTriangulator;
import com.vividsolutions.jts.triangulate.quadedge.Vertex;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Coordinate;

public class Test extends Frame implements GLEventListener,
        MouseListener,
        MouseMotionListener,
        MouseWheelListener {

    public static void main(String[] args) {
        Draw.HEIGH = HEIGHT;
        Draw.WIDTH = WIDTH;
        Draw.DEPTH = 100;

        Frame frame = new Frame("Alg. Geom. Pract. 2");
        canvas = new GLCanvas();

        canvas.addGLEventListener(new Test());
        frame.add(canvas);
        frame.setSize(HEIGHT, WIDTH);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });

        frame.addKeyListener(new KeyListener() {
            long clock = 0;

            /**
             * Handle the key typed event from the text field.
             */
            public void keyTyped(KeyEvent e) {
//                        System.out.println(e + "KEY TYPED: ");
            }

            /**
             * Handle the key pressed event from the text field.
             */
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case 'E':
                    case 'e':
                        visualizeAxis = !visualizeAxis;
                        canvas.repaint();
                        clock = e.getWhen();
                        break;

                    case 27: // esc
                        System.exit(0);
                }

            }

            /**
             * Handle the key released event from the text field.
             */
            public void keyReleased(KeyEvent e) {
                clock = e.getWhen();
//                        System.out.println(e + "KEY RELEASED: ");
            }
        });

        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }
    static GLCanvas canvas;
    // So we can zoom in and out 
    private float zoom = 0.0f;
    // rotating the scene
    private float view_rotx = 20.0f; //20
    private float view_roty = 30.0f; //30
    // remember last mouse position
    private int oldMouseX;
    private int oldMouseY;
    static int HEIGHT = 800, WIDTH = 800;
    //static int HEIGHT = 10, WIDTH = 10;

    Vect3d cameraPosition, cameraLookAt, cameraUp;

    // Assigment
    static boolean visualizeAxis = true;

    DrawAxis dAxis = new DrawAxis();
    TriangleMesh mesh1;
    TriangleMesh mesh2;
    DrawTriangleMesh dtm1;
    DrawTriangleMesh dtm2;
    ArrayList<DrawAABB> das1;
    ArrayList<DrawAABB> das2;
    ArrayList<DrawTriangle3d> dts;
    int a = 0;

    public void initLight(GL gl) {
        gl.glPushMatrix();
        gl.glShadeModel(GL.GL_SMOOTH);

        float ambient[] = {1.0f, 1.0f, 1.0f, 1.0f};
        float diffuse[] = {1.0f, 1.0f, 1.0f, 1.0f};
        float specular[] = {0.2f, 0.0f, 0.0f, 1.0f};
        float position[] = {20.0f, 30.0f, 20.0f, 0.0f};
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specular, 0);
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);

        gl.glEnable(GL.GL_NORMALIZE);
        gl.glEnable(GL.GL_COLOR_MATERIAL);
        gl.glPopMatrix();

    }

    public void initExerciseA(GL gl) {
        long start, end, totalTime = 0;
        try {
            start = System.currentTimeMillis();
            // cat models
//            mesh1 = new TriangleMesh("modelos/cat.obj");
//            mesh2 = new TriangleMesh("modelos/cat.obj");
//            end = System.currentTimeMillis();
//            long timeLoading = end - start;
//            totalTime += timeLoading;
//            mesh1.move(new Vect3d(-100, 0, 100));
//            mesh2.move(new Vect3d(100, 0, 100));
            
            //beer can models
            mesh1 = new TriangleMesh("modelos/lata_cerveza.obj");
            mesh2 = new TriangleMesh("modelos/lata_cerveza.obj");
            end = System.currentTimeMillis();
            long timeLoading = end - start;
            totalTime += timeLoading;
            mesh1.move(new Vect3d(-50, 0, 0));
            mesh2.move(new Vect3d(50, 0, 24));


            if(timeLoading > 1000){
                timeLoading /= 1000;
            }
            System.out.println("Time to load models: " + timeLoading + " milliseconds.");
            
            dtm1 = new DrawTriangleMesh(mesh1, false, 0.5f);
            dtm2 = new DrawTriangleMesh(mesh2, false, 0.5f);
            start = System.currentTimeMillis();
            Octree tree1 = new Octree(mesh1, 12);
            Octree tree2 = new Octree(mesh2, 12);
            end = System.currentTimeMillis();
            long timeBuilding = end - start;
            totalTime += timeBuilding;
            if(timeBuilding > 1000){
                timeBuilding /= 1000;
                System.out.println("\nTime to build the octrees: " + timeBuilding + " seconds.");
            } else {
                System.out.println("\nTime to build the octrees: " + timeBuilding + " milliseconds.");
            }
            
//            start = System.currentTimeMillis();
//            int level = 9;
//            tree1.adjustAtLevel(level);
//            tree2.adjustAtLevel(level);
//            end = System.currentTimeMillis();
//            long timeDeepening = end - start;
//            totalTime += timeDeepening;
//            if(timeDeepening > 1000){
//                timeDeepening /= 1000;
//                System.out.println("\nTime to reach the level " + level + " : " + timeDeepening + " seconds.");
//            } else {
//                System.out.println("\nTime to reach the level: " + timeDeepening + " milliseconds.");
//            }
            
            ArrayList<AABB> aabbs1 = new ArrayList<AABB>();
            ArrayList<AABB> aabbs2 = new ArrayList<AABB>();
            ArrayList<Triangle3d> triangles = new ArrayList<Triangle3d>();
            start = System.currentTimeMillis();
            boolean collision = tree1.collision(tree2, aabbs1, aabbs2, triangles);
            end = System.currentTimeMillis();
            
            long timeFindingCollisions = end - start;
            totalTime += timeFindingCollisions;
            if(timeFindingCollisions > 1000){
                timeFindingCollisions /= 1000;
                System.out.println("\nTime to get if there is a collision : " + timeFindingCollisions + " seconds.");
            } else {
                System.out.println("\nTime to get if there is a collision : " + timeFindingCollisions + " milliseconds.");
            }
            
            System.out.println("\nTotal time in the full process : " + totalTime + " milliseconds.");
            
            das1 = new ArrayList<DrawAABB>();
            das2 = new ArrayList<DrawAABB>();
            dts = new ArrayList<DrawTriangle3d>();
            
            if(collision){
                System.out.println("\n");
                System.out.println("**************************");
                System.out.println("**The two models collide**");
                System.out.println("**************************");
            
                for(int i = 0; i < aabbs1.size(); i++){
                    das1.add(new DrawAABB(aabbs1.get(i), 2));
                }
                for(int i = 0; i < aabbs2.size(); i++){
                    das2.add(new DrawAABB(aabbs2.get(i), 2));
                }
                for(int i = 0; i < aabbs2.size(); i++){
                    dts.add(new DrawTriangle3d(triangles.get(i)));
                }
            } else {
                System.out.println("\n");
                System.out.println("*********************************");
                System.out.println("**The two models do not collide**");
                System.out.println("*********************************");
            }
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
//        //Prueba triángulos
//        Triangle3d t1 = new Triangle3d(20, 0, 0, 0, 20, 0, -20, 0, 0);
//        Triangle3d t2 = new Triangle3d(20, 10, 0, -15, 10, 20, -15, 10, -20);
//        System.out.println(t1.intersectionTriTri(t2));
//        dts = new ArrayList<DrawTriangle3d>();
//        dts.add(new DrawTriangle3d(t1));
//        dts.add(new DrawTriangle3d(t2));
    }

    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        // Set backgroundcolor and shading mode
        gl.glEnable(GL.GL_BLEND);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        gl.glEnable(GL.GL_POINT_SMOOTH);
        // Set backgroundcolor and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        System.out.println("INIT GL IS: " + gl.getClass().getName());
        System.err.println(drawable.getChosenGLCapabilities());
        System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
        System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
        System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));

        cameraPosition = new Vect3d(100, 100, 100);
        cameraLookAt = new Vect3d(0, 0, 0);
        cameraUp = new Vect3d(0, 1, 0);

        initLight(gl);

        initExerciseA(gl);

        drawable.addMouseListener(this);
        drawable.addMouseMotionListener(this);
        drawable.addMouseWheelListener(this);

    }

    public void reshape(GLAutoDrawable drawable,
            int x, int y, int width, int height) {
        WIDTH = width; // new width and height saved
        HEIGHT = height;

        GL gl = drawable.getGL();
        GLU glu = new GLU();
        if (height <= 0) {
            height = 1;
        }
        // keep ratio
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 0.1, 2000.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

    }

    public void displayExerciseA(GL gl) {
        if((a != 0) && (a % 30 == 0)){
            mesh1.move(new Vect3d(5, 0, 0));
            mesh2.move(new Vect3d(-5, 0, 0));
            dtm1 = new DrawTriangleMesh(mesh1, false, 0.5f);
            dtm2 = new DrawTriangleMesh(mesh2, false, 0.5f);
            
            long start, end;
            
            start = System.currentTimeMillis();
            Octree tree1 = new Octree(mesh1, 12);
            Octree tree2 = new Octree(mesh2, 12);
            end = System.currentTimeMillis();
            long timeBuilding = end - start;
            
            if(timeBuilding > 1000){
                timeBuilding /= 1000;
                System.out.println("\nTime to build the octrees: " + timeBuilding + " seconds.");
            } else {
                System.out.println("\nTime to build the octrees: " + timeBuilding + " milliseconds.");
            }
            
            ArrayList<AABB> aabbs1 = new ArrayList<AABB>();
            ArrayList<AABB> aabbs2 = new ArrayList<AABB>();
            ArrayList<Triangle3d> triangles = new ArrayList<Triangle3d>();
            start = System.currentTimeMillis();
            boolean collision = tree1.collision(tree2, aabbs1, aabbs2, triangles);
            end = System.currentTimeMillis();
            
            long timeFindingCollisions = end - start;
            if(timeFindingCollisions > 1000){
                timeFindingCollisions /= 1000;
                System.out.println("\nTime to get if there is a collision : " + timeFindingCollisions + " seconds.");
            } else {
                System.out.println("\nTime to get if there is a collision : " + timeFindingCollisions + " milliseconds.");
            }
            
            das1 = new ArrayList<DrawAABB>();
            das2 = new ArrayList<DrawAABB>();
            dts = new ArrayList<DrawTriangle3d>();
            
            if(collision){
                System.out.println("\n");
                System.out.println("**************************");
                System.out.println("**The two models collide**");
                System.out.println("**************************");                
            
                das1 = new ArrayList<DrawAABB>();
                for(int i = 0; i < aabbs1.size(); i++){
                    das1.add(new DrawAABB(aabbs1.get(i), 2));
                }
                das2 = new ArrayList<DrawAABB>();
                for(int i = 0; i < aabbs2.size(); i++){
                    das2.add(new DrawAABB(aabbs2.get(i), 2));
                }
                dts = new ArrayList<DrawTriangle3d>();
                for(int i = 0; i < triangles.size(); i++){
                    dts.add(new DrawTriangle3d(triangles.get(i)));
                }
            } else {
                System.out.println("\n");
                System.out.println("*********************************");
                System.out.println("**The two models do not collide**");
                System.out.println("*********************************");
            }
        }
        dtm1.drawObjectC(gl, 1, 0, 0);
        dtm2.drawObjectC(gl, 0, 0, 1);
        for(int i = 0; i < das1.size(); i++){
            das1.get(i).drawObjectC(gl, 1, 0, 0);
        }
        for(int i = 0; i < das2.size(); i++){
            das2.get(i).drawObjectC(gl, 0, 0, 1);
        }
        for(int i = 0; i < dts.size(); i++){
            dts.get(i).drawObjectC(gl, 0, 0, 1);
        }
        a++;
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GLU glu = new GLU(); // needed for lookat
        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();
        glu.gluLookAt(cameraPosition.getX(), cameraPosition.getY(), cameraPosition.getZ(), // eye pos
                cameraLookAt.getX(), cameraLookAt.getY(), cameraLookAt.getZ(), // look at
                cameraUp.getX(), cameraUp.getY(), cameraUp.getZ());  // up

        gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);

        // Fill this method with the proposed exercises
        if (visualizeAxis) {
            DrawAxis ejes = new DrawAxis();
            ejes.drawObject(gl);
        }

        displayExerciseA(gl);

        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable,
            boolean modeChanged, boolean deviceChanged) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        oldMouseX = e.getX();
        oldMouseY = e.getY();
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Dimension size = e.getComponent().getSize();
        float thetaY = 360.0f * ((float) (x - oldMouseX) / (float) size.width);
        float thetaX = 360.0f * ((float) (oldMouseY - y) / (float) size.height);
        oldMouseX = x;
        oldMouseY = y;
        view_rotx += thetaX;
        view_roty += thetaY;
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getUnitsToScroll() < 0) {
            Segment3d seg = new Segment3d(cameraPosition, cameraLookAt);
            cameraPosition = seg.getPoint(zoom - ((double) e.getUnitsToScroll() / 100.0f));
        } else {
            Segment3d seg = new Segment3d(cameraPosition, cameraLookAt);
            cameraPosition = seg.getPoint(zoom - ((double) e.getUnitsToScroll() / 100.0f));
        }
    }
}
