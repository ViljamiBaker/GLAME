package glame.renderer.model;

import glame.renderer.Renderer;
import glame.renderer.util.LUTILVB;

// 
public class Renderable {
    protected Model model;
    public Renderable(){
        setModel();
        Renderer.addRenderable(this);
    }
    public void setModel(){
        model = new Model(new float[0], new int[0], LUTILVB.defaultTexture, LUTILVB.defaultSpecular);
    }
    public Model model(){
        if(model == null){
            try {
                setModel();
                return model;
            } catch (Exception e) {
                // model or setting failed to load
                return new Model(new float[0], new int[0], LUTILVB.defaultTexture, LUTILVB.defaultSpecular);
            }
        }
        return model;
    }
    public void remove(){
        Renderer.removeRenderable(this);
    }
    public void updateModel(){}
}
