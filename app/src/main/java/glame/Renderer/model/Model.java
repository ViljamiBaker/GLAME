package glame.renderer.model;

import org.joml.Matrix4f;

import glame.renderer.Renderer;

public class Model{
    public int index = -1;
    public float[] vertices;
    public int[] indices;
    public Matrix4f transform;
    public Texture tex;
    public Texture spec;
    public Model(float[] vertices, int[] indices, String tex, String spec) {
        this.vertices = vertices;
        this.indices = indices;
        this.transform = new Matrix4f().identity();
        this.tex = Renderer.getTexture(tex);
        this.spec = Renderer.getTexture(spec);
    }
    public Model(float[] vertices, int[] indices, Texture tex, Texture spec) {
        this.vertices = vertices;
        this.indices = indices;
        this.transform = new Matrix4f().identity();
        this.tex = tex;
        this.spec = spec;
    }
}
