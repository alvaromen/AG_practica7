/**
 * Java Library for Geometric Algorithms subject
 *
 * @author Lidia Ortega, Alejandro Graciano
 * @version 1.0
 */
package algGeom.lib2D;

import Util.Draw;
import com.sun.opengl.util.Animator;
import Util.GeomMethods;
import Util.RandomGen;
import algGeom.lib3D.DrawPointCloud3d;
import algGeom.lib3D.Line3d;
import algGeom.lib3D.PointCloud3d;
import algGeom.lib3D.Vect3d;
import java.awt.Frame;
import java.awt.BorderLayout;
import com.vividsolutions.jts.triangulate.quadedge.Vertex;
import com.vividsolutions.jts.geom.Coordinate;
import javax.media.opengl.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Test extends Frame implements GLEventListener {

    static int A = 0;
    static int B = 1;
    static int HEIGHT = 800;
    static int WIDTH = 800;
    static int POINT_CLOUD_VERT = 20;
    static GL gl; // interface to OpenGL
    static GLCanvas canvas; // drawable in a frame
    static GLCapabilities capabilities;
    static boolean visualizeAxis = true;
    static int exercise = A;
    static Animator animator;

    // Geometric data in memory
    // Exercise A 
    PointCloud pc;
    PointCloud pi;
    DrawPointCloud dc;
    
    

    TDelaunay d;
    DrawTDelaunay dd;

    int i = 0;

    public Test(String title) {
        super(title);

        // 1. specify a drawable: canvas
        capabilities = new GLCapabilities();
        capabilities.setDoubleBuffered(false);
        canvas = new GLCanvas();

        // 2. listen to the events related to canvas: reshape
        canvas.addGLEventListener(this);

        // 3. add the canvas to fill the Frame container
        add(canvas, BorderLayout.CENTER);

        // 4. interface to OpenGL functions
        gl = canvas.getGL();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//				animator.stop(); // stop animation
                System.exit(0);
            }
        });

        addKeyListener(new KeyListener() {
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
                    case 'a':
                        System.out.println("Cambiando a ejercicio A");
                        exercise = A;
                        break;
                    case 'A':
                        System.out.println("Cambiando a ejercicio A");
                        exercise = A;
                        break;
                    case 'b':
                        System.out.println("Cambiando a ejercicio B");
                        exercise = B;
                        break;
                    case 'B':
                        System.out.println("Cambiando a ejercicio B");
                        exercise = B;
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
    }

    protected void initExerciseA() throws Exception {
        //Ejercicio 1
//        pc = new PointCloud(100);
//        dc = new DrawPointCloud(pc);
//        d = new TDelaunay(new PointCloud());
//        dd = new DrawTDelaunay(d);
        //Ejercicio 2
        d = new TDelaunay();
        dd = new DrawTDelaunay(d);
        pi = new PointCloud();
        pc = new PointCloud(50);
        dc = new DrawPointCloud(pi);

    }

    protected void drawExerciseA() {
        //Exercise 2
        if (i < 50) {
            pi.addPoint(pc.getPoint(i));
            try {
                d.addPoint(pc.getPoint(i));
                i++;
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        if (i >= 3) {
            dd.drawObjectC(gl, 0, 1, 1);
        }
        dc.drawObjectC(gl, 1, 0, 1);
    }

    protected void initExerciseB() {

    }

    protected void drawExerciseB() {
    }

    // called once for OpenGL initialization
    public void init(GLAutoDrawable drawable) {

        animator = new Animator(canvas);
        animator.start(); // start animator thread
        // display OpenGL and graphics system information
        System.out.println("INIT GL IS: " + gl.getClass().getName());
        System.err.println(drawable.getChosenGLCapabilities());
        System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
        System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
        System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));

        RandomGen random = RandomGen.getInst();
        //random.setSeed(5308170449555L);
        System.err.println("SEED: " + random.getSeed());

        try {

            initExerciseA();
            //initExerciseB();

        } catch (Exception ex) {
            System.out.println("Error en el dibujado");
            ex.printStackTrace();
        }

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
            int height) {

        Draw.WIDTH = WIDTH = width; // new width and height saved
        Draw.HEIGH = HEIGHT = height;
        //DEEP = deep;
        if (Draw.HEIGH < Draw.WIDTH) {
            Draw.WIDTH = Draw.HEIGH;
        }
        if (Draw.HEIGH > Draw.WIDTH) {
            Draw.HEIGH = Draw.WIDTH;
        }
        // 7. specify the drawing area (frame) coordinates
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, width, 0, height, -100, 100);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        if (HEIGHT < WIDTH) {
            gl.glTranslatef((WIDTH - HEIGHT) / 2, 0, 0);
        }
        if (HEIGHT > WIDTH) {
            gl.glTranslatef(0, (HEIGHT - WIDTH) / 2, 0);
        }

    }

    // called for OpenGL rendering every reshape
    public void display(GLAutoDrawable drawable) {

        // limpiar la pantalla
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        /* El color de limpiado ser� cero */
        gl.glClearDepth(1.0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        if (visualizeAxis) {
            DrawAxis ejes = new DrawAxis();
            ejes.drawObject(gl);
        }

        if (exercise == A) {
            drawExerciseA();
        }

        /*
        if (exercise == B) {
            drawExerciseB();
        }
         */
        gl.glFlush();
    }

    // called if display mode or device are changed
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
            boolean deviceChanged) {
    }

    public static void main(String[] args) {
        Draw.HEIGH = HEIGHT;
        Draw.WIDTH = WIDTH;
        Test frame = new Test("Prac1. Algoritmos Geometricos");
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
    }
}
