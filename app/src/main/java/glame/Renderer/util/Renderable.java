package glame.renderer.util;

import org.joml.Matrix4f;

import glame.renderer.Renderer;

// 
public class Renderable {
    protected Model model;
    public Renderable(){
        //getModel();
        Renderer.addRenderable(this);
    }
    public void setModel(){
        model = new Model(new float[0], new Matrix4f(), LUTILVB.defaultTexture, LUTILVB.defaultSpecular);
    }
    public Model getModel(){
        return model;
    }
    public Model model(){
        if(model == null){
            return new Model(new float[0], new Matrix4f(), LUTILVB.defaultTexture, LUTILVB.defaultSpecular);
        }
        return getModel();
    }
    public void remove(){
        Renderer.removeRenderable(this);
    }
    public void updateModel(){}
}
