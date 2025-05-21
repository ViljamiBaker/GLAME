package glame.renderer.util;

import org.joml.Matrix4f;

import glame.renderer.Renderer;

public class Model{
    public int index = -1;
    public float[] vertices;
    public Matrix4f transform;
    public Texture tex;
    public Texture spec;
    public Model(float[] vertices,Matrix4f transform, String tex, String spec) {
        this.vertices = vertices;
        this.transform = transform;
        this.tex = Renderer.getTexture(tex);
        this.spec = Renderer.getTexture(spec);
    }
    public Model(float[] vertices,Matrix4f transform, Texture tex, Texture spec) {
        this.vertices = vertices;
        this.transform = transform;
        this.tex = tex;
        this.spec = spec;
    }
}
