/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algGeom.lib3D;

import Util.Draw;
import algGeom.lib3D.Voxel.Colour;
import javax.media.opengl.*;

/**
 *
 * @author alvaro
 */
public class DrawVoxelModel extends Draw {
    
    VoxelModel model;
    
    public DrawVoxelModel(VoxelModel model){
        this.model = model;
    }

    @Override
    public void drawObject(GL g) {
        int num = 0;
        for(int i = 0; i < model.malla.length; i++){
            for(int j = 0; j < model.malla[i].length; j++){
                for(int k = 0; k < model.malla[i][j].length; k++){
                    if(model.malla[i][j][k].getColour() == Colour.grey){
                        DrawVoxel dv = new DrawVoxel(model.malla[i][j][k]);
                        dv.drawObjectC(g, 0.0f, 0.0f, 0.0f);
                    }else if(model.malla[i][j][k].getColour() == Colour.black){
                        DrawVoxelBlack dv = new DrawVoxelBlack(model.malla[i][j][k]);
                        dv.drawObjectC(g, 0.0f, 0.0f, 1.0f);
                    } else {
                        if(model.malla[i][j][k].getColour() == Colour.yellow){
                            DrawVoxel dv = new DrawVoxel(model.malla[i][j][k]);
                            dv.drawObjectC(g, 0.0f, 1.0f, 0.0f);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void drawObjectC(GL g, float R, float G, float B) {
        g.glColor3f(R, G, B);
        drawObject(g);
    }
    
}
