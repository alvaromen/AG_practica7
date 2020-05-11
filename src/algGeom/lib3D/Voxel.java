/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom.lib3D;

import algGeom.lib2D.BasicGeom;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alvaro
 */
public class Voxel extends AABB {

    public enum Colour {
        white,
        grey,
        black,
        NP,
        yellow
    }

    private Colour colour;
    
    public Voxel(Vect3d minimum, Vect3d maximum) {
        super(minimum, maximum);
    }

    public Voxel(Vect3d minimum, Vect3d maximum, Colour c) {
        super(minimum, maximum);
        colour = c;
    }
    

    public void setColour(Colour c) {
        colour = c;
    }

    public Colour getColour() {
        return colour;
    }
}
